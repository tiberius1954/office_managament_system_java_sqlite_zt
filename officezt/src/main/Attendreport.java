package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.sql.ResultSetMetaData;

import classes.Employee;
import classes.Hhelper;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import net.proteanit.sql.DbUtils;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.border.*;

public class Attendreport extends JFrame {
	Connection con;
	PreparedStatement pst;
	Statement stmt;
	ResultSet rs;
	Color zold = new Color(125, 254, 127);
	Color rozsasz = new Color(255, 127, 126);
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();		
	String[] days = { "Employee", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "Total" };
	Object[][] data = null;
	
	 static String sev, sho, snho;
	static ArrayList<ArrayList> Arr2d = new ArrayList<ArrayList>();
	ArrayList<Integer> jelek = new ArrayList<Integer>();

	Attendreport() {
		super("Workdays of employees");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		initComponents();	
		try {
			dd.employeecombofill(cmbemployees);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  hh.iconhere(this);
		// table_update("");
		  Arr2d.clear();
	}

	private void initComponents() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		this.setLayout(null);
		setBounds(10, 10, 1180, 630);
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1180, 60);
		fejpanel.setBackground(hh.zold);
		
		lbheader= hh.flabel("WORKDAYS IN A MONTH");		
		lbheader.setBounds(400, 5, 400, 40);	
		fejpanel.add(lbheader);	
		add(fejpanel);		
		
		panel1 = new JPanel(null);
		panel1.setBounds(20, 80, 1126, 490);
		panel1.setBorder(hh.borderf);
		panel1.setBackground(hh.neonzold);
		add(panel1);			

		lbname = hh.clabel("Employee:");
		lbname.setBounds(180, 20, 100, 25);
		lbname.setHorizontalAlignment(JLabel.RIGHT);
		panel1.add(lbname);

		cmbemployees = hh.cbcombo();
		cmbemployees.setBounds(290, 20, 200, 25);
		cmbemployees.setName("employee");
		panel1.add(cmbemployees);

		lbyear = hh.clabel("Year:");
		lbyear.setBounds(500, 20, 45, 25);
		lbyear.setHorizontalAlignment(JLabel.RIGHT);
		panel1.add(lbyear);

		cmbyear = hh.cbcombo();
		cmbyear.setModel(new DefaultComboBoxModel(hh.yearso(0)));
		cmbyear.setBounds(555, 20, 90, 25);
		cmbyear.setName("year");
		panel1.add(cmbyear);

		lbmonth = hh.clabel("Month:");
		lbmonth.setBounds(655, 20, 60, 25);
		lbmonth.setHorizontalAlignment(JLabel.RIGHT);
		panel1.add(lbmonth);

		cmbmonth = hh.cbcombo();
		cmbmonth.setModel(new DefaultComboBoxModel(hh.monthso(0)));
		cmbmonth.setBounds(725, 20, 60, 25);
		panel1.add(cmbmonth);

		btnrajz = hh.cbutton("Show");
		btnrajz.setForeground(Color.black);
		btnrajz.setBackground(Color.magenta);
		btnrajz.setBounds(793, 19, 80, 27);
		btnrajz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		panel1.add(btnrajz);
		btnrajz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbyear.getSelectedIndex() != -1 && cmbmonth.getSelectedIndex() != -1) {
					sev = String.valueOf(cmbyear.getSelectedItem());								
					sho = String.valueOf(cmbmonth.getSelectedItem());						
					Arr2d.clear();
					kigyujtes();
				}
			}
		});
		model = new DefaultTableModel(data, days);
		tabla = new JTable(model) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		tabla.setBorder(hh.borderf2);
		Font fontt = new Font("tahoma", Font.BOLD, 12);
		tabla.setFont(fontt);
		tabla.setGridColor(Color.black);
		tabla.setShowGrid(true);
		tabla.setRowHeight(25);	
		tabla.setFocusable(false);			
