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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.Date;
import java.util.stream.Collectors;

import com.toedter.calendar.JDateChooser;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import classes.Department;
import classes.Depvalidation;
import classes.Employee;
import classes.Task;
import classes.Empvalidation;
import classes.Hhelper;
import net.proteanit.sql.DbUtils;

public class Taskmanage extends JFrame {
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
	public int Man_idd = 0;
	public String Empname = "";
	JFrame myframe = this;
	String Departs="";

	JDateChooser duedate = new JDateChooser(new Date());

	Taskmanage() {
		initcomponents();
		try {
			dd.employeecombofill(cmbemployees);			
			dd.managerscombofill(cmbmanagers);
			dd.taskscombofill(cmbtasks);
			dd.taskscombofill(cmbetasks);	
			//table_update("");

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
		setTitle("Manage tasks");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1240, 50);
		fejpanel.setBackground(hh.zold);

		lbheader = hh.flabel("MANAGE TASKS");
		lbheader.setBounds(470, 5, 300, 40);
		fejpanel.add(lbheader);
		add(fejpanel);

		cards = new CardLayout();
		cardPanel = new JPanel(null);	
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 50, 1205, 540);
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
		ttpanel.setBounds(0, 0, 1000, 520);
		ttpanel.setBackground(hh.neonzold);

		lbmanager = hh.clabel("Taskmanager");
		lbmanager.setBounds(0, 20, 115, 25);
		ttpanel.add(lbmanager);

