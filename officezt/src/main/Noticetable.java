package main;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import classes.Employee;
import classes.Hhelper;
import classes.Salvalidation;
import net.proteanit.sql.DbUtils;

public class Noticetable extends JFrame{
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;	
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();	
	Salvalidation svad = new Salvalidation();
	private String rowid = "";
	private int myrow = 0;
	private int Emp_idd = 0;
	Noticetable(){
		initcomponents();
		try {		
		dd.employeecombofill(cmbemployees);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	table_update("");
	hh.iconhere(this);		
		
	}
	private void initcomponents(){
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
		setTitle("Notice table");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		epanel = new JPanel(null);
		epanel.setBounds(10, 120, 490, 500);	
		epanel.setBackground(hh.neonzold);
		epanel.setBorder(hh.borderf);
		cp.add(epanel);
		
		tpanel = new JPanel(null);
		tpanel.setBounds(500, 120, 623, 500);
		tpanel.setBackground(hh.neonzold);
		tpanel.setBorder(hh.borderf);
		cp.add(tpanel);
	
		fejpanel.setBounds(0, 0, 1150, 50);
		fejpanel.setBackground(hh.zold);
		lbheader = hh.flabel("NOTICE TABLE");
		lbheader.setBounds(470, 5, 300, 40);
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
					if (cmbemployees.getSelectedItem() != null) {
					    Emp_idd = ((Employee) cmbemployees.getSelectedItem()).getEmp_id();
					}
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
		
		lbmonths = hh.clabel("Month");
		lbmonths.setBounds(640, 70, 60, 25);
		add(lbmonths);

		cmbmonths = hh.cbcombo();
		cmbmonths.setModel(new DefaultComboBoxModel(hh.monthso(1)));
		cmbmonths.setBounds(710, 70, 60, 25);
		cmbmonths.setName("months");
		add(cmbmonths);
		
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
				cmbyears.setSelectedIndex(-1);
				cmbmonths.setSelectedIndex(-1);
				DefaultTableModel d1 = (DefaultTableModel) not_table.getModel();
				d1.setRowCount(0);
				clearFields();
			    txanote.setText("");				
			}
		});	
		
		lbtitle = hh.clabel("Title");
		lbtitle.setBounds(40, 40, 100, 25);
		epanel.add(lbtitle);
		
		
		txtitle = cTextField(25);
		txtitle.setBounds(150, 40, 250, 25);		
		epanel.add(txtitle);	
		txtitle.addKeyListener( hh.MUpper());
		
		lbnote = hh.clabel("Note");
		lbnote.setBounds(170, 90, 100, 25);
		epanel.add(lbnote);
		
		txaenote = new JTextArea();		
		txaenote.setMargin( new Insets(5,5,5,5) );
		txaenote.setFont(new java.awt.Font("Monospaced", 1, 18));
		txaenote.setLineWrap(true);
		txaenote.setWrapStyleWord(true);		
		txaenote.setBackground(new Color(255, 255, 255));
		txaenote.putClientProperty("caretAspectRatio", 0.1);	
		txaenote.setCaretColor(Color.RED);		
		jSPane2 = new JScrollPane();			
		jSPane2.setBounds(20, 130, 450, 250);
		jSPane2.setBorder(hh.borderf);
		jSPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jSPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jSPane2.setViewportView(txaenote);		
		epanel.add(jSPane2);
		
		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(50, 420, 120, 30);
		epanel.add(btnsave);
		
		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (Emp_idd >0) {
				    savebuttrun();
				}
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(180, 420, 120, 30);
		epanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
	       clearFields();
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBackground(hh.vkek);
		btndelete.setBounds(310, 420, 120, 30);
		epanel.add(btndelete);

		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel d1 = (DefaultTableModel) not_table.getModel();
				int sIndex = not_table.getSelectedRow();
				if (sIndex < 0) {
					return;
				}
				String iid = d1.getValueAt(sIndex, 0).toString();			
			dd.data_delete(not_table, "delete from noticetable where not_id =");
			  clearFields();
			  txanote.setText("");
			}
		});	
		
		not_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) not_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		not_table.setTableHeader(new JTableHeader(not_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(not_table);

		not_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				not_table.scrollRectToVisible(not_table.getCellRect(not_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane = new JScrollPane(not_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		not_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { },
				new String[] { "not_id", "emp_id", "Employee", "Title", "Date", "Time","Note" }));

		hh.setJTableColumnsWidth(not_table, 595, 0, 0, 40,30,18,12,0);
		jSPane.setViewportView(not_table);
		jSPane.getViewport().setBackground(hh.vvvzold);
		jSPane.setBounds(15, 20, 595, 220);
		jSPane.setBorder(hh.borderf);
		tpanel.add(jSPane);	
		not_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			tableMouseClicked(evt);
			}
		});			
		
		txanote = new JTextArea();
		txanote.setFont(new java.awt.Font("Monospaced", 1, 18));
		txanote.setLineWrap(true);
		txanote.setWrapStyleWord(true);
		txanote.setMargin( new Insets(5,5,5,5) );	
		txanote.putClientProperty("caretAspectRatio", 0.1);
		txanote.setEditable(false);
		txanote.setCaretColor(Color.RED);
		jSPane1 = new JScrollPane(txanote);	
		jSPane1.setBounds(90, 260, 450, 220);
		tpanel.add(jSPane1);	
		lbtnote = hh.clabel("Note");
		lbtnote.setBounds(30, 360,50,25);
		tpanel.add(lbtnote);		
		
		not_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) not_table.getModel();
				try {
					int row = not_table.getSelectedRow();
					if (row > -1) {
						String note = model.getValueAt(row, 6).toString();
						txanote.setText(note);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});
	}
	
	private void table_update(String what) {
		String Sql;	
		if (what == "") {
			Sql ="select a.not_id, a.emp_id, e.name as empname, a.title, a.date, a.time, a.note "
					+" from noticetable a join employees e on a.emp_id = e.emp_id order by empname , date, time";
		} else {
			Sql ="select a.not_id, a.emp_id, e.name as empname, a.title, a.date, a.time, a.note "
					+" from noticetable a join employees e on a.emp_id = e.emp_id "
					+ " where " + what + " order by empname, date, time ";		
		}
		ResultSet res = dh.GetData(Sql);
		not_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();

		String[] fej = { "not_id", "emp_id", "Employee", "Title", "Date", "Time","Note" };
		((DefaultTableModel) not_table.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(not_table, 595, 0, 0, 40, 30, 18, 12, 0);			
		hh.table_show(not_table);
		if (not_table.getRowCount() > 0) {
			int row = not_table.getRowCount() - 1;
			not_table.setRowSelectionInterval(row, row);
		}		
	}
	
	private void savebuttrun() {	
		String sql="";
		DefaultTableModel d1 = (DefaultTableModel) not_table.getModel();
		int emp_id =0;	
		String employee = "";
		if (cmbemployees.getSelectedItem() != null) {	
		    	Employee cemployee = (Employee) cmbemployees.getSelectedItem();
			     employee = String.valueOf(cemployee);
			}	
		
		String title = txtitle.getText();
		String note = txaenote.getText();
		String stime = hh.currenttime();
		String sdate = hh.currentDate();
		if (notvalidation(employee, title, note) == false) {
			 return;
		 }		
		if (rowid != "") {		
			sql = "update  noticetable set emp_id = " + Emp_idd  + ", title='" + title +"', note ='" 
			+ note +"', date = '" + sdate + "',  time = '" + stime + "' where not_id = " + rowid;
		} else {
			sql = "insert into noticetable (emp_id, title, note, date, time ) " + 
		      "values (" + Emp_idd +  ",'"+ title +"','" + note +"','"+ sdate +"','"+ stime+"')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(not_id) AS max_id from noticetable");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, Emp_idd,employee, title, sdate, stime,note});
					hh.gotolastrow(not_table);					
				} else {
					d1.setValueAt(Emp_idd, myrow, 1);		
					d1.setValueAt(employee, myrow, 2);	
					d1.setValueAt(title, myrow, 3);				
					d1.setValueAt(sdate, myrow, 4);	
					d1.setValueAt(stime, myrow, 5);
					d1.setValueAt(note,myrow,6);
				}
				txanote.setText(note);
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
		table_update(" a.emp_id =" + Emp_idd);
	}		
		
	private Boolean notvalidation(String employee, String title, String note){
		Boolean ret = true;
		 ArrayList<String> err = new ArrayList<String>();
		
		 if (!svad.employeevalid(employee)) {
			 err.add(svad.mess);			
		    	ret = false;
		}
		 if (!svad.titlevalid(title)) {
			 err.add(svad.mess);			
		    	ret = false;
		}		 
		 if (!svad.notevalid(note)) {				
			 err.add(svad.mess);			
	    	ret = false;
	}		
		 if (err.size() > 0) {
            JOptionPane.showMessageDialog(null, err.toArray(),"Error message",1);					               
        }			
		return ret;		
	}
	
	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		DefaultTableModel d1 = (DefaultTableModel) not_table.getModel();
		int row = not_table.getSelectedRow();
		if (row >= 0) {		
			rowid = not_table.getValueAt(row, 0).toString();
			myrow = row;
			String cnum = d1.getValueAt(row, 1).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbemployees, number);
			cmbemployees.updateUI();
			txtitle.setText(not_table.getValueAt(row, 3).toString());
			txaenote.setText(not_table.getValueAt(row, 6).toString());		
		}
	}
	
	  private void clearFields(){
		  txtitle.setText("");
		  txaenote.setText("");
		//  txanote.setText("");
	  }
		
		
	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
      //  textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}
	private void sqlgyart() {		
		String year = "";
		String month = "";

		Emp_idd = ((Employee) cmbemployees.getSelectedItem()).getEmp_id();
		
		if (cmbyears.getSelectedItem() != null) {		
			year = String.valueOf(cmbyears.getSelectedItem());
		}
		if (cmbmonths.getSelectedItem() != null) {
			month = String.valueOf(cmbmonths.getSelectedItem());
		}
	
		if (Emp_idd > 0) {
			String swhere = " a.emp_id =" + Emp_idd;
		
			if (!hh.zempty(year)) {
				swhere = swhere + " and strftime('%Y',a.date)= '" + year + "'";
			}		
			if (!hh.zempty(month)) {
				swhere = swhere + " and strftime('%m',a.date) = '" + month + "'";
			}
		
		   table_update(swhere);		
		}		
		
	}
	public static void main(String args[]) {
		Noticetable  not = new Noticetable();
		not.setSize(1150,700);
		not.setLayout(null);
		not.setLocationRelativeTo(null);
		not.setVisible(true);
	}
	Container cp;
	JPanel fejpanel, epanel, tpanel;
	JLabel lbheader, lbemployee, lbtitle, lbnote, lbtnote, lbyears,lbmonths;
	JTextField  txtitle;
	JScrollPane jSPane, jSPane2, jSPane1;
	JTable not_table;
	JButton btnsave, btncancel, btndelete, btnsearch, btnclear;
	JComboBox cmbemployees, cmbyears, cmbmonths;
	JTextArea txaenote, txanote;
}
