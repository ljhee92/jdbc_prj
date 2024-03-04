package kr.co.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton Pattern을 사용한 DBMS Connection 관리 Class
 */
public class DbConnection {
	
	private static DbConnection dbCon;
	
	private DbConnection() {
	}	// DbConnection
	
	public static DbConnection getInstance() {
		if(dbCon == null) {	// 최초 호출이거나, 사용 중에 객체가 죽었다면 
			dbCon = new DbConnection();
		}	// end if
		return dbCon;
	}	// getInstance
	
	/**
	 * Connection을 반환하는 method
	 * @param url
	 * @param id
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String url, String id, String pass) throws SQLException {
		Connection con = null;
		
		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	// end catch
		
		// 2. 커넥션 얻기
		con = DriverManager.getConnection(url, id, pass);
		
		return con;
	}	// getConnection

	/**
	 * 로컬 DBMS에 연동하여 Connection을 반환하는 method
	 * @param id
	 * @param pass
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String id, String pass) throws SQLException {
		Connection con = null;
		
		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	// end catch
		
		// 2. 커넥션 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		con = DriverManager.getConnection(url, id, pass);
		
		return con;
	}	// getConnection

	/**
	 * 연결을 종료하는 일을 하는 method
	 * @param rs
	 * @param stmt
	 * @param con
	 * @throws SQLException
	 */
	public void dbClose(ResultSet rs, Statement stmt, Connection con) throws SQLException {
		try {
			if(rs != null) {
				rs.close();
			}	// end if
			if(stmt != null) {
				stmt.close();
			}	// end if
		} finally {	// 앞에서 문제가 생기더라도 커넥션은 무조건 끊어준다.
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
	}	// dbClose
	
}	// class