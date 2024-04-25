package classes;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;
import classes.Hhelper;

public class Empvalidation {
	Hhelper hh = new Hhelper();
	public String mess = "";
	String ss;
	Boolean yes = false;

	public boolean namevalid(String content) {
		if (hh.zempty(content)) {
			mess= "Name is empty !";
			return false;
		}
		return true;
	}
	public Boolean phonevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Phone is empty !";
			return false;
		} else if (content.length() < 7) {
			mess = "Phone is short !";
			return false;
		}
		return true;
	}

	public boolean emailvalid(String content) {
		if (hh.zempty(content)) {
		     mess = "Email is empty !";
			return false;
		} else if (content.indexOf(".") == -1 || content.indexOf("@") == -1) {
			mess = "Incorrect email  !";
			return false;
		}
		return true;
	}
	public boolean addressvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Address is empty !";
			return false;
		}
		return true;
	}
	public boolean cityvalid(String content) {
		if (hh.zempty(content)) {
			mess = "City is empty !";
			return false;
		}
		return true;
	}
	public Boolean countryvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Country is empty !";
			return false;	
		}
	return true;
	}	
	
	public boolean datevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Date is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect date !";
				return false;
			}
		}
		return true;
	}
	
	public Boolean nationvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Nationality is empty !";
			return false;	
		}
		return true;
	}
	
	public Boolean qualvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Qualification is empty !";
			return false;	
		}
		return true;
	}		
}
