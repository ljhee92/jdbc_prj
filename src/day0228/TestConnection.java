package day0228;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {
	
	public TestConnection() {
		
		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 성공!!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	// end catch
		
		// 2. 로딩된 드라이버를 사용하여 Connection 얻기
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		
		Connection con = null;
		Statement stmt = null;
		
		try {
			con = DriverManager.getConnection(url, id, pass);
			System.out.println("DB 연결 성공!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}	// end catch
		
		// 3. 쿼리문 생성 객체 얻기
		
		// 4. 쿼리문 수행 후 결과 얻기 (CP_DEPT 테이블에 레코드를 추가)
		
	}	// TestConnection

	public static void main(String[] args) {
		new TestConnection();
	}	// main

}	// class