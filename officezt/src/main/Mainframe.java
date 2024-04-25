package main;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import classes.Hhelper;

public class Mainframe extends JFrame {

	Hhelper hh = new Hhelper();

	Mainframe() {
		init();
		menumaker();
	    hh.iconhere(this);
	}

	private void init() {
		setSize(1290, 700);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Office sytem");
		JLabel lbmain = new JLabel();
		lbmain.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/office.jpg")));
		lbmain.setBounds(0, 0, 1290, 700);
		add(lbmain);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int x, y, d;
				x = 1000;
				y = 600;
				d = 10;
				while (x > 0 && y > 0) {
					setSize(x, y);
					x = x - 2 * d;
					y = y - d;
					setVisible(true);
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println("Error:" + e);
					}
				}
				dispose();
			}
		});
	}

	private void menumaker() {
		// create a menu bar;
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(500, 30));
		Font f = new Font("Tahoma", Font.BOLD, 16);
		UIManager.put("Menu.font", f);
		// create menus
		JMenu exitMenu = new JMenu("Exit");
		JMenu empMenu = new JMenu("Employees");
		JMenu attMenu = new JMenu("Attendence");
		JMenu leaMenu = new JMenu("Leave");
		JMenu taskMenu = new JMenu("Tasks");
		JMenu listMenu = new JMenu("Lists");
		menuBar.add(exitMenu);
		menuBar.add(empMenu);
		menuBar.add(attMenu);
		menuBar.add(leaMenu);
		menuBar.add(taskMenu);
		menuBar.add(listMenu);

		// create menu items
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setFont(f);
		exitMenuItem.setMnemonic(KeyEvent.VK_K);
		exitMenuItem.setActionCommand("Exit");
		exitMenu.add(exitMenuItem);

		JMenuItem empMenuItem = new JMenuItem("Employees");
		empMenuItem.setFont(f);
		empMenuItem.setActionCommand("employees");

		JMenuItem empdepMenuItem = new JMenuItem("Employee-department");
		empdepMenuItem.setFont(f);
		empdepMenuItem.setActionCommand("empdep");

		JMenuItem titlesMenuItem = new JMenuItem("Employee titles");
		titlesMenuItem.setFont(f);
		titlesMenuItem.setActionCommand("titles");

		JMenuItem jtitlesMenuItem = new JMenuItem("Job titles");
		jtitlesMenuItem.setFont(f);
		jtitlesMenuItem.setActionCommand("jtitles");

		JMenuItem deptMenuItem = new JMenuItem("Departments");
		deptMenuItem.setFont(f);
		deptMenuItem.setActionCommand("department");
		
		JMenuItem deptmanMenuItem = new JMenuItem("Department managers");
		deptmanMenuItem.setFont(f);
		deptmanMenuItem.setActionCommand("department_manager");
		
		JMenuItem salMenuItem = new JMenuItem("Employee salaries");
		salMenuItem.setFont(f);
		salMenuItem.setActionCommand("salary");

		empMenu.add(empMenuItem);
		empMenu.add(empdepMenuItem);
		empMenu.add(titlesMenuItem);
		empMenu.add(jtitlesMenuItem);
		empMenu.add(deptMenuItem);
		empMenu.add(deptmanMenuItem);
		empMenu.add(salMenuItem);

		JMenuItem attMenuItem = new JMenuItem("Attendence");
		attMenuItem.setFont(f);
		attMenuItem.setActionCommand("attendence");

		JMenuItem attrecMenuItem = new JMenuItem("Attendence record");
		attrecMenuItem.setFont(f);
		attrecMenuItem.setActionCommand("attrec");
		
		JMenuItem attrepMenuItem = new JMenuItem("Attendence report");
		attrepMenuItem.setFont(f);
		attrepMenuItem.setActionCommand("attrep");

		attMenu.add(attMenuItem);
		attMenu.add(attrecMenuItem);
		attMenu.add(attrepMenuItem);	

		JMenuItem leaMenuItem = new JMenuItem("Leave");
		leaMenuItem.setFont(f);
		leaMenuItem.setActionCommand("leave");
		leaMenu.add(leaMenuItem);

		JMenuItem leaverepMenuItem = new JMenuItem("Leave report");
		leaverepMenuItem.setFont(f);
		leaverepMenuItem.setActionCommand("leaverep");
		leaMenu.add(leaverepMenuItem);

		JMenuItem taskmanMenuItem = new JMenuItem("Manage tasks");
		taskmanMenuItem.setFont(f);
		taskmanMenuItem.setActionCommand("taskman");
		taskMenu.add(taskmanMenuItem);

		JMenuItem tasksMenuItem = new JMenuItem("Task codes");
		tasksMenuItem.setFont(f);
		tasksMenuItem.setActionCommand("taskcodes");
		taskMenu.add(tasksMenuItem);
		
		JMenuItem noteMenuItem = new JMenuItem("Notice table");
		noteMenuItem.setFont(f);
		noteMenuItem.setActionCommand("note");
		taskMenu.add(noteMenuItem);
		
		JMenuItem listMenuItem = new JMenuItem("Employee list A");
		listMenuItem.setFont(f);
		listMenuItem.setActionCommand("emplist");
		listMenu.add(listMenuItem);
		
		JMenuItem list1MenuItem = new JMenuItem("Employee list B");
		list1MenuItem.setFont(f);
		list1MenuItem.setActionCommand("emplist1");
		listMenu.add(list1MenuItem);
		
		JMenuItem pattMenuItem = new JMenuItem("Attendence list");
		pattMenuItem.setFont(f);
		pattMenuItem.setActionCommand("attlist");
		listMenu.add(pattMenuItem);		
		
		JMenuItem pleaveMenuItem = new JMenuItem("Leave list");
		pleaveMenuItem.setFont(f);
		pleaveMenuItem.setActionCommand("pleave");
		listMenu.add(pleaveMenuItem);		
	
		this.setJMenuBar(menuBar);
		MenuItemListener menuItemListener = new MenuItemListener();
		exitMenuItem.addActionListener(menuItemListener);
		empMenuItem.addActionListener(menuItemListener);
		empdepMenuItem.addActionListener(menuItemListener);
		salMenuItem.addActionListener(menuItemListener);
		titlesMenuItem.addActionListener(menuItemListener);
		jtitlesMenuItem.addActionListener(menuItemListener);
		deptMenuItem.addActionListener(menuItemListener);
		deptmanMenuItem.addActionListener(menuItemListener);	
		attMenuItem.addActionListener(menuItemListener);
		attrecMenuItem.addActionListener(menuItemListener);
		attrepMenuItem.addActionListener(menuItemListener);		
		leaMenuItem.addActionListener(menuItemListener);
		leaverepMenuItem.addActionListener(menuItemListener);
		taskmanMenuItem.addActionListener(menuItemListener);
		tasksMenuItem.addActionListener(menuItemListener);
		noteMenuItem.addActionListener(menuItemListener);
		listMenuItem.addActionListener(menuItemListener);
		list1MenuItem.addActionListener(menuItemListener);
		pattMenuItem.addActionListener(menuItemListener);
		pleaveMenuItem.addActionListener(menuItemListener);
	}

	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String par = e.getActionCommand();
			if (par == "Exit") {
				System.exit(0);

			} else if (par == "employees") {
				Employees ob = new Employees();
				ob.setSize(1230, 650);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);
			} else if (par == "empdep") {
				Employee_department ob = new Employee_department();
				ob.setSize(1050, 510);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);
			} else if (par == "titles") {
				Titles ob = new Titles();
				ob.setSize(1050, 510);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);
			} else if (par == "jtitles") {
				Jobtitle ob = new Jobtitle();
				ob.setSize(700, 410);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);
			} else if (par == "department") {
				Departments ob = new Departments();
				ob.setSize(700, 410);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);
			} else if (par == "department_manager") {
				Deptmanager ob = new Deptmanager();
				ob.setSize(1150,510);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "attendence") {
				Attendenceb ob = new Attendenceb();
				ob.setSize(1230, 650);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
		    	ob.setVisible(true);	
			} else if (par == "attrec") {
			Attendencerecord ob = new Attendencerecord();	
			ob.setLayout(null);
			ob.setLocationRelativeTo(null);
			ob.setVisible(true);	
			} else if (par == "attrep") {
				Attendreport ob = new Attendreport();	
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);	
			} else if (par == "leave") {
			Leave ob = new Leave();
			ob.setSize(1230, 650);
			ob.setLayout(null);
			ob.setLocationRelativeTo(null);
    		ob.setVisible(true);		
			}else if (par == "leaverep") {
    			Leavereport ob = new Leavereport();
    			//ob.setSize(1230, 650);
    			ob.setLayout(null);
    			ob.setLocationRelativeTo(null);
        		ob.setVisible(true);		
			} else if (par == "taskman") {
			Taskmanage ob  = new Taskmanage();
			ob.setSize(1240, 650);
			ob.setLayout(null);
			ob.setLocationRelativeTo(null);
			ob.setVisible(true);			
			} else if (par == "taskcodes") {
				Tasks ob  = new Tasks();
				ob.setSize(700,410);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);			
			} else if (par == "note") {
				Noticetable ob  = new Noticetable();
				ob.setSize(1150,700);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);			
				} else if (par == "salary") {
				Salaries ob  = new Salaries();
				ob.setSize(1050,510);
				ob.setLayout(null);
				ob.setLocationRelativeTo(null);
				ob.setVisible(true);			
				} else if (par == "emplist") {
					Print_employees ob  = new Print_employees();					
					ob.setSize(1205, 600);
					ob.setLayout(null);
					ob.setLocationRelativeTo(null);
					ob.setVisible(true);						
				}else if (par == "emplist1") {
					Print_employees1 ob  = new Print_employees1();						
					ob.setSize(1205, 590);
					ob.setLayout(null);
					ob.setLocationRelativeTo(null);
					ob.setVisible(true);	
				}else if (par == "attlist") {
					Print_attendence ob  = new Print_attendence();						
					ob.setSize(1205, 650);
					ob.setLayout(null);
					ob.setLocationRelativeTo(null);
					ob.setVisible(true);	
				}else if (par == "pleave") {
					Print_leave ob  = new Print_leave();						
					ob.setSize(1205, 650);
					ob.setLayout(null);
					ob.setLocationRelativeTo(null);
					ob.setVisible(true);	
				}
		}
	}

	public static void main(String args[]) {

		Mainframe Main = new Mainframe();
		Main.setVisible(true);
	}

}
