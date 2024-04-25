package classes;

public class Employee{	
   
    private int emp_id;
    private String name;
    private String phone;
    private String email;
    private String dob; 
    private String address;
    private String city;
    private String country;
   private String in_date;
    private String gender;
    private String  nationality;
	private String qualification;
	private String experience;
    
    public Employee( int emp_id, String name, String phone, String email, String dob, String address, String city, String country,
    		String indate, String gender, String nationality, String qualification, String experience) 
	  { 
	  this.emp_id = emp_id; 
	  this.name = name; 
	  this.phone = phone;
	  this.email = email;
	  this.dob = dob;
	  this.address = address;
	  this.city = city;
	  this.country = country;
	  this.in_date = in_date;
	  this.gender = gender;
	  this.nationality = nationality;
	  this.qualification = qualification;
	  this.experience = experience;
	  } 	
	
  public Employee( int emp_id, String name, String phone ) 
  { 
  this.emp_id = emp_id; 
  this.name = name;  
  this.phone = phone;
  } 	
  public Employee( int emp_id, String name) 
  { 
  this.emp_id = emp_id; 
  this.name = name;   
  } 	
  
@Override
  public String toString() 
  {  
       return name;   
  } 


    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }  

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }   

    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getDob() {
        return dob;
    }     

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry() {
        return country;
    }    
    
    public void setIn_date(String in_date) {
        this.in_date = in_date;
    }
    public String getIn_date() {
        return in_date;
    }    
   
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }  
    
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    } 
    
    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }  
    
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }  
    
   
    
    
}
