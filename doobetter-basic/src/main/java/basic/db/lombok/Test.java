package basic.db.lombok;

import lombok.extern.log4j.Log4j2;



public class Test {

	public static void main(String[] args) {
		Person person = new Person("A","B","C");
		person.setId("A");
		person.setIdentity("B");
		Person person2 = Person.builder().id("DDD").identity("BBBB").build();
		System.out.println(person);
		System.out.println(person2);
	}

}
