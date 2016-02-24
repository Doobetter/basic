package basic.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Args  -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *  -XX:+PrintGCDetails
 * @author YAO
 *
 */
public class HeapOutOfMem {

	static class OOMObject{
		
	}
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while (true){
			list.add(new OOMObject());
		}
	}

}