		cmbmanagers = hh.cbcombo();
		cmbmanagers.setBounds(125, 20, 200, 25);
		cmbmanagers.setName("manager");
		ttpanel.add(cmbmanagers);
		cmbmanagers.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (cmbmanagers.getSelectedItem() != null) {				
					   Man_idd =	((Employee) cmbmanagers.getSelectedItem()).getEmp_id();	
					   if (Man_idd>0) {
				        	   Departs = listtostring();
				        	   try {
								dd.employeecombofillta(cmbeemployees,Departs);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					   }
				}
			}}
		});

		lbtasks = hh.clabel("Tasks");
		lbtasks.setBounds(335, 20, 50, 25);
		ttpanel.add(lbtasks);

		cmbtasks = hh.cbcombo();
		cmbtasks.setBounds(395, 20, 200, 25);
		cmbtasks.setName("task");
		ttpanel.add(cmbtasks);

		lbemp = hh.clabel("Assign to");
		lbemp.setBounds(605, 20, 80, 25);
		ttpanel.add(lbemp);

		cmbemployees = hh.cbcombo();
		cmbemployees.setBounds(700, 20, 200, 25);
		cmbemployees.setName("employee");
		ttpanel.add(cmbemployees);
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
		lbyears.setBounds(910, 20, 40, 25);
		ttpanel.add(lbyears);

		cmbyears = hh.cbcombo();
		cmbyears.setModel(new DefaultComboBoxModel(hh.yearso(1)));
		cmbyears.setBounds(960, 20, 70, 25);
		cmbyears.setName("years");
		ttpanel.add(cmbyears);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(1040, 20, 70, 25);
		ttpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		btnclear = hh.cbutton("Clear");
		btnclear.setBounds(1120, 20, 70, 25);
		btnclear.setForeground(Color.black);
		btnclear.setBackground(hh.lpiros);
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbemployees.setSelectedIndex(-1);
				hh.setSelectedValue(cmbemployees, 0);
				cmbmanagers.updateUI();
				hh.setSelectedValue(cmbmanagers, 0);		
				cmbmanagers.updateUI();
				cmbyears.setSelectedIndex(-1);
				DefaultTableModel d1 = (DefaultTableModel) ts_table.getModel();
				d1.setRowCount(0);
				txanote.setText("");
			}
		});
		ts_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) ts_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		ts_table.setTableHeader(new JTableHeader(ts_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		ts_table.setIntercellSpacing(new java.awt.Dimension(5, 5));

		hh.madeheader(ts_table);

		ts_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ts_table.scrollRectToVisible(ts_table.getCellRect(ts_table.getRowCount() - 1, 0, true));
			}
		});	

		jSPane1 = new JScrollPane(ts_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ts_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { 
			"task_id", "emp_id","Assign to", "tco_id", "Task Name", "Due date", "Status", "Priority", 
			"Phase %","Note" }));
		hh.setJTableColumnsWidth(ts_table, 1155, 0, 0, 20, 0, 20, 15, 15, 15, 15,0);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		jSPane1.setViewportView(ts_table);
		jSPane1.getViewport().setBackground(hh.vvvzold);
		jSPane1.setBounds(20, 70, 1155, 200);
		jSPane1.setBorder(hh.borderf);
		ttpanel.add(jSPane1);

		lbnote = hh.clabel("Note");
		lbnote.setBounds(200, 310, 130, 30);
		ttpanel.add(lbnote);

		jSPane2 = new JScrollPane();
		txanote = new JTextArea();		
		txanote.setMargin( new Insets(5,5,5,5) );	
		txanote.setFont(new java.awt.Font("Monospaced", 1, 18));
		txanote.setLineWrap(true);
		txanote.setWrapStyleWord(true);
		jSPane2.setBounds(350, 285, 500, 180);
		jSPane2.setBorder(hh.borderf);
		jSPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jSPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		jSPane2.setViewportView(txanote);
		txanote.setEditable(false);
		ttpanel.add(jSPane2);

		ts_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) ts_table.getModel();
				try {
					int row = ts_table.getSelectedRow();
					if (row > -1) {
						String note = model.getValueAt(row, 9).toString();
						txanote.setText(note);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		btnnew = hh.cbutton("New");
		btnnew.setBounds(400, 490, 130, 30);	
		btnnew.setBackground(hh.vpiros1);
		ttpanel.add(btnnew);

		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				Employee cemployee = (Employee) cmbmanagers.getSelectedItem();
				Man_idd = cemployee.getEmp_id();
				if (Man_idd < 1) {
					return;
				}		
				cards.show(cardPanel, "edit");
			  	cmbeemployees.requestFocus();
				clearFields();
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
				String sql = "delete from task_table  where task_id =";
				dd.data_delete(ts_table, sql);
				txanote.setText("");
			}
		});

		return ttpanel;
	}
	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select a.task_id,  a.emp_id, e.name as ename, a.tco_id, t.name as tname , a.duedate, a.status, a.priority,"
					+ " a.phase,  a.notes  from task_table a  join employees e on a.emp_id = e.emp_id "
					+ "join taskcode t on a.tco_id = t.tco_id  order by ename";
		} else {
			Sql = "select a.task_id, a.emp_id, e.name as ename, a.tco_id, t.name as tname, a.duedate, a.status, a.priority,  "
					+ " a.phase, a.notes from task_table a join employees e on a.emp_id = e.emp_id "
					+ "join taskcode t on a.tco_id = t.tco_id  where " + what + " order by ename ";
		}
		ResultSet res = dh.GetData(Sql);
		ts_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej ={ "task_id", "emp_id","Assign to", "tco_id", "Task Name", "Due date", "Status", "Priority", 
				"Phase %","Note"};	
		((DefaultTableModel) ts_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ts_table, 1155, 0, 0, 20, 0, 20, 15, 15, 15, 15, 0);
		hh.table_show(ts_table);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		ts_table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		ts_table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);	
		ts_table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);	
		
		if (ts_table.getRowCount() > 0) {
			int row = ts_table.getRowCount() - 1;
			ts_table.setRowSelectionInterval(row, row);
		}		
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBounds(0, 0, 1000, 520);
		eepanel.setBackground(hh.neonzold);
		lPanel = new JPanel(null);
		lPanel.setBounds(200, 40, 400, 440);
		lPanel.setBackground(hh.neonzold);
		lPanel.setBorder(hh.borderf);
		eepanel.add(lPanel);
		
		lbemployee = hh.clabel("Assign to");		
		lbemployee.setBounds(10,30,130,25);
		lPanel.add(lbemployee);
		
		cmbeemployees = hh.cbcombo();
		cmbeemployees.setName("employee");
		cmbeemployees.setBounds(150, 30, 220, 25);
		lPanel.add(cmbeemployees);
		cmbeemployees.addFocusListener(cFocusListener);
		
		lbetask = hh.clabel("Task");
		lbetask.setBounds(10,70,130, 25);
		lPanel.add(lbetask);
		
		cmbetasks = hh.cbcombo();
		cmbetasks.setName("task");
		cmbetasks.setBounds(150, 70, 220, 25);
		lPanel.add(cmbetasks);
	    cmbetasks.addFocusListener(cFocusListener);
	    
	    btnnewtask = hh.cbutton("New task");
		btnnewtask.setBackground(hh.vpiros1);
		btnnewtask.setBounds(200, 110, 120, 25);
		lPanel.add(btnnewtask);

		btnnewtask.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Tasks tas = new Tasks(myframe);
				tas.setSize(700,410);
				tas.setLayout(null);
				tas.setLocationRelativeTo(null);
				tas.setVisible(true);	
			}
		});		
		
		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(10,150,130,25);
		lPanel.add(lbstatus);
		
		cmbstatus = hh.cbcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(hh.tstatus()));	
		cmbstatus.setName("status");
		cmbstatus.setBounds(150, 150, 220, 25);
		lPanel.add(cmbstatus);
		cmbstatus.addFocusListener(cFocusListener);
		
		lbpriority = hh.clabel("Priority");
		lbpriority.setBounds(10,190,130,25);
		lPanel.add(lbpriority);
		
		cmbpriority = hh.cbcombo();
		cmbpriority.setName("priority");
		cmbpriority.setModel(new DefaultComboBoxModel(hh.prior()));	
		cmbpriority.setBounds(150, 190, 220, 25);
		lPanel.add(cmbpriority);
		cmbpriority.addFocusListener(cFocusListener);
		
		lbphase= hh.clabel("Phase %");
		lbphase.setBounds(10,230,130,25);
		lPanel.add(lbphase);
		
		txphase = cTextField(25);
		txphase.setBounds(150, 230, 220, 25);
		lPanel.add(txphase);
		txphase.addKeyListener(hh.Onlynum());
		
		lbdue = hh.clabel("Due date");
		lbdue.setBounds(10, 270, 130, 20);
		lPanel.add(lbdue);

		duedate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		duedate.setDateFormatString("yyyy-MM-dd");
		duedate.setFont(new Font("Arial", Font.BOLD, 16));
		duedate.setBounds(150, 270, 220, 25);
		lPanel.add(duedate);		
		
		rPanel = new JPanel(null);
		rPanel.setBounds(600, 40, 400, 440);
		rPanel.setBackground(hh.neonzold);
		rPanel.setBorder(hh.borderf);
		eepanel.add(rPanel);
		lbenote = hh.clabel("Note");
		lbenote.setBounds(110, 20, 100, 25);
		rPanel.add(lbenote);
		
		txaenote = new JTextArea();
		txaenote.setFont(new java.awt.Font("Monospaced", 1, 18));
		txaenote.setLineWrap(true);
		txaenote.setWrapStyleWord(true);
		txaenote.setMargin( new Insets(5,5,5,5) );
		txaenote.putClientProperty("caretAspectRatio", 0.1);
		txaenote.setCaretColor(Color.RED);
		jSEPane = new JScrollPane(txaenote);	
		jSEPane.setBounds(20, 50, 360, 340);
		rPanel.add(jSEPane);
		
		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(200, 320, 120, 30);
		lPanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(200, 360, 120, 30);
		lPanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			clearFields();
			}
		});
		return eepanel;
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
				String status =  String.valueOf(cmbstatus.getSelectedItem());
				ret = dvad.statusvalid(status);
			} else if (bb.getName() == "priority") {			
				String priority =  String.valueOf(cmbpriority.getSelectedItem());
				ret = dvad.priorityvalid(priority);
			} else if (bb.getName() == "task") {				
				String task =  String.valueOf(cmbetasks.getSelectedItem());
				ret = dvad.taskvalid(task);
			}else if (bb.getName() == "employee") {				
				String employee =  String.valueOf(cmbeemployees.getSelectedItem());
				ret = dvad.taskvalid(employee);
			}
			if (ret == true) {
				b.setBorder(hh.borderf);
			} else {
				b.setBorder(hh.borderp);
			}
		}
	};
	
	private void data_update() throws SQLException {
		DefaultTableModel d1 = (DefaultTableModel) ts_table.getModel();
		int row = ts_table.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			ResultSet rs = dd.getTasktable(rowid);
			while (rs.next()) {
				txphase.setText(rs.getString("phase"));
				String cnum = rs.getString("emp_id");
				int number = 0;
				if (!hh.zempty(cnum)) {
					number = Integer.parseInt(cnum);
				}
				hh.setSelectedValue(cmbeemployees, number);				
				cmbeemployees.updateUI();				
				cnum = rs.getString("tco_id");
				number = 0;
				if (!hh.zempty(cnum)) {
					number = Integer.parseInt(cnum);
				}
				hh.setSelectedValue(cmbetasks, number);
				cmbetasks.updateUI();				
				cmbstatus.setSelectedItem(rs.getString("status"));		
				cmbpriority.setSelectedItem(rs.getString("priority"));		
				txaenote.setText( rs.getString("notes"));
				Date date;
				try {	
				String dd = rs.getString("duedate");	
				if (!hh.zempty(dd)) {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
					duedate.setDate(date);
				} else {
					duedate.setCalendar(null);
				}				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			dh.CloseConnection();			
			cards.show(cardPanel, "edit");
			cmbeemployees.requestFocus();		
	}
	}
			
	private void sqlgyart(){
		String year = "";		
		int emp_id = ((Employee) cmbemployees.getSelectedItem()).getEmp_id();	
		Man_idd= ((Employee) cmbmanagers.getSelectedItem()).getEmp_id();	
		int tco_id = ((Task) cmbtasks.getSelectedItem()).getTco_id();		
		if (cmbyears.getSelectedItem() != null) {		
			year = String.valueOf(cmbyears.getSelectedItem());
		}
	
		if (Man_idd > 0) {
			String swhere = " a.dm_id =" + Man_idd;
			
			if (emp_id > 0 ) {
				swhere = swhere + " and a.emp_id ='"+ emp_id+"'";
			}
			if (tco_id > 0 ) {
				swhere = swhere + " and a.tco_id ='"+ tco_id+"'";
			} 
			if (!hh.zempty(year)) {
				swhere = swhere + " and strftime('%Y',a.duedate)= '" + year + "'";
			}
		
		   table_update(swhere);		
		}		
	}
	private void savebuttrun(){
		DefaultTableModel d1 = (DefaultTableModel) ts_table.getModel();
		int emp_id = 0;	
		String employee = "";
		int tco_id =0;
		 String task="";
		String status ="", priority="", note ="", sql="", jel="";	
		String smanid = hh.itos(Man_idd);	
	
		if (cmbeemployees.getSelectedItem() != null) {			
		       emp_id = ((Employee) cmbeemployees.getSelectedItem()).getEmp_id();
		    	Employee cemployee = (Employee) cmbeemployees.getSelectedItem();
			     employee = String.valueOf(cemployee);
			}	
		if (cmbtasks.getSelectedItem() != null) {	
	           	    tco_id = ((Task) cmbetasks.getSelectedItem()).getTco_id();		
		            task =  String.valueOf(cmbetasks.getSelectedItem());
		}
		if (cmbstatus.getSelectedItem() != null) {		
			status = String.valueOf(cmbstatus.getSelectedItem());
		}
		if (cmbpriority.getSelectedItem() != null) {		
			priority = String.valueOf(cmbpriority.getSelectedItem());
			}
		String dudate = ((JTextField) duedate.getDateEditor().getUiComponent()).getText();
		String phase = txphase.getText();
		note = txaenote.getText();		
		
		if (taskvalidation(employee, task, status, priority) == false) {
			return;
		}

		if (rowid != "") {
			jel = "UP";
			sql = "update  task_table set emp_id= '" + emp_id + "', tco_id= " + tco_id + "," + "duedate = '" + dudate
					+ "', status = '" + status + "'," + "priority= '" + priority + "', phase= '" + phase + "',"
					+ "notes = '" + note + "', dm_id='" + smanid + "' where task_id = " + rowid;
		} else {
			sql = "insert into task_table (emp_id, tco_id, duedate, status, priority, phase, notes, dm_id) " + "values ("
					+ emp_id + "," + tco_id + ",'" + dudate + "','" + status + "','" + priority + "','" + phase + "','"
					+ note +"','"  + smanid +"')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(task_id) AS max_id from task_table");						
					d1.insertRow(d1.getRowCount(), new Object[] { myid, emp_id, employee, tco_id, task, dudate, status,
							priority, phase, note});
					hh.gotolastrow(ts_table);
					if (ts_table.getRowCount() > 0) {
						int row = ts_table.getRowCount() - 1;
						ts_table.setRowSelectionInterval(row, row);
					}
				} else {
     				d1.setValueAt(emp_id, myrow, 1);
    				d1.setValueAt(employee, myrow, 2);
    				d1.setValueAt(tco_id, myrow, 3);
        			d1.setValueAt(task, myrow, 4);
    				d1.setValueAt(dudate, myrow, 5);
         			d1.setValueAt(status, myrow, 6);
     				d1.setValueAt(priority, myrow, 7);
        			d1.setValueAt(phase, myrow, 8);
     				d1.setValueAt(note, myrow, 9);     			
     				txanote.setText(note);     				
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
	
private	Boolean taskvalidation(String employee, String task, String status, String priority) {
	Boolean ret = true;
	ArrayList<String> err = new ArrayList<String>();

	if (!dvad.assigntovalid(employee)) {
		err.add(dvad.mess);
		ret = false;
	}

	if (!dvad.taskvalid(task)) {
		err.add(dvad.mess);
		ret = false;
	}

	if (!dvad.statusvalid(status)) {
		err.add(dvad.mess);
		ret = false;
	}
	if (!dvad.priorityvalid(priority)) {
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
	private void clearFields(){		
		hh.setSelectedValue(cmbeemployees, 0);		
		cmbeemployees.updateUI();
		cmbeemployees.setSelectedIndex(-1);		
		hh.setSelectedValue(cmbetasks, 0);
		cmbetasks.updateUI();
		cmbetasks.setSelectedIndex(-1);		
		cmbstatus.setSelectedIndex(-1);
		cmbpriority.setSelectedIndex(-1);
		duedate.setCalendar(null);
		txphase.setText("");
		txaenote.setText("");
		rowid = "";
		myrow = 0;		
	}
	public void passtocmbc(int number) {
		try {			
			dd.taskscombofill(cmbetasks);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		hh.setSelectedValue(cmbetasks, number);
		cmbetasks.updateUI();
	}
//private String listtostring() {	
//	String ret="";
//	 ArrayList<String> myList = new ArrayList<String>();
//     myList.add("Apple");
//     myList.add("Banana");
//     myList.add("Cherry");
//     
//     ret = String.join(", ", myList);
//    return ret; 
//}

private String listtostring() {	
String ret="";

ArrayList<String> myList = new ArrayList<String>();
String sql = "select dept_id  from deptmanager where  emp_id = '"+ Man_idd + "'";
rs = dh.GetData(sql);
try {
	while (rs.next()) {
	    myList.add(String.valueOf(rs.getString("dept_id")));
	}
} catch (SQLException e) {
	e.printStackTrace();
}
dh.CloseConnection();
StringBuilder sb = new StringBuilder();
for (String s : myList)
{
	sb.append("'");
    sb.append(s);
    sb.append("',");
}
String sbb = sb.toString();
if (sbb.lastIndexOf(",") > -1 ) { 
		ret = sbb.substring(0, sbb.lastIndexOf(","));
}	
return ret; 
 }


	public static void main(String args[]) {
		Taskmanage ts = new Taskmanage();
		ts.setSize(1240, 650);
		ts.setLayout(null);
		ts.setLocationRelativeTo(null);
		ts.setVisible(true);
	}

	CardLayout cards;
	JPanel cardPanel, tPanel, ePanel, fejpanel, lPanel, rPanel;
	JScrollPane jSPane1, jSPane2, jSEPane;
	JTable ts_table;
	JComboBox cmbemployees, cmbeemployees, cmbmanagers, cmbtasks, cmbetasks,  cmbstatus, cmbyears,cmbpriority;
	JLabel lbemp, lbdep, lbyears, lbheader, lbdate, lbstatus;
	JLabel lbpicture, lbsearch, lbemployee;
	JLabel lbtasks, lbmanager, lbnote, lbenote, lbetask,lbpriority,lbphase, lbdue;
	JTextField txphase;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel, btnsendto, btnclear, btnsearch, btnnewtask;
	Container cp;
	SpinnerDateModel zimodel, zdmodel, zomodel;
	private JSpinner stimein, sdate, stimeout;
	JTextArea txanote, txaenote;

}
