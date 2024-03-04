package day0304;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsePreparedStatement {
	
	/**
	 * CP_DEPT 테이블에 insert 
	 * @throws SQLException
	 */
	public UsePreparedStatement() throws SQLException {
		// 1. 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	// end catch
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "scott";
		String pass = "tiger";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 2. 커넥션 얻기
			con = DriverManager.getConnection(url, id, pass);
			
			// 3. 쿼리문 생성 객체 얻기(값과 쿼리문이 분리되어 있다.)
			String insertCpDept = "insert into cp_dept(deptno, dname, loc) values(?, ?, ?)";
			pstmt = con.prepareStatement(insertCpDept);
		
			// 4. 바인드 변수에 값 설정
			int deptno = 42;
			String dname = "개발부";
			String loc = "서울";
			
			pstmt.setInt(1, deptno);
			pstmt.setString(2, dname);
			pstmt.setString(3, loc);
			
			// 5. 쿼리문 수행 후 결과 얻기
			// pstmt.executeUpdate();
			int cnt = pstmt.executeUpdate();
			
			System.out.println(cnt + "건 추가되었습니다.");
		} finally {
			// 6. 연결 끊기
			if(pstmt != null) {
				pstmt.close();
			}	// end if
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
	}	// UsePreparedStatement

	public static void main(String[] args) {
		try {
			new UsePreparedStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			// db error 모두 여기서 처리 가능!
		}	// end catch
	}	// main

}	// class