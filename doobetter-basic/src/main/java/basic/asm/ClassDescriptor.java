package basic.asm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 作为Bean所有的类都是public 
 * 所有field都是
 * @author LC
 *
 */
public class ClassDescriptor {
	private String packageName = null;
	// just Name 
	private String simpleName = null;
	// package + simpleName
	private String name; 
	// fully Names. eg. java.io.Serializable
	private String [] impls = null;
	private ArrayList<FieldDescriptor> fields = null;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getImpls() {
		return impls;
	}
	public void setImpls(String[] impls) {
		this.impls = impls;
	}
	public ArrayList<FieldDescriptor> getFields() {
		return fields;
	}
	public void setFields(ArrayList<FieldDescriptor> fields) {
		this.fields = fields;
	}
	
	public void addField(FieldDescriptor field){
		if(this.fields == null){
			this.fields = new ArrayList<FieldDescriptor>();
		}else{
			if(this.fields.contains(field)){
				return ;
			}
		}

		this.fields.add(field);
	}
}
