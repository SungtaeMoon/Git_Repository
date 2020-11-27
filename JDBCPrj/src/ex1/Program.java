package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String url = "jdbc:oracle:thin:@192.168.137.50:1521/oracle19";
		String sql = "SELECT * FROM NOTICE WHERE HIT >= 10 ORDER BY ID";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "newlec", "kikja");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String writerId = rs.getString("writer_id");
			Date regDate = rs.getDate("regdate");
			String content = rs.getString("content");
			int hit = rs.getInt("hit");
			String files = rs.getString("files");
			
			System.out.printf("id:%2d, title:%s, writerId:%s, regDate:%s, content:%s, hit:%d\n",
					            id, title, writerId, regDate, content, hit);
		}

		rs.close();
		st.close();
		con.close();
	}
}