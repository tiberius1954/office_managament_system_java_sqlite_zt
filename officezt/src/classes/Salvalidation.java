package classes;

public class Salvalidation {
	Hhelper hh = new Hhelper();
	public String mess = "";
	String ss;
	Boolean yes = false;		
	
	public boolean sdatevalid(String content) {
		if (hh.zempty(content)) {
			mess = "From date is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect date !";
				return false;
			}
		}
		return true;
	}
	public boolean edatevalid(String content) {
		if (hh.zempty(content)) {			
			return true;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect date !";
				return false;
			} 
		}
		return true;
	}	
	public boolean employeevalid(String content) {
		if (hh.zempty(content)) {
			mess= " Employee is empty !";
			return false;
		}
		return true;
	}
	public boolean salaryvalid(String content) {
		if (hh.zempty(content)) {
			mess= " Salary is empty !";
			return false;
		}
		return true;
	}
	public boolean departmentvalid(String content) {
		if (hh.zempty(content)) {
			mess= " Department is empty !";
			return false;
		}
		return true;
	}
	public boolean titlevalid(String content) {
		if (hh.zempty(content)) {
			mess= " Title is empty !";
			return false;
		}
		return true;
	}
	public boolean notevalid(String content) {
		if (hh.zempty(content)) {
			mess= " Note is empty !";
			return false;
		}
		return true;
	}
}
