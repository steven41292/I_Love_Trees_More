/**
 * @author Paul Rabbat
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Database {

	public static void main(String[] args) {
		final int totalIterations = 1000000;
		//final int[] treeSizes = {10, 100, 1000, 10000, 100000};
		final int[] treeSizes = {100000};
		int treeSize;
		boolean debug = false;
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
	        int[] indexArrRandom = new int[totalIterations];
	        int[] indexArrSequential = new int[totalIterations];
	        for(int i=0; i<totalIterations; i++){
	        	indexArrRandom[i] = generator.nextInt(database.size());
	        	// generate sequential block
	        	if(i<totalIterations/10){
		        	for(int j=0;j<10;j++){
		        		indexArrSequential[i+j] = indexArrRandom[i] + j;
		        	}
	        	}
	        }

	        testResults1.addAll(BTree1.runTests(totalIterations, treeSize, database, indexArrRandom, indexArrSequential));

	        // prints results from tree1 so I can easily graph them
	        if(debug){
	        	String treeSizeStr = "Tree sizes: ";
	        	String nStr = "N values: ";
	        	String heightStr = "Heights: ";
	        	String avgInsertionCpuTimeStr = "Average insertion CPU times (nanoseconds): ";
	        	String avgInsertionAccessesStr = "Average insertion accesses: ";
	        	String avgRandomSearchCpuTimeStr = "Average random search CPU times (nanoseconds): ";
	        	String avgRandomSearchAccessesStr = "Average random search accesses: ";
	        	String avgSequentialSearchCpuTimeStr = "Average sequential search CPU times (nanoseconds): ";
	        	String avgSequentialSearchAccessesStr = "Average sequential search accesses: ";
	        	String wastedSpacePercentStr = "Wasted Space Percentage: ";
	        	for(int i=0;i<testResults1.size();i++){
	        		TestResult t = testResults1.get(i);
		        	treeSizeStr += ", " + t.treeSize;
		        	nStr += ", " + t.n;
		        	heightStr += ", " + t.height;
		        	avgInsertionCpuTimeStr += ", " + t.avgInsertionCpuTimeNano;
		        	avgInsertionAccessesStr += ", " + t.avgInsertionAccesses;
		        	avgRandomSearchCpuTimeStr += ", " + t.avgRandomSearchCpuTimeNano;
		        	avgRandomSearchAccessesStr += ", " + t.avgRandomSearchAccesses;
		        	avgSequentialSearchCpuTimeStr += ", " + t.avgSequentialSearchCpuTimeNano;
		        	avgSequentialSearchAccessesStr += ", " + t.avgSequentialSearchAccesses;
		        	wastedSpacePercentStr += ", " + t.wastedSpacePercent;
	        	}	
	        	
	        	System.out.println(treeSizeStr + "\n");	        	
	        	System.out.println(nStr + "\n");	        	
	        	System.out.println(heightStr + "\n");	        	
	        	System.out.println(avgInsertionCpuTimeStr + "\n");	        	
	        	System.out.println(avgInsertionAccessesStr + "\n");       	
	        	System.out.println(avgRandomSearchCpuTimeStr + "\n");       	
	        	System.out.println(avgRandomSearchAccessesStr + "\n");       	
	        	System.out.println(avgSequentialSearchCpuTimeStr + "\n");      	
	        	System.out.println(avgSequentialSearchAccessesStr + "\n");  	
	        	System.out.println(wastedSpacePercentStr + "\n");
	        }
	        // CALL YOUR B TREE BELOW LIKE THIS:
	        testResults2.addAll(BTree2.runTests(totalIterations, treeSize, database, indexArrRandom)); 
	        //testResults3.addAll(BTree3.runTests(totalIterations, treeSize, database, indexArr));
		}
	}
}
