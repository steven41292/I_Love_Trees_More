/**
 * 
 * @author Paul Rabbat
 *
 */
public class TestResult {

	private int treeSize, n, height;
	private double avgInsertionCpuTimeNano, avgSearchCpuTimeNano, avgSearchAccesses;
	
	
	public TestResult(int _treeSize, int _n, int _height, double _avgInsertionCpuTimeNano, double _avgSearchCpuTimeNano, double _avgSearchAccesses){
		treeSize = _treeSize;
		n = _n;
		height = _height;
		avgInsertionCpuTimeNano = _avgInsertionCpuTimeNano;
		avgSearchCpuTimeNano = _avgSearchCpuTimeNano;
		avgSearchAccesses = _avgSearchAccesses;
	}
}
