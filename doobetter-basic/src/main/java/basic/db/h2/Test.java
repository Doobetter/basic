package basic.db.h2;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;


public class Test {

	public static String SELECT_PERSON_TABLE = "select * from PERSON";
	public static void main(String[] args) throws Exception {
		 JdbcConnectionPool dataSrc = JdbcConnectionPool.create("jdbc:h2:tcp://localhost/mem:ExampleDb", "sa", "");

         // Create Person table in database.
		 ResultSet rs= RunScript.execute(dataSrc.getConnection(), new StringReader(SELECT_PERSON_TABLE));

		 while(rs.next()){

			 System.out.println(rs.getString(1));
			 System.out.println(rs.getString(2));
			 System.out.println(rs.getString(3));
			 
		 }
	}

}
