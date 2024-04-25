package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import classes.Empvalidation;
import classes.Hhelper;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import net.proteanit.sql.DbUtils;

public class Tasks extends JFrame {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;	
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Empvalidation evad = new Empvalidation();
	private String rowid = "";
	private int myrow = 0;
	private String sfrom = "";
	private JFrame pframe;
	
	Tasks(){
		initcomponents();
	    table_update("");
	    hh.iconhere(this);
	}
	Tasks(JFrame parent) {
		sfrom = "taskmfrom";
		pframe = parent;
		initcomponents();
	    table_update("");
	    hh.iconhere(this);	
	}
	private void initcomponents(){
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Task codes");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		epanel = new JPanel(null);
		epanel.setBounds(10, 52, 310, 300);	
		epanel.setBackground(hh.neonzold);
		epanel.setBorder(hh.borderf);
		cp.add(epanel);
		
		tpanel = new JPanel(null);
		tpanel.setBounds(310, 52, 360, 300);
		tpanel.setBackground(hh.neonzold);
		tpanel.setBorder(hh.borderf);
		cp.add(tpanel);
	
		fejpanel.setBounds(0, 0, 700, 50);
		fejpanel.setBackground(hh.zold);
		lbheader = hh.flabel("TASK CODES");
		lbheader.setBounds(240, 5, 250, 40);
		fejpanel.add(lbheader);
		add(fejpanel);
		lbname = hh.clabel("Name");
		lbname.setBounds(20, 50, 50, 25);
		epanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(80, 50, 210, 25);	
		epanel.add(txname);
		txname.addKeyListener( hh.MUpper());

		
		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(125, 100, 120, 30);
		epanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(125, 140, 120, 30);
		epanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
		    clearFields();
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBackground(hh.vkek);
		btndelete.setBounds(125, 180, 120, 30);
		epanel.add(btndelete);

		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel d1 = (DefaultTableModel) taskc_table.getModel();
				int sIndex = taskc_table.getSelectedRow();
				if (sIndex < 0) {
					return;
				}
				String iid = d1.getValueAt(sIndex, 0).toString();
				if (dd.cannotdelete("select tco_id from task_table  where tco_id ="+iid)==true) {
					 JOptionPane.showMessageDialog(null, "You can not delete this task  !");
					 return ;
				}	
				dd.data_delete(taskc_table, "delete from taskcode where tco_id =");
				clearFields();
			}
		});
		
		btnsendto = hh.cbutton("Send to manage task");
		btnsendto.setBounds(90, 250, 190, 30);
		btnsendto.setBackground(hh.narancs);
		
		if (sfrom == "taskmfrom") { 
			epanel.add(btnsendto);
	}
		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});
		
		taskc_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) taskc_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		taskc_table.setTableHeader(new JTableHeader(taskc_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(taskc_table);
		hh.table_show(taskc_table);

		jSPane = new JScrollPane(taskc_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		taskc_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { },
				new String[] { "dep_ID", "Name" }));

		hh.setJTableColumnsWidth(taskc_table, 310, 0, 100);
		jSPane.setViewportView(taskc_table);
		jSPane.getViewport().setBackground(hh.vvvzold);
		jSPane.setBounds(30, 10, 310, 280);
		jSPane.setBorder(hh.borderf);
		tpanel.add(jSPane);	
		taskc_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			tableMouseClicked(evt);
			}
		});		
	}
	private void savebuttrun() {	
		String sql ="";
		DefaultTableModel d1 = (DefaultTableModel) taskc_table.getModel();
		String name = txname.getText();		
	
		if (hh.zempty(name)) {
			 JOptionPane.showMessageDialog(null,"Name is empty !","Error",1);	
			return;
		}

		if (rowid != "") {
			sql = "update  taskcode set name= '" + name + "' where tco_id = " + rowid;
		} else {
			sql = "insert into taskcode (name) " + "values ('" + name +  "' )";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(tco_id) AS max_id FROM taskcode");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name});
					hh.gotolastrow(taskc_table);
				} else {
					d1.setValueAt(name, myrow, 1);					
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
	clearFields();
	if (sfrom == "taskmfrom") {
		if (taskc_table.getRowCount() > 0) {
			int row = taskc_table.getRowCount() - 1;
			taskc_table.setRowSelectionInterval(row, row);
		}}
	}
	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = taskc_table.getSelectedRow();
		if (row >= 0) {		
			rowid = taskc_table.getValueAt(row, 0).toString();
			myrow = row;
			txname.setText(taskc_table.getValueAt(row, 1).toString());		
		}
	}
	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select tco_id, name from taskcode order by name";
		} else {
			Sql = "select tco_id, name  from taskcode where " + what +" order by name";
		}
		String[] fej = { "ID", "Name"};
	
		ResultSet res = dh.GetData(Sql);
		taskc_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		((DefaultTableModel) taskc_table.getModel()).setColumnIdentifiers(fej);
		hh.table_show(taskc_table);
		hh.setJTableColumnsWidth(taskc_table, 250, 0, 100);
	}
	
	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
		//textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}
	
	private void clearFields() {
		txname.setText("");
		rowid = "";
		myrow = 0;
		taskc_table.clearSelection();
	}
	
	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) taskc_table.getModel();
		int row = taskc_table.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			((Taskmanage) pframe).passtocmbc(number);
			pframe.setVisible(true);	
			dispose();
		}
	}
	
	
	
	public static void main(String args[]) {
		Tasks tas = new Tasks();
		tas.setSize(700,410);
		tas.setLayout(null);
		tas.setLocationRelativeTo(null);
		tas.setVisible(true);
	}
	Container cp;
	JPanel fejpanel, epanel, tpanel;
	JLabel lbheader, lbname;
	JTextField txname;
	JScrollPane jSPane;
	JTable taskc_table;
	JButton btnsave, btncancel, btndelete, btnsendto;
}
