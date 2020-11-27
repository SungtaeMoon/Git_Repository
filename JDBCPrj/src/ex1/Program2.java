package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String title = "TEST2";
		String writer_id = "newlec";
		String content = "hahaha";
		String files = "";
		
		
		String url = "jdbc:oracle:thin:@192.168.137.50:1521/oracle19";
		String sql = "INSERT INTO notice ( "
				+ "title,"
				+ " writer_id,"
				+ " content,"
				+ " files"
				+ ") VALUES (?,?,?,?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "newlec", "kikja");
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title);
		st.setString(2, writer_id);
		st.setString(3, content);
		st.setString(4, files);
		
		int result = st.executeUpdate();
		
		System.out.println(result);

		st.close();
		con.close();
	}
}