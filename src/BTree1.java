/**
 * Tests implemented by Paul Rabbat
 */

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*************************************************************************
 *  Compilation:  javac BTree.java
 *  Execution:    java BTree
 *
 *  B-tree.
 *
 *  Limitations
 *  -----------
 *   -  Assumes M is even and M >= 4
 *   -  should b be an array of children or list (it would help with
 *      casting to make it a list)
 *
 *************************************************************************/

public class BTree1<Key extends Comparable<Key>, Value>  {
    private Node root;             // root of the B-tree
    private int HT;                // height of the B-tree
    private int N;                 // number of key-value pairs in the B-tree
    
    // ADD THE STUFF BELOW THIS
    private static long startCpuTimeNano, endCpuTimeNano;
    private static int accesses = 0, totalIterations, treeSize;
    private static List<Tuple> database;
    private static Random generator = new Random();
    private static int[] indexArr;
    private static boolean debug = true;   
    private static int M;    // max children per B-tree node = M-1
    private final static int[] mArr = {4,5,6,11,16,21,26}; // max children values that I randomly chose
   
    
   // ADD THIS METHOD 
    public static List<TestResult> runTests(int _totalIterations, int _treeSize, List<Tuple> _database, int[] _indexArr){
		List<TestResult> results = new ArrayList<TestResult>();
    	totalIterations = _totalIterations;
		treeSize = _treeSize;
		indexArr = _indexArr;
		database = _database;

		if(debug) System.out.println("********** B TREE 1 RESULTS **********\n");
		
		// run the code below for different max children values
		for(int mIndex=0;mIndex<mArr.length;mIndex++){
			M = mArr[mIndex]; // get M (max children for this run)
		
			// initialize the tree
			BTree1<String, String> st = new BTree1<String, String>();
			
			// add each node in the database to the b tree and time it
			startCpuTimeNano = getCpuTime();
			for(int i=0; i<database.size(); i++){
				Tuple t = database.get(i);
				st.put(t.getKey(), t.getAttribute());
			}
			endCpuTimeNano = getCpuTime() - startCpuTimeNano;
			double avgInsertionCpuTimeNano = endCpuTimeNano/totalIterations;

			int index;
			String val;
			accesses = 0;
			
			// search for the random nodes and find time it
			startCpuTimeNano =  getCpuTime();
			for(int i=0; i<totalIterations; i++){
				index = indexArr[i];
				String keyName = "key" + index;
				val = st.get(keyName);
			}
			endCpuTimeNano =  getCpuTime() - startCpuTimeNano;
			double avgSearchCpuTimeNano = endCpuTimeNano/totalIterations; 
			
			// gather the test results
			int size = st.size();
			int n = M-1;
			int height = st.height();
			double avgSearchAccesses = (double)accesses/(totalIterations);
			// add it to the list of results
			results.add(new TestResult(size, n, height, avgInsertionCpuTimeNano, avgSearchCpuTimeNano, avgSearchAccesses));
			
			if(debug){
				System.out.println("Tree size: " + st.size());
				System.out.println("N value: " + (M-1));
				System.out.println("Height: " + st.height());
				System.out.println("Average insertion CPU time: " + avgInsertionCpuTimeNano + " nanoseconds");
				System.out.println("Average search CPU time: " + avgSearchCpuTimeNano + " nanoseconds");
				System.out.println("Average search accesses: " + (double)accesses/(totalIterations));
				System.out.println("================================");
				System.out.println();
			}
		}
		
		// return the results
		return results;
    }

    // helper B-tree node data type
    private static final class Node {
        private int m;                             // number of children
        private Entry[] children = new Entry[M];   // the array of children
        private Node(int k) { m = k; }             // create a node with k children
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    private static class Entry {
        private Comparable key;
        private Object value;
        private Node next;     // helper field to iterate over array entries
        public Entry(Comparable key, Object value, Node next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    // constructor
    public BTree1() { root = new Node(0); }
 
    // return number of key-value pairs in the B-tree
    public int size() { return N; }

    // return height of B-tree
    public int height() { return HT; }


    // search for given key, return associated value; return null if no such key
    public Value get(Key key) { return search(root, key, HT); }
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
            	accesses++;
                if (eq(key, children[j].key)) return (Value) children[j].value;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
            	accesses++;
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }


    // insert key-value pair
    // add code to check for duplicate keys
    public void put(Key key, Value value) {
        Node u = insert(root, key, value, HT); 
        N++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        HT++;
    }


    private Node insert(Node h, Key key, Value value, int ht) {
        int j;
        Entry t = new Entry(key, value, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, value, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--) h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // split node in half
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    // for debugging
    public String toString() {
        return toString(root, HT, "") + "\n";
    }
    private String toString(Node h, int ht, String indent) {
        String s = "";
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s += indent + children[j].key + " " + children[j].value + "\n";
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s += indent + "(" + children[j].key + ")\n";
                s += toString(children[j].next, ht-1, indent + "     ");
            }
        }
        return s;
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    
	/** Get CPU time in nanoseconds. */
	public static long getCpuTime( ) {
	    ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
	    return bean.isCurrentThreadCpuTimeSupported( ) ?
	        bean.getCurrentThreadCpuTime( ) : 0L;
	}
}