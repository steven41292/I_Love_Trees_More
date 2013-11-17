/**
 * 
 * @author Paul Rabbat
 *
 */
public class TestResult {

	public int treeSize, n, height;
	public double avgInsertionCpuTimeNano, avgRandomSearchCpuTimeNano,avgInsertionAccesses, avgRandomSearchAccesses, avgSequentialSearchCpuTimeNano, avgSequentialSearchAccesses, wastedSpacePercent;
	
	
	// Add you own contstructor if you're not using all the testing things I used.
	
	public TestResult(int _treeSize, int _n, int _height, double _avgInsertionCpuTimeNano, double _avgInsertionAccesses, double _avgRandomSearchCpuTimeNano, double _avgRandomSearchAccesses, double _avgSequentialSearchCpuTimeNano, double _avgSequentialSearchAccesses, double _wastedSpacePercent){
		treeSize = _treeSize;
		n = _n;
		height = _height;
		avgInsertionCpuTimeNano = _avgInsertionCpuTimeNano;
		avgInsertionAccesses = _avgInsertionAccesses;
		avgRandomSearchCpuTimeNano = _avgRandomSearchCpuTimeNano;
		avgRandomSearchAccesses = _avgRandomSearchAccesses;
		avgSequentialSearchCpuTimeNano = _avgSequentialSearchCpuTimeNano;
		avgSequentialSearchAccesses = _avgSequentialSearchAccesses;
		wastedSpacePercent = _wastedSpacePercent;
	}
}
