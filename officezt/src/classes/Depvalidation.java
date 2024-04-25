package classes;

public class Depvalidation {
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
		public boolean departmentvalid(String content) {
			if (hh.zempty(content)) {
				mess= " Department is empty !";
				return false;
			}
			return true;
		}
		public boolean hoursvalid(String content) {
			if (content =="00:00" || hh.zempty(content)) {
				mess= " Hours is wrong !";
				return false;
			}
			return true;
		}
		public boolean noofdaysvalid(String content) {
			if (content =="0" || hh.zempty(content)) {
				mess= " No of days is wrong !";
				return false;
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
		public boolean assigntovalid(String content) {
			if (hh.zempty(content)) {
				mess= " Assign to  is empty !";
				return false;
			}
			return true;
		}
		public boolean statusvalid(String content) {
			if (hh.zempty(content)) {
				mess= " Status is empty !";
				return false;
			}
			return true;
		}
		public boolean taskvalid(String content) {
			if (hh.zempty(content)) {
				mess= " Task is empty !";
				return false;
			}
			return true;
		}
		public boolean priorityvalid(String content) {
			if (hh.zempty(content)) {
				mess= " Priority is empty !";
				return false;
			}
			return true;
		}
		public boolean typevalid(String content) {
			if (hh.zempty(content)) {
				mess= " Type is empty !";
				return false;
			}
			return true;
		}
}
