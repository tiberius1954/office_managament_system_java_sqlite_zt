package classes;

public class Task {
	  private int tco_id;
	  private String name;
	  
	  public Task( int tco_id, String name) 
	  { 
	  this.tco_id = tco_id; 
	  this.name = name;   
	  } 		 	
	  
	  @Override
	  public String toString() 
	  {  
	       return name;   
	  } 


	    public int getTco_id() {
	        return tco_id;
	    }

	    public void setTco_id(int tco_id) {
	        this.tco_id = tco_id;
	    }

	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
}
