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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
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
import classes.Hhelper.StringUtils;
import net.proteanit.sql.DbUtils;

public class Print_employees extends JFrame {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	StringUtils hss = hh.new StringUtils();

	Print_employees() {
		initcomponents();
		table_update("");
		hh.iconhere(this);	
	}

	void initcomponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Print employees");
		cp = getContentPane();
		cp.setBackground(hh.neonzold);
		fejpanel = new JPanel(null);
		fejpanel.setBounds(0, 0, 1210, 50);
		fejpanel.setBackground(hh.zold);

		lbheader = hh.flabel("PRINT EMPLOYEES");
		lbheader.setBounds(450, 5, 350, 40);
		fejpanel.add(lbheader);
		add(fejpanel);
		emp_table = hh.ptable();

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

		hh.madepheader(emp_table);

		emp_table.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				emp_table.scrollRectToVisible(emp_table.getCellRect(emp_table.getRowCount() - 1, 0, true));
			}
		});

		jSPane1 = new JScrollPane(emp_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		emp_table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { {} },
				new String[] { "Emp_ID", "Name", "Phone", "Email", "Address", "City", "Country", "Gender" }));

		hh.setJTableColumnsWidth(emp_table, 1150, 0, 20, 10, 15, 20, 15, 14, 6);

		jSPane1.setViewportView(emp_table);
		jSPane1.getViewport().setBackground(hh.vvvzold);
		jSPane1.setBounds(20, 70, 1150, 380);
		jSPane1.setBorder(hh.borderf2);
		add(jSPane1);

		btnprint = hh.cbutton("Print");
		btnprint.setBounds(550, 490, 130, 30);	
		btnprint.setBackground(hh.vpiros1);
		add(btnprint);

		btnprint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
				PrinterJob job = PrinterJob.getPrinterJob();
				job.setJobName("Employees");
				HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
				// attr.add(new MediaPrintableArea(5, 5, 200, 287, MediaPrintableArea.MM));
				attr.add(new MediaPrintableArea(10, 10, 190, 275, MediaPrintableArea.MM));
				job.print(attr);

				MessageFormat[] header = new MessageFormat[3];
				header[0] = new MessageFormat(hss.center("EMPLOYEES  LIST", 100));
				header[1] = new MessageFormat("");
				header[2] = new MessageFormat("Date: " + hh.currentDate()+ " Time: " + hh.currenttime());
				MessageFormat[] footer = new MessageFormat[1];
				// footer[0] = new MessageFormat(hss.center(" -{0}-",160));
				footer[0] = new MessageFormat(hss.center("Page {0,number,integer}", 185));
				job.setPrintable(hh.new MyTablePrintable(emp_table, JTable.PrintMode.FIT_WIDTH, header, footer));
				job.printDialog();
				job.print();
				
				} catch (java.awt.print.PrinterAbortException e) {
				} catch (PrinterException ex) {
					System.err.println("Error printing: " + ex.getMessage());
					ex.printStackTrace();
				}		
			}
		});
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
		hh.setJTableColumnsWidth(emp_table, 1150, 0, 20, 10, 15, 20, 15, 14, 6);
		hh.table_show(emp_table);
	}

	public static void main(String args[]) {
		Print_employees emp = new Print_employees();
		emp.setSize(1205, 600);
		emp.setLayout(null);
		emp.setLocationRelativeTo(null);
		emp.setVisible(true);
	}

	JPanel fejpanel;
	JLabel lbheader;
	Container cp;
	JScrollPane jSPane1;
	JTable emp_table;
	JButton btnprint;

}
