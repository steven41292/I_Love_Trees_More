/**
 * @author Paul Rabbat
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Database {

	public static void main(String[] args) {
		final int totalIterations = 1000000;
		final int[] treeSizes = {10, 100, 1000, 10000, 100000};
		int treeSize;
		Random generator = new Random();
		List<TestResult> testResults1 = new ArrayList<TestResult>();
		List<TestResult> testResults2 = new ArrayList<TestResult>();
		List<TestResult> testResults3 = new ArrayList<TestResult>();
		
		// Run this for each tree size
		for(int k=0; k<treeSizes.length; k++){
			List<Tuple> database = new ArrayList<Tuple>();
			treeSize = treeSizes[k];
			
			// Generates random IP address data in the format "###.###.###.##" and
			// a key for it in the format "key#"
	        
			for(int i=0; i<treeSize;i++){
	        	String val = "";
	        	for(int j=0; j<14;j++){
	        		if(j == 3 || j == 7 || j == 11){
	        			val+= ".";
	        		}
	        		else{
	            		int dig = generator.nextInt(10);
	            		val+=dig;
	        		}
	        	}
	        	
	        	database.add(new Tuple("key" + i, val));
	        }
	        
	        // Generates random indexes to search for			
	        int[] indexArr = new int[totalIterations];
	        for(int i=0; i<totalIterations; i++){
	        	indexArr[i] = generator.nextInt(database.size());
	        }

	        testResults1.addAll(BTree1.runTests(totalIterations, treeSize, database, indexArr));

	        // CALL YOUR B TREE BELOW LIKE THIS:
	        testResults2.addAll(BTree2.runTests(totalIterations, treeSize, database, indexArr)); 
	        //testResults3.addAll(BTree3.runTests(totalIterations, treeSize, database, indexArr));
		}
	}
}
