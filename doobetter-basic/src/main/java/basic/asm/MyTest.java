package basic.asm;

import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class MyTest extends ClassLoader{
	public int inc(){
		int x;
		try{
			x = 1;
			return x;
		}catch(Exception e){
			x = 2;
			return x;
		}finally{
			x = 3;
		}
	}

	/*	
package pkg;
public interface Comparable extends Mesurable {
int LESS = -1;
int EQUAL = 0;
int GREATER = 1;
int compareTo(Object o);
}
	 */
	public static void test(){
		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
		"pkg/Comparable", null, "java/lang/Object",
		new String[] { "pkg/Mesurable" });
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
		null, new Integer(-1)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
		null, new Integer(0)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
		null, new Integer(1)).visitEnd();
		cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
		"(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();
		byte[] b = cw.toByteArray();
	}
	
	
	
	public static void main(String[] args) throws Exception, SecurityException {
		ClassWriter cw = new ClassWriter(0);
		
		// Opcodes.V1_1 jdk的版本
		// 类名中如果有包路径用/替换.
		cw.visit(Opcodes.V1_1, Opcodes.ACC_PUBLIC, "Example", null, "java/lang/Object", null);
		
		MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL,"java/lang/Object","<init>","()V",false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	
		mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn("HelloWorld!");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(2, 2);
		mv.visitEnd();
		byte [] code = cw.toByteArray();
		
		MyTest loader = new MyTest();
		Class<?> clazz = loader.defineClass("Example", code, 0, code.length);
		Method method = clazz.getMethods()[0];
		method.invoke(null, new Object[]{null});

	}

}