//		tabla.setSelectionBackground(rozsasz);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JTableHeader header = tabla.getTableHeader();
		header.setBorder(hh.borderf2);
		Font font = new Font("tahoma", Font.BOLD, 12);
		header.setFont(font);
		header.setBackground(hh.sotetkek);
		header.setForeground(Color.white);
		// header.setForeground(Color.white);
		 header.setDefaultRenderer(new SimpleHeaderRenderer());
		hh.setJTableColumnsWidth(tabla, 1115, 18, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5,
				2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 2.5, 8);

		tabla.setShowVerticalLines(true);
		tabla.setDefaultRenderer(Object.class, new BorderColorRenderer());
		jScrollPane1 = new JScrollPane();
		jScrollPane1= new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jScrollPane1.setBounds(5, 90, 1115, 360);	
		jScrollPane1.setViewportView(tabla);	
		jScrollPane1.setColumnHeader(new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 32;
				return d;
			}
		});
		jScrollPane1.getViewport().setBackground(hh.vvvzold);
		panel1.add(jScrollPane1);
	}

	private void kigyujtes() {
		int iho, iday;	
		String sql;
		int empid = ((Employee) cmbemployees.getSelectedItem()).getEmp_id();		
		if (hh.zempty(sev) || hh.zempty(sho)) {
			return;
		}
		sql = " select a.emp_id, e.name as empname, a.date from attendence a "
		 		+ " join employees e on a.emp_id = e.emp_id ";
		if (empid >0) {
			 sql = sql +" where a.emp_id= '" +empid+"'"
					+ " and strftime('%Y',a.date) = '" + sev +"' and strftime('%m',a.date) = '" + sho + "'";					
		}else {
			sql = sql + " where strftime('%Y',a.date) = '" + sev +"' and strftime('%m',a.date) = '" + sho + "'";						
		}
		sql = sql + " order by empname, a.emp_id, a.date";	
			try {
			ResultSet rs = dh.GetData(sql);		
			while (rs.next()) {
				int eempid= rs.getInt("emp_id");
				String date = rs.getString("date");
				LocalDate idate = hh.stringtoldate(date);
				iho = idate.getMonthValue();
				iday = idate.getDayOfMonth();
				ArrayList row = new ArrayList();
				row.add(eempid);																																																																																																									
				row.add(iho);
				row.add(iday);
				//	System.out.println("Elements of ArrayList are:" + row);
				Arr2d.add(row);
			}
			dh.CloseConnection();	
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		} finally {
			model.setRowCount(0);
			int nap;
			if (Arr2d.size()<=0) {
				return;
			}
			int doid = (int) Arr2d.get(0).get(0);
			String nev="";
			int iid;
			int hossz = Arr2d.size();
			String[] hotomb = new String[33];
			Arrays.fill(hotomb, "");
			for (int i = 0; i < Arr2d.size(); i++) {
				iid = (int) Arr2d.get(i).get(0);
				if (iid!= doid) {			
					nev = hh.kerescombo(cmbemployees, doid);
					hotomb[0] = nev;	
					kiiras(hotomb);
					Arrays.fill(hotomb, "");	
					doid= iid;
				}	
				nap = (int) Arr2d.get(i).get(2);				
				hotomb[nap] = "1";	
				if (i==(hossz-1) ) {
					nev = hh.kerescombo(cmbemployees, iid);
					hotomb[0] = nev;	
					kiiras(hotomb);
				}
				}
		}
		}		

	private void kiiras(String[] tomb) {
		int szam = 0;	
		Vector<String> v2= new Vector<String>();
 	for (int i = 0; i < 32; i++) {	
			v2.add(tomb[i]);
			if (tomb[i].equals("1")){
				szam++;
			}			
		}
		tomb[32] = hh.itos(szam);
		v2.add(tomb[32]);		
		model.addRow(v2);
	}	
	
	public class SimpleHeaderRenderer extends JLabel implements TableCellRenderer {
		public SimpleHeaderRenderer() {
			setFont(new Font("Tahoma", Font.BOLD, 14));		
			setBorder(hh.border3);
			setHorizontalAlignment(JLabel.CENTER);
			setForeground(Color.white);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value.toString());
			return this;
		}
	}
	private static class BorderColorRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Border border;		
			Color vzold = new Color(102, 255, 102);
			Color ideg = new Color(255, 255, 255);
			border = BorderFactory.createMatteBorder(0, 2, 1, 0, Color.BLACK);		
			JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			comp.setBorder(border);
			((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);	
			comp.setForeground(Color.black);
			Boolean found = new Attendreport().isweekend(column);
             if (value.equals("1") && column!=32) {
			        comp.setBackground(Color.RED);				
             }else if (column == 0) {
				comp.setBackground(vzold);
             } else if (found== true){
            	 comp.setBackground(Color.ORANGE);	
			} else {
				comp.setBackground(Color.white);
			}
			return comp;
		}
	}	
	
	 Boolean   isweekend(int numday) {
		Boolean talalt = false;
		String snap =  hh.padLeftZeros(hh.itos(numday),2);
		String sdate = sev +"-"+ sho +"-"+ snap;		
		if (!hh.validatedate(sdate,"N")) {	
			return talalt;			
		}
		LocalDate dt = LocalDate.parse(sdate);
		  DayOfWeek dayOfWeek = dt.getDayOfWeek();
	        if ((dayOfWeek == DayOfWeek.SATURDAY) || (dayOfWeek == DayOfWeek.SUNDAY)){
	                	talalt = true;
	                }		
		return talalt;
	}
	
	public static void main(String args[]) {
	  Attendreport att = new Attendreport();		
		att.setVisible(true);
	}

	JComboBox cmbemployees, cmbyear, cmbmonth;
	JPanel panel1, fejpanel;
	JLabel lbname, lbyear, lbmonth, lbheader;
	JButton btnrajz;
	JTable tabla;
	private JScrollPane jScrollPane1;
	private DefaultTableModel model;
	Component cp;
}

