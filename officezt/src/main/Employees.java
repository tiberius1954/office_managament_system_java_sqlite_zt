package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import classes.Empvalidation;
import classes.Hhelper;
import net.proteanit.sql.DbUtils;

public class Employees extends JFrame {
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
	JDateChooser dobdate = new JDateChooser(new Date());
	JDateChooser indate = new JDateChooser(new Date());

	Employees() {
		initcomponents();
		dd.countrycombofill(cmbcountries);
		table_update("");
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
		setTitle("Employees");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1230, 50);
		fejpanel.setBackground(hh.zold);

		lbheader = hh.flabel("EMPLOYEES");
		lbheader.setBounds(500, 5, 200, 40);
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

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(280, 20, 70, 25);
		ttpanel.add(lbsearch);

		txsearch = cTextField(25);
		txsearch.setBounds(360, 20, 200, 25);
		ttpanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(560, 20, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		ttpanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				table_update("");
			}
		});
		cmbsearch = hh.cbcombo();
		cmbsearch.setFocusable(true);
		cmbsearch.setBounds(590, 20, 150, 25);
		cmbsearch.setFont(new java.awt.Font("Tahoma", 1, 16));
		cmbsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		cmbsearch.setBackground(Color.ORANGE);
		cmbsearch.addItem("Name");
		cmbsearch.addItem("Phone");
		cmbsearch.addItem("Email");
		ttpanel.add(cmbsearch);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(745, 20, 90, 25);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		ttpanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});

		emp_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) emp_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		emp_table.setTableHeader(new JTableHeader(emp_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(emp_table);

		emp_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				emp_table.scrollRectToVisible(emp_table.getCellRect(emp_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane1 = new JScrollPane(emp_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		emp_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { {} },
				new String[] { "Emp_ID", "Name", "Phone", "Email", "Address", "City", "Country", "Gender" }));
		hh.setJTableColumnsWidth(emp_table, 1150, 0, 18, 12, 17, 20, 15, 12, 6);
		jSPane1.setViewportView(emp_table);
		jSPane1.getViewport().setBackground(hh.vvvzold);
		jSPane1.setBounds(20, 70, 1150, 380);
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
				data_new();
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
				empdata_delete();
			}
		});

		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setSize(1200, 540);
		eepanel.setBackground(hh.neonzold);
		lPanel = new JPanel(null);
		lPanel.setBounds(190, 80, 400, 280);
		lPanel.setBackground(hh.neonzold);
		// lPanel.setBorder(hh.line);
		eepanel.add(lPanel);
		rPanel = new JPanel(null);
		rPanel.setBounds(590, 80, 460, 280);
		rPanel.setBackground(hh.neonzold);
		eepanel.add(rPanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(10, 20, 120, 20);
		lPanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(140, 20, 250, 25);
		lPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 60, 120, 20);
		lPanel.add(lbphone);

		txphone = cTextField(25);
		txphone.setBounds(140, 60, 250, 25);
		lPanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());

		lbemail = hh.clabel("Email");
		lbemail.setBounds(10, 100, 120, 20);
		lPanel.add(lbemail);

		txemail = cTextField(25);
		txemail.setBounds(140, 100, 250, 25);
		lPanel.add(txemail);

		lbaddr = hh.clabel("Address");
		lbaddr.setBounds(10, 140, 120, 20);
		lPanel.add(lbaddr);

		txaddress = cTextField(25);
		txaddress.setBounds(140, 140, 250, 25);
		lPanel.add(txaddress);

		lbcity = hh.clabel("City");
		lbcity.setBounds(10, 180, 120, 20);
		lPanel.add(lbcity);

		txcity = cTextField(25);
		txcity.setBounds(140, 180, 250, 25);
		lPanel.add(txcity);
		txcity.addKeyListener(hh.MUpper());

		lbcountry = hh.clabel("Country");
		lbcountry.setBounds(10, 220, 120, 20);
		lPanel.add(lbcountry);

		cmbcountries = hh.cbcombo();
		cmbcountries.setBounds(140, 220, 250, 25);
		lPanel.add(cmbcountries);
		// cmbcountries.addFocusListener(cFocusListener);

		lbnation = hh.clabel("Nationality");
		lbnation.setBounds(10, 20, 120, 20);
		rPanel.add(lbnation);

		txnation = cTextField(25);
		txnation.setBounds(140, 20, 250, 25);
		rPanel.add(txnation);
		txnation.addKeyListener(hh.MUpper());

		lbgender = hh.clabel("Gender");
		lbgender.setBounds(10, 60, 120, 20);
		rPanel.add(lbgender);

		cmbgender = hh.cbcombo();
		cmbgender.setModel(new DefaultComboBoxModel(new String[] { "Female", "Male" }));
		cmbgender.setBounds(140, 60, 250, 25);
		rPanel.add(cmbgender);

		lbdob = hh.clabel("Dob");
		lbdob.setBounds(10, 100, 120, 20);
		rPanel.add(lbdob);

		dobdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		dobdate.setDateFormatString("yyyy-MM-dd");
		dobdate.setFont(new Font("Arial", Font.BOLD, 16));
		dobdate.setBounds(140, 100, 250, 25);
		rPanel.add(dobdate);

		lbindate = hh.clabel("Indate");
		lbindate.setBounds(10, 140, 120, 20);
		rPanel.add(lbindate);

		indate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		indate.setDateFormatString("yyyy-MM-dd");
		indate.setFont(new Font("Arial", Font.BOLD, 16));
		indate.setBounds(140, 140, 250, 25);
		rPanel.add(indate);

		lbqual = hh.clabel("Qualification");
		lbqual.setBounds(10, 180, 120, 20);
		rPanel.add(lbqual);

		txqual = cTextField(25);
		txqual.setBounds(140, 180, 250, 25);
		rPanel.add(txqual);
		txqual.addKeyListener(hh.MUpper());

		lbexp = hh.clabel("Experience");
		lbexp.setBounds(10, 220, 120, 20);
		rPanel.add(lbexp);

		txexp = cTextField(25);
		txexp.setBounds(140, 220, 250, 25);
		rPanel.add(txexp);
		
		lbexpe = hh.llabel("years");
		lbexpe.setBounds(395, 220, 40, 20);
		rPanel.add(lbexpe);		

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(540, 400, 110, 30);
		btnsave.setBackground(hh.vpiros1);
		eepanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});
		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(660, 400, 110, 30);
		eepanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
				cards.show(cardPanel, "tabla");
			}
		});

		return eepanel;
	}

	private void clearFields() {
		cmbcountries.setSelectedIndex(-1);
		txname.setText("");
		txaddress.setText("");
		txcity.setText("");
		txphone.setText("");
		txemail.setText("");
		txnation.setText("");
		txqual.setText("");
		txexp.setText("");
		indate.setCalendar(null);
		dobdate.setCalendar(null);
		rowid = "";
		myrow = 0;
		emp_table.clearSelection();
	}

	private void sqlgyart() {
		String stext = txsearch.getText().trim().toLowerCase();
		String scmbtxt = String.valueOf(cmbsearch.getSelectedItem());
		String swhere = "";
		if (!hh.zempty(stext)) {
			if (scmbtxt == "Name") {
				swhere = " lower(name) like '%" + stext.trim() + "%'";
			} else if (scmbtxt == "Phone") {
				swhere = " phone like '%" + stext.trim() + "%' ";
			} else if (scmbtxt == "Email") {
				swhere = " lower(email) like '%" + stext.trim() + "%' ";
			}
			table_update(swhere);
		} else {
			JOptionPane.showMessageDialog(null, "Empty condition !", "Error", 1);
			return;
		}
	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select emp_id, name, phone, email, address, city, country, gender  from employees order by name";
		} else {
			Sql = "select emp_id, name, phone, email, address, city, country, gender  from " + "employees where " + what
					+ " order by name";
		}
		ResultSet res = dh.GetData(Sql);
		emp_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "Emp_ID", "Name", "Phone", "Email", "Address", "City", "Country", "Gender" };
		((DefaultTableModel) emp_table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(emp_table, 1150, 0, 18, 12, 17, 20, 15, 12, 6);
		hh.table_show(emp_table);
		if (emp_table.getRowCount() > 0) {
			int row = emp_table.getRowCount() - 1;
			emp_table.setRowSelectionInterval(row, row);
		}
	}

	private void data_new() {
		rowid = "";
		cards.show(cardPanel, "edit");
		clearFields();
		txname.requestFocus();
	}

	private void data_update() throws SQLException {
		DefaultTableModel d1 = (DefaultTableModel) emp_table.getModel();
		int row = emp_table.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			ResultSet rs = dd.getEmployee(rowid);
			while (rs.next()) {
				txname.setText(rs.getString("name"));
				txphone.setText(rs.getString("phone"));
				txemail.setText(rs.getString("email"));
				txaddress.setText(rs.getString("address"));
				txcity.setText(rs.getString("city"));
				cmbcountries.setSelectedItem(rs.getString("country"));
				cmbgender.setSelectedItem(rs.getString("gender"));				
				txnation.setText(rs.getString("nationality"));
				txqual.setText(rs.getString("qualification"));
				txexp.setText(rs.getString("experience"));
				try {
					String dd = rs.getString("in_date");
					Date date;
					if (!hh.zempty(dd)) {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						indate.setDate(date);
					} else {
						indate.setCalendar(null);
					}

					dd = rs.getString("dob");
					if (!hh.zempty(dd)) {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						dobdate.setDate(date);
					} else {
						dobdate.setCalendar(null);
					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dh.CloseConnection();
		}
		cards.show(cardPanel, "edit");
		txname.requestFocus();
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";
		String country = "";
		String gender = "";
		DefaultTableModel d1 = (DefaultTableModel) emp_table.getModel();
		if (cmbcountries.getSelectedItem() != null) {
			country = String.valueOf(cmbcountries.getSelectedItem());
		}
		if (cmbgender.getSelectedItem() != null) {
			gender = String.valueOf(cmbgender.getSelectedItem());
		}
		String name = txname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String address = txaddress.getText();
		String city = txcity.getText();
		String nation = txnation.getText();
		String qual = txqual.getText();
		String exp = txexp.getText();
		String in_date = ((JTextField) indate.getDateEditor().getUiComponent()).getText();
		String dob = ((JTextField) dobdate.getDateEditor().getUiComponent()).getText();

		if (empvalidation(name, phone, email, address, city, country, nation, qual) == false) {
			return;
		}

		if (rowid != "") {
			jel = "UP";
			sql = "update  employees set name= '" + name + "', phone= '" + phone + "'," + "email = '" + email
					+ "', address = '" + address + "'," + "city= '" + city + "', country= '" + country + "',"
					+ "nationality = '" + nation + "', qualification='" + qual + "', experience='" + exp
					+ "', in_date='" + in_date + "', " + " dob='" + dob + "', gender='" + gender + "' where emp_id = "
					+ rowid;
		} else {
			sql = "insert into employees (name, phone, email, address, city, country, nationality, qualification, "
					+ "experience, in_date, dob, gender) " + "values ('" + name + "','" + phone + "','" + email + "','"
					+ address + "','" + city + "','" + country + "','" + nation + "','" + qual + "','" + exp + "','"
					+ in_date + "','" + dob + "','" + gender + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(emp_id) AS max_id FROM employees");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, name, phone, email, address, city, country, gender });
					hh.gotolastrow(emp_table);
					if (emp_table.getRowCount() > 0) {
						int row = emp_table.getRowCount() - 1;
						emp_table.setRowSelectionInterval(row, row);
					}
				} else {
					d1.setValueAt(name, myrow, 1);
					d1.setValueAt(phone, myrow, 2);
					d1.setValueAt(email, myrow, 3);
					d1.setValueAt(address, myrow, 4);
					d1.setValueAt(city, myrow, 5);
					d1.setValueAt(country, myrow, 6);
					d1.setValueAt(gender, myrow, 7);
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
		cards.show(cardPanel, "tabla");
	}

	private Boolean empvalidation(String name, String phone, String email, String address, String city, String country,
			String nation, String qual) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (!evad.namevalid(name)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.phonevalid(phone)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.emailvalid(email)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.addressvalid(address)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.cityvalid(city)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.countryvalid(country)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.qualvalid(qual)) {
			err.add(evad.mess);
			ret = false;
		}
		if (!evad.nationvalid(nation)) {
			err.add(evad.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}

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
			if (Txt == txname) {
				Txt.setText(content);
				ret = evad.namevalid(content);
			} else if (Txt == txnation) {
				Txt.setText(content);
				ret = evad.nationvalid(content);
			} else if (Txt == txqual) {
				Txt.setText(content);
				ret = evad.qualvalid(content);
			} else if (Txt == txaddress) {
				Txt.setText(content);
				ret = evad.addressvalid(content);
			} else if (Txt == txcity) {
				Txt.setText(content);
				ret = evad.cityvalid(content);
			} else if (Txt == txphone) {
				Txt.setText(content);
				ret = evad.phonevalid(content);
			} else if (Txt == txemail) {
				Txt.setText(content);
				ret = evad.emailvalid(content);
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

	private int empdata_delete() {
		String sql = "delete from employees  where emp_id =";
		Boolean error = false;
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) emp_table.getModel();
		int sIndex = emp_table.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}

		if (dd.cannotdelete("select emp_id from attendence  where emp_id =" + iid) == true) {
			JOptionPane.showMessageDialog(null, "You can not delete this employee !");
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

	public static void main(String args[]) {
		Employees emp = new Employees();
		emp.setSize(1230, 650);
		emp.setLayout(null);
		emp.setLocationRelativeTo(null);
		emp.setVisible(true);
	}

	CardLayout cards;
	JPanel cardPanel, tPanel, ePanel, fejpanel, lPanel, rPanel;
	JScrollPane jSPane1;
	JTable emp_table;
	JComboBox cmbcountries, cmbsearch, cmbgender;
	JLabel lbname, lbaddr, lbcity, lbcountry, lbphone, lbemail, lbheader, lbnation;
	JLabel lbpicture, lbsearch, lbdob, lbgender, lbindate, lbqual, lbexp, lbexpe;
	JTextField txname, txaddress, txcity, txphone, txemail, txsearch, txnation, txqual, txexp;
	JButton btnnew, btndelete, btnupdate, btnsave, btncancel, btnsendto, btnclear, btnsearch;
	Container cp;
}
