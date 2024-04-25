package databaseop;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import classes.Hhelper;
import classes.Employee;
import classes.Task;
import classes.Title;
import classes.Department;
import net.proteanit.sql.DbUtils;

public class Databaseop {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	DatabaseHelper dh = new DatabaseHelper();
	Hhelper hh = new Hhelper();

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
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

	public int table_maxid(String sql) {
		int myid = 0;
		try {
			con = dh.getConnection();
			rs = dh.GetData(sql);
			if (!rs.next()) {
				System.out.println("Error.");
			} else {
				myid = rs.getInt("max_id");
			}
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return myid;
	}

	public void countrycombofill(JComboBox ccombo) {
		String Sql = " select countryname from country order by  countryname";
		try {
			ResultSet res = dh.GetData(Sql);
			while (res.next()) {
				ccombo.addItem(res.getString("countryname"));
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	public void employeecombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Employee A = new Employee(0, " "," ");
		mycombo.addItem(A);
		String sql = "select emp_id, name, phone from employees order by name";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Employee(rs.getInt("emp_id"), rs.getString("name"), rs.getString("phone"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}
	public void employeecombofillta(JComboBox mycombo, String depts) throws SQLException {
		mycombo.removeAllItems();
		Employee A = new Employee(0, " ");
		mycombo.addItem(A);
		String sql = "select  distinct a.emp_id, a.name  from employees a join employee_department d on a.emp_id = d.emp_id "
				+ " where  d.dep_id in ( "+ depts+" )   order by name";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Employee(rs.getInt("emp_id"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}
	public void titlescombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Title A = new Title(0, " ");
		mycombo.addItem(A);
		String sql = "select title_id, name  from jobtitle order by name";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Title(rs.getInt("title_id"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}
	
	public void managerscombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Employee A = new Employee(0, " ");
		mycombo.addItem(A);
		String sql = "select emp_id, name from employees where emp_id  in ( select  emp_id from  deptmanager ) "
				+ " order by name ";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Employee(rs.getInt("emp_id"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}
	public void taskscombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Task A = new Task(0, " ");
		mycombo.addItem(A);
		String sql = "select tco_id, name from taskcode order by name ";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Task(rs.getInt("tco_id"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}
	
	
	public void departmentcombofill(JComboBox mycombo) throws SQLException {
		mycombo.removeAllItems();
		Department A = new Department(0, " ");
		mycombo.addItem(A);
		String sql = "select dep_id, name from department order by name";
		ResultSet rs = dh.GetData(sql);
		while (rs.next()) {
			A = new Department(rs.getInt("dep_id"), rs.getString("name"));
			mycombo.addItem(A);
		}
		dh.CloseConnection();
	}
	
	public ResultSet getEmployee(String empid) throws SQLException {
        String sql = " select * from employees where emp_id ='" + empid+"'";  
		ResultSet rs = dh.GetData(sql);	
       return rs;
    } 
	 
		public ResultSet getTasktable(String taskid) throws SQLException {
	        String sql = " select * from task_table where task_id ='" + taskid+"'";  
			ResultSet rs = dh.GetData(sql);	
	       return rs;
	    } 
	 
	public ResultSet getAttendence(String attid) throws SQLException {
        String sql = " select * from attendence where att_id ='" + attid+"'";  
		ResultSet rs = dh.GetData(sql);	
       return rs;
    } 
	public ResultSet getLeave(String leid) throws SQLException {
        String sql = " select * from leave where le_id ='" + leid+"'";  
		ResultSet rs = dh.GetData(sql);	
       return rs;
    } 

	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		ResultSet res = dh.GetData(sql);
		try {
			if (res.next()) {
				found = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		return found;
	}

}
