package basic.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestMethodHandle {

	class GrandFather {
		void thinking() {
			System.out.println("I am grandfather");
		}
	}

	class Father extends GrandFather {
		void thinking() {
			System.out.println("I am father");
		}
	}

	class Son extends Father {
		void thinking() {
			
			System.out.println("dddd");
			MethodType mt = MethodType.methodType(void.class);
			try {
				MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, getClass()).bindTo(this);
				mh.invoke();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		(new TestMethodHandle().new Son()).thinking();
	}

}
