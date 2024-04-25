package main;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
import java.util.ArrayList;
import databaseop.DatabaseHelper;
import databaseop.Databaseop;
import classes.Employee;
import classes.Hhelper;
import classes.Salvalidation;
import net.proteanit.sql.DbUtils;

public class Salaries extends JFrame{
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
	Salaries(){
		initcomponents();
		try {		
		dd.employeecombofill(cmbemployees);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		table_update("");
 		hh.iconhere(this);		
	}
	private void initcomponents() {
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
		setTitle("Employee salaries");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		epanel = new JPanel(null);
		epanel.setBounds(10, 52, 400, 400);	
		epanel.setBackground(hh.neonzold);
		epanel.setBorder(hh.borderf);
		cp.add(epanel);
		
		tpanel = new JPanel(null);
		tpanel.setBounds(410, 52, 614, 400);
		tpanel.setBackground(hh.neonzold);
		tpanel.setBorder(hh.borderf);
		cp.add(tpanel);
	
		fejpanel.setBounds(0, 0, 1050, 50);
		fejpanel.setBackground(hh.zold);
		lbheader = hh.flabel("S A L A R I ES");
		lbheader.setBounds(440, 5, 300, 40);
		fejpanel.add(lbheader);
		add(fejpanel);
		
		lbemployee = hh.clabel("Employee");
		lbemployee.setBounds(20, 50, 100, 25);
		epanel.add(lbemployee);

		cmbemployees = hh.cbcombo();
		cmbemployees.setName("employee");
		cmbemployees.setBounds(130, 50, 250, 25);	
		epanel.add(cmbemployees);
		cmbemployees.addFocusListener(cFocusListener);
		
		lbsalary = hh.clabel("Salary");
		lbsalary.setBounds(20, 100, 100, 25);
		epanel.add(lbsalary);
		
		txsalary = cTextField(25);
		txsalary.setBounds(130, 100, 250, 25);
		txsalary.setHorizontalAlignment(JTextField.RIGHT);
		epanel.add(txsalary);		
		txsalary.addKeyListener(hh.Onlynum());		
		
		lbsdate = hh.clabel("From date");
		lbsdate.setBounds(20, 150, 100, 25);
		epanel.add(lbsdate);
		
		lbsdatec = hh.llabel("yyyy-mm-dd");
		lbsdatec.setBounds(60,170,60,15);
		epanel.add(lbsdatec);
		
		txsdate = cTextField(25);
		txsdate.setBounds(130, 150, 250, 25);
		epanel.add(txsdate);		
		txsdate.addKeyListener(hh.Onlydate());
		
		lbedate = hh.clabel("To date");
		lbedate.setBounds(20, 200, 100, 25);
		epanel.add(lbedate);
		
		lbedatec = hh.llabel("yyyy-mm-dd");
		lbedatec.setBounds(60,220,60,15);
		epanel.add(lbedatec);
		
		txedate = cTextField(25);
		txedate.setBounds(130, 200, 250, 25);
		epanel.add(txedate);
		txedate.addKeyListener(hh.Onlydate());

		btnsave = hh.cbutton("Save");
		btnsave.setBackground(hh.vpiros1);
		btnsave.setBounds(190, 250, 120, 30);
		epanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.zold);
		btncancel.setBounds(190, 290, 120, 30);
		epanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
		    clearFields();
			}
		});

		btndelete = hh.cbutton("Delete");
		btndelete.setBackground(hh.vkek);
		btndelete.setBounds(190, 330, 120, 30);
		epanel.add(btndelete);

		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel d1 = (DefaultTableModel) sal_table.getModel();
				int sIndex = sal_table.getSelectedRow();
				if (sIndex < 0) {
					return;
				}				
				dd.data_delete(sal_table, "delete from salaries where sal_id =");
				clearFields();
			}
		});	
		sal_table = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) sal_table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		sal_table.setTableHeader(new JTableHeader(sal_table.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(sal_table);

		sal_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				sal_table.scrollRectToVisible(sal_table.getCellRect(sal_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane = new JScrollPane(sal_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		sal_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { },
				new String[] { "sal_id", "emp_id", "Employee", "Salary", "From date", "To date" }));

		hh.setJTableColumnsWidth(sal_table, 590, 0, 0, 40,20,20,20);
		jSPane.setViewportView(sal_table);
		jSPane.getViewport().setBackground(hh.vvvzold);
		jSPane.setBounds(10, 20, 590, 350);
		jSPane.setBorder(hh.borderf);
		tpanel.add(jSPane);	
		sal_table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});				
	}
	
	private void table_update(String what) {
		String Sql;	
		if (what == "") {
			Sql ="select a.sal_id, a.emp_id, e.name as empname, a.salary, a.from_date, a.to_date "
					+" from salaries a join employees e on a.emp_id = e.emp_id order by empname, a.from_date ";
		} else {
			Sql ="select a.sal_id, a.emp_id, e.name as empname, a.salary, a.from_date, a.to_date "
					+" from salaries a join employees e on a.emp_id = e.emp_id "
					+ " where " + what + "order by empname, a.from_date ";		
		}
		ResultSet res = dh.GetData(Sql);
		sal_table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();

		String[] fej = { "sal_id", "emp_id", "Employee", "Salary", "From date", "To date" };

		((DefaultTableModel) sal_table.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(sal_table, 590, 0, 0, 40,20,20,20);	
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		sal_table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		hh.table_show(sal_table);
	}
	private void savebuttrun() {	
		String sql ="";
		int emp_id = 0;	
		String employee = "";
		DefaultTableModel d1 = (DefaultTableModel) sal_table.getModel();
		String sdate = txsdate.getText();	
		String edate = txedate.getText();	
		String salary = txsalary.getText();
	
		if (cmbemployees.getSelectedItem() != null) {			
		       emp_id = ((Employee) cmbemployees.getSelectedItem()).getEmp_id();
		    	Employee cemployee = (Employee) cmbemployees.getSelectedItem();
			     employee = String.valueOf(cemployee);
			}	
	
		 if (ssalvalidation(employee, salary, sdate, edate) == false) {
			 return;
		 }

		if (rowid != "") {		
			sql = "update  salaries set emp_id = " + emp_id  + ", salary='" + salary +"', from_date ='" 
			+ sdate +"', to_date = '" + edate + "' where sal_id = " + rowid;
		} else {
			sql = "insert into salaries (emp_id, salary, from_date, to_date) " + 
		      "values (" + emp_id +  ","+ salary +",'" + sdate +"','"+ edate + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(sal_id) AS max_id from salaries");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, emp_id,employee, salary,sdate, edate});
					hh.gotolastrow(sal_table);
				} else {
					d1.setValueAt(emp_id, myrow, 1);		
					d1.setValueAt(employee, myrow, 2);	
					d1.setValueAt(salary, myrow, 3);				
					d1.setValueAt(sdate, myrow, 4);	
					d1.setValueAt(edate, myrow, 5);	
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
	private void clearFields(){
		txsdate.setText("");	
		txedate.setText("");
		txsalary.setText("");
		myrow = 0;
		rowid ="";
	//	cmbemployees.setSelectedItem(-1);		
		hh.setSelectedValue(cmbemployees, 0);
		cmbemployees.updateUI();
		sal_table.clearSelection();
	}
	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		DefaultTableModel d1 = (DefaultTableModel) sal_table.getModel();
		int row = sal_table.getSelectedRow();
		if (row >= 0) {		
			rowid = sal_table.getValueAt(row, 0).toString();
			myrow = row;
			String cnum = d1.getValueAt(row, 1).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbemployees, number);
			cmbemployees.updateUI();
			txsalary.setText(sal_table.getValueAt(row, 3).toString());				
			txsdate.setText(sal_table.getValueAt(row, 4).toString());	
			txedate.setText(sal_table.getValueAt(row, 5).toString());
		}
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
				if (Txt == txsdate) {
					Txt.setText(content);
					 ret = svad.sdatevalid(content);
				}	else if (Txt == txedate) {
					Txt.setText(content);
					 ret = svad.edatevalid(content);				
				}		else if (Txt == txsalary) {
					Txt.setText(content);
					 ret = svad.salaryvalid(content);				
				}							
				if (ret == true) {
					Txt.setBorder(hh.borderf);
				} else {
					Txt.setBorder(hh.borderp);
				}
			}
		};
		
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

			if (bb.getName() == "employee") {
				String employee= String.valueOf(cmbemployees.getSelectedItem());
				ret = svad.employeevalid(employee);
			} 
			if (ret == true) {
				b.setBorder(hh.borderf);
			} else {
				b.setBorder(hh.borderp);
			}
		}
	};
	private Boolean ssalvalidation(String employee, String salary, String sdate, String edate){
		Boolean ret = true;
		 ArrayList<String> err = new ArrayList<String>();
		
		 if (!svad.employeevalid(employee)) {
			 err.add(svad.mess);			
		    	ret = false;
		}
		 if (!svad.salaryvalid(salary)) {
			 err.add(svad.mess);			
		    	ret = false;
		}		 
		 if (!svad.sdatevalid(sdate)) {				
			 err.add(svad.mess);			
	    	ret = false;
	}	
		 if (!svad.edatevalid(edate)) {				
			 err.add(svad.mess);			
	    	ret = false;
	}			 
		 if (!hh.zempty(edate)) {
		 if (hh.twodate(sdate, edate) == false) {		
		    	ret = false;
		 }}
		 if (err.size() > 0) {
            JOptionPane.showMessageDialog(null, err.toArray(),"Error message",1);					               
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
    	textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}
	
	public static void main(String args[]) {
		Salaries sal = new Salaries();
		sal.setSize(1050,510);
		sal.setLayout(null);
		sal.setLocationRelativeTo(null);
		sal.setVisible(true);
	}
	Container cp;
	JPanel fejpanel, epanel, tpanel;
	JLabel lbheader, lbemployee, lbsalary, lbsdate, lbedate, lbsdatec, lbedatec;
	JTextField txsdate, txedate, txsalary;
	JScrollPane jSPane;
	JTable sal_table;
	JButton btnsave, btncancel, btndelete;
	JComboBox cmbemployees;	
}
