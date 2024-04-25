package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import classes.Department;
import classes.Depvalidation;
import classes.Employee;
import classes.Empvalidation;
import classes.Hhelper;
import net.proteanit.sql.DbUtils;

public class Leave extends JFrame{
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();	
	Depvalidation dvad = new Depvalidation();
	JDateChooser startdate = new JDateChooser(new Date());
	JDateChooser enddate = new JDateChooser(new Date());
	private String rowid = "";
	private int myrow = 0;
	private int Emp_idd = 0;
	public  String Empname ="";
	Leave(){
		initcomponents();	
		try {
			dd.employeecombofill(cmbemployees);
			dd.departmentcombofill(cmbdepartments);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hh.iconhere(this);
	}
	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (hh.whichpanel(cardPanel) == "tabla") {				
					dispose();
				} else if (hh.whichpanel(cardPanel) == "edit") {
					cards.show(cardPanel, "tabla");
				}			
			}
		});
		setTitle("Employees leave");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1230, 50);
		fejpanel.setBackground(hh.zold);

		lbheader = hh.flabel("L E A V E");
		lbheader.setBounds(550, 5, 300, 40);
		fejpanel.add(lbheader);
		add(fejpanel);

		cards = new CardLayout();
		cardPanel = new JPanel(null);
		// cardPanel.setBorder(hh.line);
		// cardPanel.setBorder(hh.ztroundborder(Color.yellow));
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 50, 1200, 570);
		tPanel = maketpanel();
		tPanel.setName("tabla");
		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(tPanel, "tabla");
		cardPanel.add(ePanel, "edit");
		add(cardPanel);
		cards.show(cardPanel, "tabla");
	//	cards.show(cardPanel, "edit");
	}
	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setSize(1200, 540);
		ttpanel.setBackground(hh.neonzold);
		lbemp = hh.clabel("Employee");
		lbemp.setBounds(180, 20, 100, 25);
		ttpanel.add(lbemp);

		cmbemployees = hh.cbcombo();
		cmbemployees.setBounds(290, 20, 200, 25);
		ttpanel.add(cmbemployees);		
		cmbemployees.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cmbemployees.getSelectedItem() != null) {
				       	Employee employee = (Employee) cmbemployees.getSelectedItem();
				    	Empname =employee.getName();	
					     lbemployername.setText(Empname);
				           Emp_idd = employee.getEmp_id();
					}					 
				}
			}
		});	
		lbyears = hh.clabel("Year");
		lbyears.setBounds(490, 20, 50, 25);
		ttpanel.add(lbyears);

		cmbyears = hh.cbcombo();
		cmbyears.setModel(new DefaultComboBoxModel(hh.yearso(1)));
		cmbyears.setBounds(550, 20, 80, 25);
		cmbyears.setName("years");
		ttpanel.add(cmbyears);

		lbmonths = hh.clabel("Month");
		lbmonths.setBounds(630, 20, 60, 25);
		ttpanel.add(lbmonths);

		cmbmonths = hh.cbcombo();
		cmbmonths.setModel(new DefaultComboBoxModel(hh.monthso(1)));
		cmbmonths.setBounds(700, 20, 60, 25);
		cmbmonths.setName("months");
		ttpanel.add(cmbmonths);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(770, 20, 90, 25);
		ttpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		btnclear = hh.cbutton("Clear");
		btnclear.setBounds(863, 20, 90, 25);
		btnclear.setForeground(Color.black);
		btnclear.setBackground(hh.lpiros);
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
			   cmbemployees.setSelectedIndex(-1);				
				hh.setSelectedValue(cmbemployees, 0);
				cmbemployees.updateUI();
				cmbyears.setSelectedIndex(-1);
				cmbmonths.setSelectedIndex(-1);
				DefaultTableModel d1 = (DefaultTableModel) lea_table.getModel();
				d1.setRowCount(0);				
			}
		});
		lea_table = hh.ztable();
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

		hh.madeheader(lea_table);

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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);	

		jSPane1.setViewportView(lea_table);
		jSPane1.getViewport().setBackground(hh.vvvzold);
		jSPane1.setBounds(20, 70, 1155, 380);
		jSPane1.setBorder(hh.borderf);
		ttpanel.add(jSPane1);

		btnnew = hh.cbutton("New");
		btnnew.setBounds(400, 490, 130, 30);
		// btnnew.setBackground(hh.vpiros1);
		btnnew.setBackground(hh.vpiros1);
		ttpanel.add(btnnew);

		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Employee cemployee = (Employee) cmbemployees.getSelectedItem();
				Emp_idd = cemployee.getEmp_id();
				if (Emp_idd < 1) {
					return;
				}
				Empname = cemployee.getName();
				lbemployername.setText(Empname);
				cards.show(cardPanel, "edit");
			//	clearFields();
				cmbdepartments.requestFocus();
			}
		});

		btnupdate = hh.cbutton("Update");
		btnupdate.setBounds(540, 490, 130, 30);
		btnupdate.setBackground(hh.zold);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					data_update();
				} catch (SQLException e) {			
					e.printStackTrace();
				}
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(680, 490, 130, 30);
		btndelete.setBackground(hh.vkek);
		ttpanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});
		return ttpanel;
	}
	
	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select a.le_id, a.emp_id, e.name as ename , a.dep_id, d.name as depname, a.type, a.start, a.end,"
					+ " a.noofdays  from leave a  join employees e on a.emp_id = e.emp_id "
					+ "join department d on a.dep_id = d.dep_id  order by ename, a.start";
		} else {
			Sql = "select a.le_id, a.emp_id, e.name as ename, a.dep_id, d.name as depname, a.type, a.start, a.end,  "
					+ " a.noofdays from leave a join employees e on a.emp_id = e.emp_id "
					+ "join department d on a.dep_id = d.dep_id  where " + what + " order by ename, a.start ";
		}	

		ResultSet res = dh.GetData(Sql);
    	lea_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();		
		
		String[]  fej = { "le_id", "emp_id","Employee", "dep_id", "Department", "Type", "Start", "End", "No of days" };	
		((DefaultTableModel) lea_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(lea_table, 1155, 0, 0, 20, 0, 20, 20, 14, 14, 12);
		
	
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) lea_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);		

     	lea_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				lea_table.scrollRectToVisible(lea_table.getCellRect(lea_table.getRowCount() - 1, 0, true));
			}
		});
		if (lea_table.getRowCount() > 0) {
			int row = lea_table.getRowCount() - 1;
			lea_table.setRowSelectionInterval(row, row);
		}		
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
	
	
	public JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		cPanel = new JPanel(null);
		cPanel.setBounds(375, 40, 450, 430);
		
		eepanel.add(cPanel);
		cPanel.setBackground(hh.neonzold);
		cPanel.setBorder(hh.borderf);

		eepanel.setSize(1200, 540);
		eepanel.setBackground(hh.neonzold);	
		 lbemployername = hh.clabel("");	
		 lbemployername.setBounds(130,30,250,25);	
		 lbemployername.setOpaque(true);
		 lbemployername.setBackground(Color.ORANGE);
		 lbemployername.setHorizontalAlignment(SwingConstants.CENTER);
		 lbemployername.setBorder(hh.myRaisedBorder);
		 cPanel.add( lbemployername);

		lbdep = hh.clabel("Department");
		lbdep.setBounds(20, 80, 100, 25);
		cPanel.add(lbdep);

		cmbdepartments = hh.cbcombo();
		cmbdepartments.setName("department");
		cmbdepartments.setBounds(130, 80, 250, 25);
		cPanel.add(cmbdepartments); 
		cmbdepartments.addFocusListener(cFocusListener);
		
		lbtypes = hh.clabel("Type");
		lbtypes.setBounds(20, 120, 100, 25);
		cPanel.add(lbtypes);
		
		cmbtypes = hh.cbcombo();
		cmbtypes.setModel(new DefaultComboBoxModel(hh.leaveso()));
		cmbtypes.setName("type");
		cmbtypes.setBounds(130, 120, 250, 25);
		cPanel.add(cmbtypes);
		cmbtypes.addFocusListener(cFocusListener);
		
		lbstartdate = hh.clabel("Startdate");
		lbstartdate.setBounds(20, 160, 100, 25);
		cPanel.add(lbstartdate);

		startdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		startdate.setDateFormatString("yyyy-MM-dd");
		startdate.setFont(new Font("Arial", Font.BOLD, 16));
		startdate.setBounds(130, 160, 250, 25);
		cPanel.add(startdate);
		startdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					countd();
				}
			}
		});
		
		lbenddate = hh.clabel("Enddate");
		lbenddate.setBounds(20, 200, 100, 25);
		cPanel.add(lbenddate);

		enddate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		enddate.setDateFormatString("yyyy-MM-dd");
		enddate.setFont(new Font("Arial", Font.BOLD, 16));
		enddate.setBounds(130, 200, 250, 25);
		cPanel.add(enddate);
		enddate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					countd();
				}
			}
		});

			
		lbnoofdays = hh.clabel("No of days");
		lbnoofdays.setBounds(20, 240, 100, 25);	
		cPanel.add(lbnoofdays);
		
		txnoofdays = cTextField(25);
		txnoofdays.setBounds(130, 240, 250, 25);
		txnoofdays.setHorizontalAlignment(JTextField.RIGHT);
		txnoofdays.setDisabledTextColor(Color.magenta);	
		cPanel.add(txnoofdays);
		txnoofdays.addKeyListener(hh.Onlynum());
		
		
		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(190, 300, 120, 30);
		cPanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			  savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(190, 350, 120, 30);
		cPanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			clearFields();
			}
		});		
		return eepanel;
	}	
	
	private void savebuttrun() {
		String sql = "";
		String jel = "";
		String department ="";
		int dep_id =0;
		
		DefaultTableModel d1 = (DefaultTableModel) lea_table.getModel();
		String noofdays = txnoofdays.getText();
		String type ="";
		if (cmbdepartments.getSelectedItem() != null) {	
		         type =  String.valueOf(cmbtypes.getSelectedItem());		
		}
		if (cmbdepartments.getSelectedItem() != null) {	
			department = String.valueOf(cmbdepartments.getSelectedItem());
			dep_id = ((Department) cmbdepartments.getSelectedItem()).getDep_id();  
			}	
		
		String sdate = ((JTextField) startdate.getDateEditor().getUiComponent()).getText();
		String edate = ((JTextField) enddate.getDateEditor().getUiComponent()).getText();		
	
		if (leavalidation(noofdays, department, type) == false) {
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  leave set emp_id= '" + Emp_idd + "', dep_id= " + dep_id + "," + "type = '" + type
					+ "', start = '" + sdate + "'," + "end= '" + edate + "', noofdays= '" + noofdays
					+ "' where le_id = " + rowid;
		} else {
			sql = "insert into leave (emp_id, dep_id, type, start, end, noofdays) " + "values ("
					+ Emp_idd + "," + dep_id + ",'" + type + "','" + sdate + "','" + edate + "','" + noofdays + "')";
		}
	
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(le_id) AS max_id from leave");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, Emp_idd, Empname, dep_id, department, type,
							sdate, edate, noofdays });
					hh.gotolastrow(lea_table);
					if (lea_table.getRowCount() > 0) {
						int row = lea_table.getRowCount() - 1;
						lea_table.setRowSelectionInterval(row, row);
					}
				} else {
					d1.setValueAt(Emp_idd, myrow, 1);
					d1.setValueAt(Empname, myrow, 2);
					d1.setValueAt(dep_id, myrow, 3);
					d1.setValueAt(department, myrow, 4);
					d1.setValueAt(type, myrow, 5);
					d1.setValueAt(sdate, myrow, 6);
					d1.setValueAt(edate, myrow, 7);
					d1.setValueAt(noofdays, myrow, 8);				
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql hiba");
		}
		clearFields();
		cards.show(cardPanel, "tabla");
	}
	private Boolean leavalidation(String noofdays, String department, String type){
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (!dvad.departmentvalid(department)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (!dvad.noofdaysvalid(noofdays)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (!dvad.statusvalid(type)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}

	private void data_update() throws SQLException {	
		DefaultTableModel d1 = (DefaultTableModel) lea_table.getModel();
		int row = lea_table.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			ResultSet rs = dd.getLeave(rowid);
			while (rs.next()) {
				txnoofdays.setText(rs.getString("noofdays"));
				String cnum = rs.getString("dep_id");
				int number = 0;
				if (!hh.zempty(cnum)) {
					number = Integer.parseInt(cnum);
				}
				hh.setSelectedValue(cmbdepartments, number);
				cmbdepartments.updateUI();			
				cmbtypes.setSelectedItem(rs.getString("type"));				
				Date date;
				try {
					String dd = rs.getString("start");					
					if (!hh.zempty(dd)) {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						startdate.setDate(date);
					} else {
						startdate.setCalendar(null);
					}
					dd = rs.getString("end");						
					if (!hh.zempty(dd)) {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						enddate.setDate(date);
					} else {
						enddate.setCalendar(null);
					}
				} catch (ParseException e) {			
					e.printStackTrace();
				}	
			}
			dh.CloseConnection();
			cards.show(cardPanel, "edit");
			cmbdepartments.requestFocus();
		}
	}
	private int data_delete() {
		String sql = "delete from leave  where le_id =";
		Boolean error = false;
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) lea_table.getModel();
		int sIndex = lea_table.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag == 1)
				d1.removeRow(sIndex);
		}
		return flag;
	}
	
	private void clearFields() {
		txnoofdays.setText("");
		cmbtypes.setSelectedIndex(-1);	
		startdate.setCalendar(null);
		enddate.setCalendar(null);
		myrow = 0;
		rowid = "";
		hh.setSelectedValue(cmbdepartments, 0);
		cmbdepartments.updateUI();
		 //lea_table.clearSelection();		
	}
	private void countd() {
		String sdate = ((JTextField) startdate.getDateEditor().getUiComponent()).getText();
		String vdate = ((JTextField) enddate.getDateEditor().getUiComponent()).getText();
		String napok = hh.countdays(sdate, vdate);
		txnoofdays.setText(napok);
		return;
	}
	private final FocusListener cFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBorder(hh.borderz);
		}

		@Override
		public void focusLost(FocusEvent e) {
			boolean ret = true;
			JComponent b = (JComponent) e.getSource();
			JComboBox bb = (JComboBox) e.getSource();
			if (bb.getName() == "type") {
				String status = String.valueOf(cmbtypes.getSelectedItem());
				ret = dvad.statusvalid(status);
			} else if (bb.getName() == "department") {
				String department = String.valueOf(cmbdepartments.getSelectedItem());
				ret = dvad.departmentvalid(department);
			}
			if (ret == true) {
				b.setBorder(hh.borderf);
			} else {
				b.setBorder(hh.borderp);
			}
		}
	};
	 private final FocusListener dFocusListener = new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				JComponent c = (JComponent) e.getSource();
				c.setBorder(hh.borderz);
			}
			@Override
			public void focusLost(FocusEvent e) {
				String content = "";
				boolean ret = true;
				JTextField Txt = (JTextField) e.getSource();
				content = Txt.getText();		
				if (Txt == txnoofdays) {
					Txt.setText(content);
					 ret = dvad.noofdaysvalid(content);
				}							
				if (ret == true) {
					Txt.setBorder(hh.borderf);
				} else {
					Txt.setBorder(hh.borderp);
				}
			}
		};


	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
	   textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}	
	
	public static void main(String args[]) {
		Leave lea = new Leave();
		lea.setSize(1230, 650);
		lea.setLayout(null);
		lea.setLocationRelativeTo(null);
		lea.setVisible(true);
	}
	
	CardLayout cards;
	JPanel cardPanel, tPanel, ePanel, fejpanel, cPanel;
	JScrollPane jSPane1;
	JTable lea_table;
	JComboBox cmbemployees, cmbyears, cmbmonths, cmbdepartments, cmbtypes;
	JLabel lbemp, lbdep, lbyears, lbmonths, lbheader, lbstartdate, lbenddate, lbtypes, lbnoofdays;
	JLabel lbpicture, lbsearch, lbemployername;
	JTextField txnoofdays;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel, btnsendto, btnclear, btnsearch;
	Container cp;
}
