/**
 * @author Paul Rabbat
 */
public class Tuple {
	
	private String primaryKey, attribute1;
	
	public Tuple(String primaryKey, String attribute1){
		this.primaryKey = primaryKey;
		this.attribute1 = attribute1;
	}
	
	public Tuple(String primaryKey){
		this.primaryKey = primaryKey;
	}
	
	
	public String getKey(){
		return primaryKey;
	}
	
	public String getAttribute(){
		return attribute1;
	}
	
}
