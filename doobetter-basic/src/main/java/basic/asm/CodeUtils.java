package basic.asm;

public class CodeUtils {
    /**
     * ABC_EDF to AbcEdf
     * @param name Source name.
     * @return String converted to java class name notation.
     */
    public static String toJavaClassName(String name) {
        int len = name.length();

        StringBuilder buf = new StringBuilder(len);

        boolean capitalizeNext = true;

        for (int i = 0; i < len; i++) {
            char ch = name.charAt(i);

            if (Character.isWhitespace(ch) || '_' == ch)
                capitalizeNext = true;
            else if (capitalizeNext) {
                buf.append(Character.toUpperCase(ch));

                capitalizeNext = false;
            }
            else
                buf.append(Character.toLowerCase(ch));
        }

        return buf.toString();
    }
	
	/**
     * @param name Source name.
     * @return String converted to java field name notation.
     */
    public static String toJavaFieldName(String name) {
        String javaName = toJavaClassName(name);

        return Character.toLowerCase(javaName.charAt(0)) + javaName.substring(1);
    }
    
    /**
     * 得到变量的get方法名
     * @param fieldName
     * @return
     */
    public static String toGetterMethodName(String fieldName){
    	StringBuilder buf = new StringBuilder("get");
    	buf.append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1));
    	return buf.toString();
    }
    /**
     * 得到变量的set方法名
     * @param fieldName
     * @return
     */
    public static String toSetterMethodName(String fieldName){
    	StringBuilder buf = new StringBuilder("set");
    	buf.append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1));
    	return buf.toString();
    }
}
