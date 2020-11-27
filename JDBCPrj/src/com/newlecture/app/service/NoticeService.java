package com.newlecture.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.app.entity.Notice;

public class NoticeService {
	private String url    = "jdbc:oracle:thin:@192.168.137.50:1521/oracle19";
	private String uid    = "newlec";
	private String pwd    = "kikja";
	private String driver = "oracle.jdbc.driver.OracleDriver";

	public List<Notice> getList(int page) throws ClassNotFoundException, SQLException {
		int start = 1 + (page-1)*10; //1, 11, 21, 31, ...
		int end   = 10 * page;       //10, 20, 30, 40, ...
		
		String sql = "select * from  notice_view where num between ? and ?";
		
		Class.forName(driver);
		Connection        con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st  = con.prepareStatement(sql);
		st.setInt(1, start);
		st.setInt(2, end);
		ResultSet rs = st.executeQuery();
		
		List<Notice> list = new ArrayList<Notice>();
		
		while (rs.next()) {
			int id          = rs.getInt   ("id");
			String title    = rs.getString("title");
			String writerId = rs.getString("writer_id");
			Date   regDate  = rs.getDate  ("regdate");
			String content  = rs.getString("content");
			int    hit      = rs.getInt   ("hit");
			String files    = rs.getString("files");
			
			Notice notice = new Notice(
					id,
					title,
					writerId,
					regDate,
					content,
					hit,
					files
					);
			list.add(notice);
		}
	
		rs.close();
		st.close();
		con.close();
		
		return list;
	}
	
	public int insert(Notice notice) throws SQLException, ClassNotFoundException {
		String title     = notice.getTitle();
		String writer_id = notice.getWriterId();
		String content   = notice.getContent();
		String files     = notice.getFiles();
		
		String sql = "INSERT INTO notice ( "
				+ "title,"
				+ " writer_id,"
				+ " content,"
				+ " files"
				+ ") VALUES (?,?,?,?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title);
		st.setString(2, writer_id);
		st.setString(3, content);
		st.setString(4, files);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();
		return result;
	}

	public int getCount() throws SQLException, ClassNotFoundException {
		int count = 0;
		String sql = "select count(id) count from notice";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		Statement  st  = con.createStatement();
		ResultSet  rs  = st.executeQuery(sql);
		
		if (rs.next())
			count = rs.getInt("COUNT");
	
		rs.close();
		st.close();
		con.close();
		
		return count;
	}

	public int update(Notice notice) throws ClassNotFoundException, SQLException {
		String title     = notice.getTitle();
		String writer_id = notice.getWriterId();
		String content   = notice.getContent();
		String files     = notice.getFiles();
		int    id        = notice.getId();
		
		
		String sql = "update notice "
				+ "set"
				+ " title = ?,"
				+ " content = ?,"
				+ " files = ?"
				+ "where id = ?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title);
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, id);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();

		return result;
	}
	
	public int delete(int id) throws SQLException, ClassNotFoundException {
		String sql = "delete notice where id = ?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		
		int result = st.executeUpdate();
		
		st.close();
		con.close();

		return result;
	}

}
