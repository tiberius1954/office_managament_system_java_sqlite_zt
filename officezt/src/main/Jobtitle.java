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

public class Jobtitle extends JFrame {
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

	
	Jobtitle(){
		initcomponents();
	    table_update("");
	    hh.iconhere(this);		
	}
	Jobtitle (JFrame parent) {
		sfrom = "titlesfrom";
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
		setTitle("Job titles");
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
		lbheader = hh.flabel("JOB TITLES");
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
				DefaultTableModel d1 = (DefaultTableModel) jobt_table.getModel();
				int sIndex = jobt_table.getSelectedRow();
				if (sIndex < 0) {
					return;
				}
				String iid = d1.getValueAt(sIndex, 0).toString();
				if (dd.cannotdelete("select title_id from titles  where title_id ="+iid)==true) {
					 JOptionPane.showMessageDialog(null, "You can not delete this task table !");
					 return ;
				}	
				dd.data_delete(jobt_table, "delete from jobtitle where title_id =");
				clearFields();
			}
		});
		
		btnsendto = hh.cbutton("Send to titles");
		btnsendto.setBounds(115, 250, 140, 30);
		btnsendto.setBackground(hh.narancs);
		if (sfrom == "titlesfrom") {
			epanel.add(btnsendto);
		}
		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});
		
		jobt_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) jobt_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		jobt_table.setTableHeader(new JTableHeader(jobt_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(jobt_table);
		hh.table_show(jobt_table);

		jSPane = new JScrollPane(jobt_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jobt_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { },
				new String[] { "dep_ID", "Name" }));

		hh.setJTableColumnsWidth(jobt_table, 310, 0, 100);
		jSPane.setViewportView(jobt_table);
		jSPane.getViewport().setBackground(hh.vvvzold);
		jSPane.setBounds(30, 10, 310, 270);
		jSPane.setBorder(hh.borderf);
		tpanel.add(jSPane);	
		jobt_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			tableMouseClicked(evt);
			}
		});		
	}
	private void savebuttrun() {	
		String sql ="";
		DefaultTableModel d1 = (DefaultTableModel) jobt_table.getModel();
		String name = txname.getText();		
	
		if (hh.zempty(name)) {
			 JOptionPane.showMessageDialog(null,"Name is empty !","Error",1);	
			return;
		}

		if (rowid != "") {
			sql = "update  jobtitle set name= '" + name + "' where title_id = " + rowid;
		} else {
			sql = "insert into jobtitle (name) " + "values ('" + name +  "' )";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(title_id) AS max_id FROM jobtitle");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name});
					hh.gotolastrow(jobt_table);
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
	if (sfrom == "titlesfrom") {
	if (jobt_table.getRowCount() > 0) {
		int row = jobt_table.getRowCount() - 1;
		jobt_table.setRowSelectionInterval(row, row);
	}}

	}
	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = jobt_table.getSelectedRow();
		if (row >= 0) {		
			rowid = jobt_table.getValueAt(row, 0).toString();
			myrow = row;
			txname.setText(jobt_table.getValueAt(row, 1).toString());		
		}
	}
	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select title_id, name from jobtitle order by name";
		} else {
			Sql = "select title_id, name  from jobtitle where " + what +" order by name";
		}
		String[] fej = { "ID", "Name"};
	
		ResultSet res = dh.GetData(Sql);
		jobt_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		((DefaultTableModel) jobt_table.getModel()).setColumnIdentifiers(fej);
		hh.table_show(jobt_table);
		hh.setJTableColumnsWidth(jobt_table, 250, 0, 100);
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
		jobt_table.clearSelection();
	}	
	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) jobt_table.getModel();
		int row = jobt_table.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			((Titles) pframe).passtocmbc(number);
			pframe.setVisible(true);	
			dispose();
		}
	}

	
	public static void main(String args[]) {
		Jobtitle jo = new Jobtitle();
		jo.setSize(700,410);
		jo.setLayout(null);
		jo.setLocationRelativeTo(null);
		jo.setVisible(true);
	}
	Container cp;
	JPanel fejpanel, epanel, tpanel;
	JLabel lbheader, lbname;
	JTextField txname;
	JScrollPane jSPane;
	JTable jobt_table;
	JButton btnsave, btncancel, btndelete,btnsendto;
}

