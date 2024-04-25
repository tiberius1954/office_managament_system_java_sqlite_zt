package classes;

public class Title {
	 private int title_id;
	    private String name;
	    
	    public Title( int title_id, String name) {
	    	  this.title_id = title_id; 
	    	  this.name = name;
	    	
	    }	  
	    @Override
	    public String toString() 
	    { 
	    	         return name; 	  
	    } 
	    
	    public int getTitle_id() {
	        return title_id;
	    }

	    public void setTitle_id(int title_id) {
	        this.title_id = title_id;
	    }

	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
}
