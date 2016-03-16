package basic.db.lombok;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerialize {

	public static void main(String[] args) throws Exception{
		 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("d:/out1.dat" ));
	     
		    User u1 = new User("user1" , "123" );              //User must implement Serializable
		    User u2 = new User("user2" , "456" );
		     
		    out.writeObject(u1);
		    out.writeObject(u2);
		    out.close();
		     
		    ObjectInputStream in = new ObjectInputStream(new FileInputStream("d:/out1.dat" ));
		    User u1_from_file = (User) in.readObject();
		    User u2_from_file = (User) in.readObject();
		     
		    System. out.println(u1_from_file.getUserName());                  //user1
		    System. out.println(u2_from_file.getUserName());                  //user2
		    System. out.println(u1_from_file.getPassword());       //123
		    System. out.println(u2_from_file.getPassword());       //456
		     
		    System. out.println(u1 == u1_from_file);                      //false
		    System. out.println(u2 == u2_from_file);                      //false

	}

}
