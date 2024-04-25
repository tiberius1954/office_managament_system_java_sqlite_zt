package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.Date;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import classes.Employee;
import classes.Hhelper;
import classes.Hhelper.MyTablePrintable;
import classes.Hhelper.StringUtils;
import net.proteanit.sql.DbUtils;


public class Print_leave extends JFrame{
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();	
	StringUtils hss = hh.new StringUtils();
	Databaseop dd = new Databaseop();	
	int Emp_idd = 0;
	private String Empname = "";
	private String Emonth ="";
	private String Eyear="";
	
	Print_leave(){
		initcomponents();
		try {		
			dd.employeecombofill(cmbemployees);
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	   table_update("");
		hh.iconhere(this);	
	}
	private void  initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("List leaves");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);		
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1210, 50);
		fejpanel.setBackground(hh.zold);

		lbheader = hh.flabel("PRINT LEAVES");
		lbheader.setBounds(430, 5, 500, 40);
		fejpanel.add(lbheader);
		add(fejpanel);
		
		lbemployee = hh.clabel("Employee");
		lbemployee.setBounds(130, 70, 100, 25);
		add(lbemployee);

		cmbemployees = hh.cbcombo();
		cmbemployees.setName("employee");
		cmbemployees.setBounds(240, 70, 250, 25);	
		add(cmbemployees);
		cmbemployees.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Employee employee = (Employee) cmbemployees.getSelectedItem();
					Empname = employee.getName();				
					Emp_idd = employee.getEmp_id();				
				}
			}
		});
		
		lbyears = hh.clabel("Year");
		lbyears.setBounds(500, 70, 50, 25);
		add(lbyears);
		
		cmbyears = hh.cbcombo();
		cmbyears.setModel(new DefaultComboBoxModel(hh.yearso(1)));
		cmbyears.setBounds(560, 70, 70, 25);
		cmbyears.setName("years");
		add(cmbyears);
		cmbyears.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cmbyears.getSelectedItem() != null) {		
						Eyear = String.valueOf(cmbyears.getSelectedItem());
				}								
				}
			}
		});		
		
		lbmonths = hh.clabel("Month");
		lbmonths.setBounds(640, 70, 60, 25);
		add(lbmonths);

		cmbmonths = hh.cbcombo();
		cmbmonths.setModel(new DefaultComboBoxModel(hh.monthso(1)));
		cmbmonths.setBounds(710, 70, 60, 25);
		cmbmonths.setName("months");
		add(cmbmonths);
		cmbmonths.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cmbmonths.getSelectedItem() != null) {		
						Emonth = String.valueOf(cmbmonths.getSelectedItem());
				}					
				}
			}
		});
		
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(780, 70, 90, 25);
		add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		btnclear = hh.cbutton("Clear");
		btnclear.setBounds(875, 70, 90, 25);
		btnclear.setForeground(Color.black);
		btnclear.setBackground(hh.lpiros);
		add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbemployees.setSelectedIndex(-1);
				hh.setSelectedValue(cmbemployees, 0);
				cmbemployees.updateUI();
				Empname ="";
				cmbyears.setSelectedIndex(-1);
				Eyear = "";
				cmbmonths.setSelectedIndex(-1);
				Emonth ="";
				DefaultTableModel d1 = (DefaultTableModel) lea_table.getModel();
				d1.setRowCount(0);				
			}
		});	
		lea_table = hh.ptable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) lea_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		lea_table.setTableHeader(new JTableHeader(lea_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madepheader(lea_table);

		lea_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				lea_table.scrollRectToVisible(lea_table.getCellRect(lea_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane1 = new JScrollPane(lea_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		
		lea_table.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, new String[] { "le_id", "emp_id","Employee", "dep_id", "Department", "Type", 
						"Start", "End", "No of days" }));
		hh.setJTableColumnsWidth(lea_table, 1155, 0, 0, 20, 0, 20, 20, 14, 14, 12);	

		jSPane1.setViewportView(lea_table);
		jSPane1.getViewport().setBackground(hh.vvvzold);
		jSPane1.setBounds(20, 130, 1155, 380);
		jSPane1.setBorder(hh.borderf2);
		add(jSPane1);

		btnprint = hh.cbutton("Print");
		btnprint.setBounds(550, 540, 130, 30);	
		btnprint.setBackground(hh.vpiros1);
		add(btnprint);

		btnprint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setJobName("Leaves");
				HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();		
				attr.add(new MediaPrintableArea(10, 10, 190, 275, MediaPrintableArea.MM));
				job.print(attr);

				MessageFormat[] header = new MessageFormat[3];
				header[0] = new MessageFormat(hss.center("LEAVES  LIST", 100));
				header[1] = new MessageFormat("");
				header[2] = new MessageFormat("Date: " + hh.currentDate()+ " Time: " + hh.currenttime()
				+ " Year: "+ Eyear +" Month: "+ Emonth);
				MessageFormat[] footer = new MessageFormat[1];
				// footer[0] = new MessageFormat(hss.center(" -{0}-",160));
				footer[0] = new MessageFormat(hss.center("Page {0,number,integer}", 185));
				job.setPrintable(hh.new MyTablePrintable(lea_table, JTable.PrintMode.FIT_WIDTH, header, footer));
				job.printDialog();
				job.print();
				
				} catch (java.awt.print.PrinterAbortException e) {
				} catch (PrinterException ex) {
					System.err.println("Error printing: " + ex.getMessage());
					ex.printStackTrace();
				}		
			}
		});			
	}	
	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select a.le_id, a.emp_id, e.name as ename , a.dep_id, d.name as depname, a.type, a.start, a.end,"
					+ " a.noofdays  from leave a  join employees e on a.emp_id = e.emp_id "
					+ "join department d on a.dep_id = d.dep_id  order by ename";
		} else {
			Sql = "select a.le_id, a.emp_id, e.name as ename, a.dep_id, d.name as depname, a.type, a.start, a.end,  "
					+ " a.noofdays from leave a join employees e on a.emp_id = e.emp_id "
					+ "join department d on a.dep_id = d.dep_id  where " + what + " order by ename ";
		}	

		ResultSet res = dh.GetData(Sql);
    	lea_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();		
		
		String[]  fej = { "le_id", "emp_id","Employee", "dep_id", "Department", "Type", "Start", "End", "No of days" };	
		((DefaultTableModel) lea_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(lea_table, 1155, 0, 0, 20, 0, 20, 20, 14, 14, 12);		
	
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) lea_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		lea_table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);	
     	 lea_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				lea_table.scrollRectToVisible(lea_table.getCellRect(lea_table.getRowCount() - 1, 0, true));
			}
		});		
		countdays();
	}
	private void sqlgyart() {
		Employee employee = (Employee) cmbemployees.getSelectedItem();
		int emp_id = employee.getEmp_id();
		if(emp_id<=0) {
			return;
		}				
		String year = "";
		String month = "";	
	
		if (cmbyears.getSelectedItem() != null) {		
			year = String.valueOf(cmbyears.getSelectedItem());
		}
		if (cmbmonths.getSelectedItem() != null) {
			month = String.valueOf(cmbmonths.getSelectedItem());
		}	
			String swhere = "";
			swhere = " a.emp_id =" + emp_id;
			if (!hh.zempty(year)) {
				swhere = swhere + " and strftime('%Y',start)= '" + year + "'";
			}
			if (!hh.zempty(month)) {
				swhere = swhere + " and strftime('%m',start) = '" + month + "'";
			}
	       table_update(swhere);		
	}	
	
	public static void main(String args[]) {
		Print_leave att = new Print_leave();
		att.setSize(1205, 650);
		att.setLayout(null);
		att.setLocationRelativeTo(null);
		att.setVisible(true);
	}
	private void countdays() {
				int total = 0;
				String ss ="";
		    for (int i = 0; i < lea_table.getRowCount(); i++) { 	
		    	ss  = String.valueOf(lea_table.getValueAt(i, 8));
		    	if (!hh.zempty(ss)){
		    	      total  =  total  + hh.stoi(ss);
		    	}		    	
		     }
		   
	        DefaultTableModel d1 = (DefaultTableModel) lea_table.getModel();
	        d1.insertRow(d1.getRowCount(), new Object[] { "","" , "","" , "", "",
					"","Total", total });
	}
	
	
	CardLayout cards;
	JPanel cardPanel, tPanel, ePanel, fejpanel, cPanel;
	JScrollPane jSPane1;
	JTable lea_table;
	JComboBox cmbemployees, cmbyears, cmbmonths;
	JLabel lbemployee, lbdep, lbyears, lbmonths, lbheader;
	JLabel lbpicture, lbsearch, lbemployername;
	JTextField txnoofdays;
	JButton  btnprint, btnclear, btnsearch;
	Container cp;

}
