package classes;

public class Department {
	 private int dep_id;
	    private String name;
	    
	    public Department( int dep_id, String name) {
	    	  this.dep_id = dep_id; 
	    	  this.name = name;
	    	
	    }	  
	    @Override
	    public String toString() 
	    { 
	    	         return name; 	  
	    } 
	    
	    public int getDep_id() {
	        return dep_id;
	    }

	    public void setDep_id(int dep_id) {
	        this.dep_id = dep_id;
	    }

	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
}
