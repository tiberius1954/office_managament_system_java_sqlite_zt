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

public class Departments extends JFrame {
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

	Departments() {
		initcomponents();
		table_update("");
       hh.iconhere(this);
	}

	private void initcomponents() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Departments");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		epanel = new JPanel(null);
		epanel.setBounds(10, 52, 350, 300);
		epanel.setBackground(hh.neonzold);
		epanel.setBorder(hh.borderf);
		cp.add(epanel);

		tpanel = new JPanel(null);
		tpanel.setBounds(360, 52, 320, 300);
		tpanel.setBackground(hh.neonzold);
		tpanel.setBorder(hh.borderf);
		cp.add(tpanel);

		fejpanel.setBounds(0, 0, 700, 50);
		fejpanel.setBackground(hh.zold);
		lbheader = hh.flabel("DEPARTMENTS");
		lbheader.setBounds(240, 5, 250, 40);
		fejpanel.add(lbheader);
		add(fejpanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(20, 50, 70, 25);
		epanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(100, 50, 210, 25);
		epanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(150, 100, 120, 30);
		epanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(150, 140, 120, 30);
		epanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBackground(hh.vkek);
		btndelete.setBounds(150, 180, 120, 30);
		epanel.add(btndelete);

		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel d1 = (DefaultTableModel) dep_table.getModel();
				int sIndex = dep_table.getSelectedRow();
				if (sIndex < 0) {
					return;
				}
				String iid = d1.getValueAt(sIndex, 0).toString();
				if (dd.cannotdelete("select dep_id from employee_department  where dep_id =" + iid) == true) {
					JOptionPane.showMessageDialog(null, "You can not delete this department !");
					return;
				}
				dd.data_delete(dep_table, "delete from department where dep_id =");
				clearFields();
			}
		});

		dep_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dep_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		dep_table.setTableHeader(new JTableHeader(dep_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(dep_table);

		dep_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dep_table.scrollRectToVisible(dep_table.getCellRect(dep_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane = new JScrollPane(dep_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		dep_table.setModel(
				new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "dep_ID", "Name" }));

		hh.setJTableColumnsWidth(dep_table, 250, 0, 100);
		jSPane.setViewportView(dep_table);
		jSPane.getViewport().setBackground(hh.vvvzold);
		jSPane.setBounds(30, 10, 260, 270);
		jSPane.setBorder(hh.borderf);
		tpanel.add(jSPane);
		dep_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});
	}

	private void savebuttrun() {
		String sql = "";
		DefaultTableModel d1 = (DefaultTableModel) dep_table.getModel();
		String name = txname.getText();

		if (hh.zempty(name)) {
			JOptionPane.showMessageDialog(null, "Name is empty !", "Error", 1);
			return;
		}

		if (rowid != "") {
			sql = "update  department set name= '" + name + "' where dep_id = " + rowid;
		} else {
			sql = "insert into department (name) " + "values ('" + name + "' )";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(dep_id) AS max_id FROM department");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name });
					hh.gotolastrow(dep_table);
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
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		int row = dep_table.getSelectedRow();
		if (row >= 0) {
			rowid = dep_table.getValueAt(row, 0).toString();
			myrow = row;
			txname.setText(dep_table.getValueAt(row, 1).toString());
		}
	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select dep_id, name from department order by name";
		} else {
			Sql = "select dep_id, name  from department where " + what + " order by name";
		}
		ResultSet res = dh.GetData(Sql);
		dep_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();

		String[] fej = { "ID", "Name" };

		((DefaultTableModel) dep_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dep_table, 250, 0, 100);
		hh.table_show(dep_table);
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

	private void clearFields() {
		txname.setText("");
		rowid = "";
		myrow = 0;
		dep_table.clearSelection();
	}

	public static void main(String args[]) {
		Departments dep = new Departments();
		dep.setSize(700, 410);
		dep.setLayout(null);
		dep.setLocationRelativeTo(null);
		dep.setVisible(true);
	}

	Container cp;
	JPanel fejpanel, epanel, tpanel;
	JLabel lbheader, lbname;
	JTextField txname;
	JScrollPane jSPane;
	JTable dep_table;
	JButton btnsave, btncancel, btndelete;
}
