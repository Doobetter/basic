package basic.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class TestMethodHandle2 {
	static class ClassA {
		public void println(String s){
			System.out.println(s);
		}
		public void println(){
			System.out.println("----");
		}
	}
	public static void main(String[] args) throws Throwable {
		Object obj = System.currentTimeMillis()%2==0? System.out : new ClassA();
		getPrintlnMH(obj).invoke();
	}
	private static MethodHandle getPrintlnMH(Object reviewer) throws Throwable{
		//MethodType mt = MethodType.methodType(void.class, String.class);
		MethodType mt = MethodType.methodType(void.class);
		return MethodHandles.lookup().findVirtual(reviewer.getClass(), "println", mt).bindTo(reviewer);
	}

}
