package basic.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


public class BeanClassBuilder extends ClassLoader {
	private ClassWriter classWriter = null;
	private ClassDescriptor classDescriptor = null;

	public BeanClassBuilder(ClassDescriptor desc) {
		this.classDescriptor = desc;
		this.classWriter = new ClassWriter(0);
		String internalName = desc.getName().replace('.', '/');
		String[] impls = desc.getImpls();
		for (int i = 0; i < desc.getImpls().length; i++) {
			impls[i] = impls[i].replace('.', '/');
		}
		classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, internalName, null, "java/lang/Object", impls);
		// 加序列化ID
		this.addSerialVersionUID(1L);
		// 数据字段
		List<FieldDescriptor> fields = desc.getFields();
		if (fields != null) {
			for (FieldDescriptor field : fields) {
				addField(field);
				addSetterMethod(field, internalName);
				addGetterMethod(field, internalName);
			}
		}
		// 构造函数
		addNoArgsConstrutor();
		addAllArgsConstrutor(internalName);
		classWriter.visitEnd();
	}

	/**
	 * 添加无参构造函数
	 */
	public void addNoArgsConstrutor() {
		MethodVisitor methodVisitor = this.classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		methodVisitor.visitCode();
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(1, 1);
		methodVisitor.visitEnd();
	}
	/**
	 * 全参构造函数
	 * @param classInternalName
	 */
	public void addAllArgsConstrutor(String classInternalName) {
		// 变量顺序是个事儿
		ArrayList<FieldDescriptor> fields = this.classDescriptor.getFields();
		Type [] fieldTypes = new Type[fields.size()];
		int len = fields.size();
		for(int i = 0 ; i < len ; i++){
			fieldTypes[i] = Type.getType(ClassUtils.getClass(fields.get(i).getType()));
		}
		String methodDesc = Type.getMethodDescriptor(Type.VOID_TYPE, fieldTypes);
		MethodVisitor methodVisitor = this.classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", methodDesc, null, null);
		methodVisitor.visitCode();
		// 调用super构造函数
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		// 各个field赋值
		int maxVarStackSize = 0;
		// for this
		int operand = 1;
		int size = 1;
		for(int i = 0 ; i < len ; i++){
			size = fieldTypes[i].getSize() ;
			if(size > maxVarStackSize){
				maxVarStackSize = size;
			}
			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitVarInsn(ClassUtils.getLoadOpcode(fieldTypes[i]), operand);
			operand += size;
			methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, classInternalName, fields.get(i).getName(), fieldTypes[i].getDescriptor());
		}
		
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(1 + maxVarStackSize, operand);
		methodVisitor.visitEnd();
	}

	
	/**
	 * 加序列化ID
	 * @param svuid
	 */
	public void addSerialVersionUID(long svuid) {
		FieldVisitor fv = this.classWriter.visitField(Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "serialVersionUID", "J", null, svuid);
		if (fv != null) {
			fv.visitEnd();
		}
	}

	/**
	 * 添加成员变量
	 * 
	 * @param field
	 */
	public void addField(FieldDescriptor field) {
		Type type = Type.getType(ClassUtils.getClass(field.getType()));
		FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PRIVATE, field.getName(), type.getDescriptor(), null, ClassUtils.getDefalutValue(type));

		// FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PRIVATE, field.getName(), type.getDescriptor(), null, null);
//		if (field.isQueryField()) {
//			AnnotationVisitor annotationVisitor = fieldVisitor.visitAnnotation(Type.getDescriptor(QuerySqlField.class), true);
//			if (field.isIndexField()) {
//				annotationVisitor.visit("index", true);
//			}
//			annotationVisitor.visitEnd();
//		}
		fieldVisitor.visitEnd();
	}

	/**
	 * 加set方法
	 * @param field
	 * @param classInternalName
	 */
	public void addSetterMethod(FieldDescriptor field, String classInternalName) {
		// 方法名字
		String methodName = CodeUtils.toSetterMethodName(field.getName());
		Type type = Type.getType(ClassUtils.getClass(field.getType()));
		int size = type.getSize();
		// 方法描述
		String methodDescriptor = Type.getMethodDescriptor(Type.VOID_TYPE, type);
		MethodVisitor methodVisitor = this.classWriter.visitMethod(Opcodes.ACC_PUBLIC, methodName, methodDescriptor, null, null);
		methodVisitor.visitCode();
		// LOAD this
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		// LOAD var
		methodVisitor.visitVarInsn(ClassUtils.getLoadOpcode(type), 1);
		// 赋值
		methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, classInternalName, field.getName(), type.getDescriptor());
		methodVisitor.visitInsn(Opcodes.RETURN);
		// 栈大小 、局部变量数
		methodVisitor.visitMaxs(1 + size, 1 + size);
		methodVisitor.visitEnd();

	}

	/**
	 * 加get方法
	 * @param field
	 * @param classInternalName
	 */
	public void addGetterMethod(FieldDescriptor field, String classInternalName) {
		String methodName = CodeUtils.toGetterMethodName(field.getName());
		Type type = Type.getType(ClassUtils.getClass(field.getType()));
		int size = type.getSize();
		String methodDescriptor = Type.getMethodDescriptor(type);
		MethodVisitor methodVisitor = this.classWriter.visitMethod(Opcodes.ACC_PUBLIC, methodName, methodDescriptor, null, null);
		methodVisitor.visitCode();
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitFieldInsn(Opcodes.GETFIELD, classInternalName, field.getName(), type.getDescriptor());
		methodVisitor.visitInsn(ClassUtils.getReturnOpcode(type));
		methodVisitor.visitMaxs(size, 1);
		methodVisitor.visitEnd();
	}

	/**
	 * 生成class文件
	 * @param dirStr
	 */
	public void generateClassFile(String dirStr) {
		if (!dirStr.endsWith(File.separator)) {
			dirStr = dirStr + File.separator;
		}
		byte[] b = this.classWriter.toByteArray();
		File dir = new File(dirStr + this.classDescriptor.getPackageName().replace('.', File.separatorChar));
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dirStr + this.classDescriptor.getName().replace('.', File.separatorChar) + ".class");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(b);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 得到Bean的Class
	 * @return
	 */
	public Class<?> getBeanCalss() {
		byte[] code = this.classWriter.toByteArray();
		return this.defineClass(null, code, 0, code.length);
	}

	public static void main(String[] args) throws Exception {

		ClassDescriptor desc = new ClassDescriptor();
		desc.setName("com.sugon.bd.ignite.model.HotelEvent");
		desc.setImpls(new String[] { "java.io.Serializable" });
		desc.setPackageName("com.sugon.bd.ignite.model");
		desc.setSimpleName("HotelEvent");
		desc.addField(new FieldDescriptor("id", "double", true, true, false));
		desc.addField(new FieldDescriptor("userName", "int", true, false, false));
		BeanClassBuilder bcb = new BeanClassBuilder(desc);
		bcb.generateClassFile("D:\\workspace2\\mem-join\\target\\classes");

		Class<?> clazz = bcb.getBeanCalss();
		Method m1 = clazz.getDeclaredMethod("getId");
		Method m2 = clazz.getDeclaredMethod("setId", double.class);
		Method m3 = clazz.getDeclaredMethod("getUserName");
		Constructor<?> constructor = clazz.getConstructor(double.class, int.class);
		Object obj = constructor.newInstance(123.0,111);
		//Object obj = clazz.newInstance();
		//m2.invoke(obj, 234);
		System.out.println(m1.invoke(obj));
		System.out.println(m3.invoke(obj));

	}
}
