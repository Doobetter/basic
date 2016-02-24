package basic.asm;

import java.util.HashMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassUtils {

	// 常用类型的class
	public static final HashMap<String, Class<?>> typeClassMap = new HashMap<String, Class<?>>();
	static {
		typeClassMap.put("boolean", boolean.class);
		typeClassMap.put("char", char.class);
		typeClassMap.put("byte", byte.class);
		typeClassMap.put("short", short.class);
		typeClassMap.put("int", int.class);
		typeClassMap.put("float", float.class);
		typeClassMap.put("long", long.class);
		typeClassMap.put("double", double.class);
		typeClassMap.put("Object", Object.class);
		typeClassMap.put("String", String.class);
		typeClassMap.put("Date", java.sql.Date.class);
		typeClassMap.put("Time", java.sql.Time.class);
		typeClassMap.put("Timestamp", java.sql.Timestamp.class);
		typeClassMap.put("BigDecimal", java.math.BigDecimal.class);
	}
	
	public static int getLoadOpcode(Type type){
		if (type != null) {
			switch (type.getSort()) {
			case Type.VOID:
				System.out.println("type can't be void");
				return -1;
			case Type.BOOLEAN:
			case Type.CHAR:
			case Type.BYTE:
			case Type.SHORT:
			case Type.INT:
				return Opcodes.ILOAD;
			case Type.FLOAT:
				return Opcodes.FLOAD;
			case Type.LONG:
				return Opcodes.LLOAD;
			case Type.DOUBLE:
				return Opcodes.DLOAD;
			default:
				// Type.ARRAY:
				// Type.OBJECT:
				return Opcodes.ALOAD;
			}
		} else {
			System.out.println("type is null!");
			return -1;
		}
	}
	
	public static Object getDefalutValue(Type type){
		if (type != null) {
			switch (type.getSort()) {
			case Type.VOID:
				System.out.println("type can't be null!");
				return null;
			case Type.BOOLEAN:
				return false;
			case Type.CHAR:
				return '\u0000';
			case Type.BYTE:
				return (byte)0;
			case Type.SHORT:
				return (short)0;
			case Type.INT:
				return 0;
			case Type.FLOAT:
				return 0.0F;
			case Type.LONG:
				return 0L;
			case Type.DOUBLE:
				return 0.0D;
			default:
				// Type.ARRAY:
				// Type.OBJECT:
				return null;
			}
		} else {
			System.out.println("type is null!");
			return null;
		}
	}
	public static int getReturnOpcode(Type type) {
		if (type != null) {
			switch (type.getSort()) {
			case Type.VOID:
				return Opcodes.RETURN;
			case Type.BOOLEAN:
			case Type.CHAR:
			case Type.BYTE:
			case Type.SHORT:
			case Type.INT:
				return Opcodes.IRETURN;
			case Type.FLOAT:
				return Opcodes.FRETURN;
			case Type.LONG:
				return Opcodes.LRETURN;
			case Type.DOUBLE:
				return Opcodes.DRETURN;
			default:
				// Type.ARRAY:
				// Type.OBJECT:
				return Opcodes.ARETURN;
			}
		} else {
			System.out.println("type is null!");
			return -1;
		}
	}

	public static Class<?> getClass(String name) {
		Class<?> clazz = typeClassMap.get(name);
		if (clazz == null) {
			try {
				clazz = Class.forName(name);
			} catch (ClassNotFoundException e) {
				return clazz;
			}
		}
		return clazz;
	}

	public static void main(String[] args) throws ClassNotFoundException {

		System.out.println(Type.getDescriptor(String.class));
		System.out.println(Type.getInternalName(String.class));
		System.out.println(Type.getMethodDescriptor(Type.getType(HashMap.class), Type.getType(double.class)));
		System.out.println(Type.getType(Class.forName("java.lang.Double")));
	}
}
