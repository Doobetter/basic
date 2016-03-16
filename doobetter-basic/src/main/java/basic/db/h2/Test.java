package basic.db.h2;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;
import org.h2.util.ScriptReader;


public class Test {

	public static String SELECT_PERSON_TABLE = "select SYSTIMESTAMP() as ts, session, start from Auth";
	
	public static String INSERT_DATA = "delete from Auth;\n insert into Auth(session, start, last) values('abc', CURRENT_TIMESTAMP, 1);";
	public static String DELETE_DATA= "delete from Auth where  TIMESTAMPDIFF('SECOND',start,SYSTIMESTAMP()) > last";
	
	public static String SELECT_TABLE = "select TIMESTAMPDIFF('SECOND',start,SYSTIMESTAMP()) as diff, session, start from Auth";
	
	public static void main(String[] args) throws Exception {
		 JdbcConnectionPool dataSrc = JdbcConnectionPool.create("jdbc:h2:tcp://localhost/mem:SessionDb", "sa", "");
		 dataSrc.setMaxConnections(2);
	
		 System.out.println("1 max :"+dataSrc.getMaxConnections());
		 System.out.println("1 active :"+dataSrc.getActiveConnections());
		 System.out.println("active :"+dataSrc.getLoginTimeout());
		 Connection conn = dataSrc.getConnection();
		 RunScript.execute(conn, new StringReader(INSERT_DATA));
		// conn.close();
		 System.out.println("2 max :"+dataSrc.getMaxConnections());
		 System.out.println("2 active :"+dataSrc.getActiveConnections());
		 Thread.sleep(10000);
         // Create Person table in database.
		 conn = dataSrc.getConnection();
		 ResultSet rs= RunScript.execute(conn, new StringReader(SELECT_TABLE));
	
		 while(rs.next()){

			 System.out.println(rs.getString(1));
			 System.out.println(rs.getString(2));
			 System.out.println(rs.getString(3));
			 
		 }
		 rs.close();
		// conn.close();
		 System.out.println("3 max :"+dataSrc.getMaxConnections());
		 System.out.println("3 active :"+dataSrc.getActiveConnections());
		 
		 conn = dataSrc.getConnection();
		 int fetch = executeUpdate(conn, DELETE_DATA);
		 System.out.println("rz size : " + fetch);
		 //conn.close();
		 System.out.println("4 max :"+dataSrc.getMaxConnections());
		 System.out.println("4 active :"+dataSrc.getActiveConnections());
		 
		 
		 rs = RunScript.execute(dataSrc.getConnection(), new StringReader(SELECT_TABLE));

		 while(rs.next()){

			 System.out.println(rs.getString(1));
			 System.out.println(rs.getString(2));
			 System.out.println(rs.getString(3));
			 
		 }
		 
		 System.out.println("5 max :"+dataSrc.getMaxConnections());
		 System.out.println("5 active :"+dataSrc.getActiveConnections());

	}

	public static int executeUpdate(Connection conn , String sql) throws SQLException{
		PreparedStatement stmt = conn.prepareStatement(sql);
		int rows = stmt.executeUpdate();
		stmt.close();
		return rows;
	}
}
