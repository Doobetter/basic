package basic.jvm;


public class ReferenceCountGC {

	public Object instance = null;
	private int _1MB = 1_024*1_024;
	public static void main(String[] args) {
	
		ReferenceCountGC A = new ReferenceCountGC();
		ReferenceCountGC B = new ReferenceCountGC();
		
		A.instance = B;
		B.instance = A;
		
		A = null;
		B = null;
		System.gc();
	}

}
