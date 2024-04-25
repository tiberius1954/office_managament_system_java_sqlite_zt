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

public class Attendenceb extends JFrame {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Depvalidation dvad = new Depvalidation();
	private String rowid = "";
	private int myrow = 0;
	private int Emp_idd = 0;
	public String Empname = "";

	Attendenceb() {
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
		setTitle("Employees attendence");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1230, 50);
		fejpanel.setBackground(hh.zold);

		lbheader = hh.flabel("A T T E N D E N C E");
		lbheader.setBounds(470, 5, 300, 40);
		fejpanel.add(lbheader);
		add(fejpanel);

		cards = new CardLayout();
		cardPanel = new JPanel(null);	
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
		// cards.show(cardPanel, "edit");
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
					Employee employee = (Employee) cmbemployees.getSelectedItem();
					Empname = employee.getName();
					lbemployername.setText(Empname);
					Emp_idd = employee.getEmp_id();

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
				lbtimesum.setText("");;
				DefaultTableModel d1 = (DefaultTableModel) att_table.getModel();
				d1.setRowCount(0);
			}
		});
		att_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) att_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		att_table.setTableHeader(new JTableHeader(att_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(att_table);

		att_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				att_table.scrollRectToVisible(att_table.getCellRect(att_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane1 = new JScrollPane(att_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		att_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "att_id", "emp_id",
				"Employee", "dep_id", "Department", "Date", "Timein", "Timeout", "Hours", " Status" }));
		hh.setJTableColumnsWidth(att_table, 1150, 0, 0, 25, 0, 25, 11, 8, 9, 7, 15);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		att_table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		att_table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		att_table.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);

		jSPane1.setViewportView(att_table);
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
				clearFields();
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
					// TODO Auto-generated catch block
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
		
		lbtimesum = hh.clabel("");
		lbtimesum.setFont(new Font("tahoma", Font.BOLD, 14));
		lbtimesum.setBorder(hh.borderf);
		lbtimesum.setHorizontalAlignment(JLabel.CENTER);
		lbtimesum.setBounds(910, 451, 80, 25);
		lbtimesum.setBackground(hh.vvvzold);
		lbtimesum.setForeground(new Color (0, 0, 0));
		lbtimesum.setOpaque(true);
		ttpanel.add(lbtimesum);
		
		lbtotal = hh.clabel("Total");
		lbtotal.setFont(new Font("tahoma", Font.BOLD, 14));
		lbtotal.setForeground(new Color (0, 0, 0));
		lbtotal.setBounds(830, 453, 70, 25);
		ttpanel.add(lbtotal);

		return ttpanel;
	}

	public JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		cPanel = new JPanel(null);
		cPanel.setBounds(375, 40, 450, 450);

		eepanel.add(cPanel);
		cPanel.setBackground(hh.neonzold);
		cPanel.setBorder(hh.borderf);

		eepanel.setSize(1200, 540);
		eepanel.setBackground(hh.neonzold);

		lbemployername = hh.clabel("");
		lbemployername.setBounds(130, 30, 250, 25);
		lbemployername.setOpaque(true);
		lbemployername.setBackground(Color.ORANGE);
		lbemployername.setHorizontalAlignment(SwingConstants.CENTER);
		lbemployername.setBorder(hh.myRaisedBorder);
		cPanel.add(lbemployername);

		lbdep = hh.clabel("Department");
		lbdep.setBounds(20, 80, 100, 25);
		cPanel.add(lbdep);

		cmbdepartments = hh.cbcombo();
		cmbdepartments.setName("department");
		cmbdepartments.setBounds(130, 80, 250, 25);
		cPanel.add(cmbdepartments);
		cmbdepartments.addFocusListener(cFocusListener);

		lbdate = hh.clabel("Date");
		lbdate.setBounds(20, 120, 100, 25);
		cPanel.add(lbdate);

		zdmodel = new SpinnerDateModel();
		sdate = hh.cspinner(zdmodel);
		hh.madexxx(sdate, "D");
		sdate.setBounds(130, 120, 120, 25);
		cPanel.add(sdate);

		lbtimein = hh.clabel("Time in");
		lbtimein.setBounds(20, 170, 100, 25);
		cPanel.add(lbtimein);

		zimodel = new SpinnerDateModel();
		stimein = hh.cspinner(zimodel);
		stimein.setBounds(130, 170, 70, 25);
		hh.madexxx(stimein, "T");
		cPanel.add(stimein);
		((JSpinner.DefaultEditor) stimein.getEditor()).getTextField().addFocusListener(sFocusListener);

		lbtimeout = hh.clabel("Time out");
		lbtimeout.setBounds(20, 210, 100, 25);
		cPanel.add(lbtimeout);

		zomodel = new SpinnerDateModel();
		stimeout = hh.cspinner(zomodel);
		stimeout.setBounds(130, 210, 70, 25);
		hh.madexxx(stimeout, "T");
		stimeout.setName("timeout");
		cPanel.add(stimeout);

		((JSpinner.DefaultEditor) stimeout.getEditor()).getTextField().addFocusListener(sFocusListener);
		// stimeout.addFocusListener(sFocusListener);

		lbhours = hh.clabel("Hours");
		lbhours.setBounds(20, 250, 100, 25);
		cPanel.add(lbhours);

		txhours = cTextField(25);
		txhours.setBounds(130, 250, 70, 25);
		txhours.setHorizontalAlignment(JTextField.RIGHT);
		txhours.setDisabledTextColor(Color.magenta);
		txhours.setEnabled(false);
		cPanel.add(txhours);

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(20, 290, 100, 25);
		cPanel.add(lbstatus);

		cmbstatus = hh.cbcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(hh.statusso()));
		cmbstatus.setName("status");
		cmbstatus.setBounds(130, 290, 250, 25);
		cPanel.add(cmbstatus);
		cmbstatus.addFocusListener(cFocusListener);

		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(190, 340, 120, 30);
		cPanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(190, 380, 120, 30);
		cPanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		return eepanel;
	}

	private final FocusListener sFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
		}

		@Override
		public void focusLost(FocusEvent e) {
			JComponent b = (JComponent) e.getSource();
			JTextField outtf = ((JSpinner.DefaultEditor) stimeout.getEditor()).getTextField();
			JTextField intf = ((JSpinner.DefaultEditor) stimein.getEditor()).getTextField();
			String intime = intf.getText();
			String outtime = outtf.getText();
			hh.datediff(intime, outtime);
			txhours.setText(hh.datediff(intime, outtime));
		}
	};

	private void savebuttrun() {
		DefaultTableModel d1 = (DefaultTableModel) att_table.getModel();
		String sql = "";
		String jel = "";
		int dep_id = 0;
		String department ="";
		String status = "";
	
		if (cmbdepartments.getSelectedItem() != null) {	
			department = String.valueOf(cmbdepartments.getSelectedItem());
			dep_id = ((Department) cmbdepartments.getSelectedItem()).getDep_id();  
			}
		if (cmbstatus.getSelectedItem() != null) {
			   status = String.valueOf(cmbstatus.getSelectedItem());
		}
	    String hours = txhours.getText();	
		String timein = hh.timetostring((Date) stimein.getValue());
		String timeout = hh.timetostring((Date) stimeout.getValue());
		String ssdate = hh.datetostring((Date) sdate.getValue());	
	
		if (attvalidation(hours, department, status) == false) {
			return;
		}

		if (rowid != "") {
			jel = "UP";
			sql = "update  attendence set emp_id= '" + Emp_idd + "', dep_id= " + dep_id + "," + "date = '" + ssdate
					+ "', time_in = '" + timein + "'," + "time_out= '" + timeout + "', status= '" + status + "',"
					+ "hours = '" + hours + "' where att_id = " + rowid;
		} else {
			sql = "insert into attendence (emp_id, dep_id, date, time_in, time_out, status, hours) " + "values ("
					+ Emp_idd + "," + dep_id + ",'" + ssdate + "','" + timein + "','" + timeout + "','" + status + "','"
					+ hours + "')";
		}
		
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(att_id) AS max_id from attendence");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, Emp_idd, Empname, dep_id, department, ssdate,
							timein, timeout, hours, status });
					hh.gotolastrow(att_table);
					if (att_table.getRowCount() > 0) {
						int row = att_table.getRowCount() - 1;
						att_table.setRowSelectionInterval(row, row);
					}
				} else {
					d1.setValueAt(Emp_idd, myrow, 1);
					d1.setValueAt(Empname, myrow, 2);
					d1.setValueAt(dep_id, myrow, 3);
					d1.setValueAt(department, myrow, 4);
					d1.setValueAt(ssdate, myrow, 5);
					d1.setValueAt(timein, myrow, 6);
					d1.setValueAt(timeout, myrow, 7);
					d1.setValueAt(hours, myrow, 8);
					d1.setValueAt(status, myrow, 9);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql hiba");
		}
		countoftimes();
		clearFields();
		cards.show(cardPanel, "tabla");
	}

	private void data_update() throws SQLException {
		DefaultTableModel d1 = (DefaultTableModel) att_table.getModel();
		int row = att_table.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			ResultSet rs = dd.getAttendence(rowid);
			while (rs.next()) {
				txhours.setText(rs.getString("hours"));
				String cnum = rs.getString("dep_id");
				int number = 0;
				if (!hh.zempty(cnum)) {
					number = Integer.parseInt(cnum);
				}
				hh.setSelectedValue(cmbdepartments, number);
				cmbdepartments.updateUI();
				cmbstatus.setSelectedItem(rs.getString("status"));
				String sdate = rs.getString("date");
				zdmodel.setValue(hh.stringtodate(sdate));
				String timein = rs.getString("time_in");
				zimodel.setValue(hh.stringtotime(timein));
				String timeout = rs.getString("time_out");
				zomodel.setValue(hh.stringtotime(timeout));
			}
			dh.CloseConnection();
			cards.show(cardPanel, "edit");
			cmbdepartments.requestFocus();			
		}
	}

	private int data_delete() {
		String sql = "delete from attendence  where att_id =";
		Boolean error = false;
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) att_table.getModel();
		int sIndex = att_table.getSelectedRow();
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
		countoftimes();
		return flag;
	}

	private void clearFields() {
		txhours.setText("");
		String cdate = hh.currentDate();
		zdmodel.setValue(hh.stringtodate(cdate));
		Date dtime = hh.stringtotime("00:00");
		zimodel.setValue(dtime);
		zomodel.setValue(dtime);
		myrow = 0;
		rowid = "";
		hh.setSelectedValue(cmbdepartments, 0);		;
		cmbdepartments.updateUI();
		cmbdepartments.setSelectedIndex(-1);
		cmbstatus.setSelectedIndex(-1);
		att_table.clearSelection();
	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select a.att_id, a.emp_id, e.name as ename , a.dep_id, d.name as depname, a.date, a.time_in, a.time_out,"
					+ " a.hours,  a.status from attendence a  join employees e on a.emp_id = e.emp_id "
					+ "join department d on a.dep_id = d.dep_id  order by ename, a.date, a.time_in";
		} else {
			Sql = "select a.att_id, a.emp_id, e.name as ename, a.dep_id, d.name as depname, a.date, a.time_in, a.time_out,  "
					+ " a.hours, a.status from attendence a join employees e on a.emp_id = e.emp_id "
					+ "join department d on a.dep_id = d.dep_id  where " + what + " order by ename, a.date, a.time_in ";
		}
		ResultSet res = dh.GetData(Sql);
		att_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "att_id", "emp_id", "Employee", "dep_id", "Department", "Date", "Timein", "Timeout", "Hours",
				" Status" };
		((DefaultTableModel) att_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(att_table, 1150, 0, 0, 25, 0, 25, 11, 8, 9, 7, 15);

		hh.table_show(att_table);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		att_table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		att_table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		att_table.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
		
		if (att_table.getRowCount() > 0) {
			int row = att_table.getRowCount() - 1;
			att_table.setRowSelectionInterval(row, row);
		}
		countoftimes();
	}

	private void sqlgyart() {
		Employee employee = (Employee) cmbemployees.getSelectedItem();
		int emp_id = employee.getEmp_id();
		String year = "";
		String month = "";
		if (cmbyears.getSelectedItem() != null) {		
			year = String.valueOf(cmbyears.getSelectedItem());
		}
		if (cmbmonths.getSelectedItem() != null) {
			month = String.valueOf(cmbmonths.getSelectedItem());
		}
		if (emp_id > 0) {
			String swhere = " a.emp_id ='" + emp_id +"'";
			if (!hh.zempty(year)) {
				swhere = swhere + " and strftime('%Y',date)= '" + year + "'";
			}
			if (!hh.zempty(month)) {
				swhere = swhere + " and strftime('%m',date) = '" + month + "'";
			}
		   table_update(swhere);		
		}
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
			if (bb.getName() == "status") {
				String status = String.valueOf( cmbstatus.getSelectedItem());
				ret = dvad.statusvalid(status);
			} else if (bb.getName() == "department") {				
				Department cdepartment = (Department) cmbdepartments.getSelectedItem();
				String department = String.valueOf(cdepartment);
				ret = dvad.departmentvalid(department);
			}
			if (ret == true) {
				b.setBorder(hh.borderf);
			} else {
				b.setBorder(hh.borderp);
			}
		}
	};

	private Boolean attvalidation(String hours, String department, String status) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (!dvad.departmentvalid(department)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (!dvad.hoursvalid(hours)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (!dvad.statusvalid(status)) {
			err.add(dvad.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
		// textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}
	private void countoftimes() {
		 ArrayList<String> stimeList = new ArrayList<String>();			
		    for (int i = 0; i < att_table.getRowCount(); i++) { 			    	
		    	String item = String.valueOf(att_table.getValueAt(i, 8));
		    	if (! hh.zempty(item)){
		        stimeList.add(item);	
		    	}
		     }
		    long tm = 0;
	        for (String tmp : stimeList){
	            String[] arr = tmp.split(":");	    
	            tm += 60 * Integer.parseInt(arr[1]);
	            tm += 3600 * Integer.parseInt(arr[0]);
	        }
	        long hhh = tm / 3600;
	        tm %= 3600;
	        long mm = tm / 60;
	        tm %= 60;
	        long ss = tm;
	        String txt = hh.tformat(hhh) + ":" + hh.tformat(mm);
	        lbtimesum.setText(txt);		    
	}
	
	public static void main(String args[]) {
		Attendenceb att = new Attendenceb();
		att.setSize(1230, 650);
		att.setLayout(null);
		att.setLocationRelativeTo(null);
		att.setVisible(true);
	}

	CardLayout cards;
	JPanel cardPanel, tPanel, ePanel, fejpanel, cPanel;
	JScrollPane jSPane1;
	JTable att_table;
	JComboBox cmbemployees, cmbyears, cmbmonths, cmbdepartments, cmbstatus;
	JLabel lbemp, lbdep, lbyears, lbmonths, lbheader, lbdate, lbtimein, lbtimeout, lbstatus, lbhours;
	JLabel lbpicture, lbsearch, lbemployername, lbtimesum,lbtotal;
	JTextField txhours;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel, btnsendto, btnclear, btnsearch;
	Container cp;
	SpinnerDateModel zimodel, zdmodel, zomodel;
	private JSpinner stimein, sdate, stimeout;

}
