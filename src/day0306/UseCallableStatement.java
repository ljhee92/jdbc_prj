package day0306;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import kr.co.sist.dao.DbConnection;

/**
 * Procedure를 호출하는 CallableStatement의 사용
 */
public class UseCallableStatement {
	
	public UseCallableStatement(int num, int num2) throws SQLException {
		DbConnection dbCon = DbConnection.getInstance();
		
		Connection con = null;
		CallableStatement cstmt = null;
		
		try {
			String id = "scott";
			String pass = "tiger";
			
			con = dbCon.getConnection(id, pass);
			cstmt = con.prepareCall("{call plus_proc(?, ?, ?)}");
			
			// in parameter : 값을 입력하기 위한 변수
			cstmt.setInt(1, num);
			cstmt.setInt(2, num2);
			
			// out parameter : 값을 반환하기 위한 변수
			cstmt.registerOutParameter(3, Types.NUMERIC);
			
			cstmt.execute();
			
			// out parameter에 저장된 값 얻기
			int result = cstmt.getInt(3);
			System.out.println(num + " + " + num2 + " = " + result);
		} finally {
			dbCon.dbClose(null, cstmt, con);
		}	// end finally
	}	// UseCallableStatement

	public static void main(String[] args) throws InterruptedException {
		try {
			for(int i = 0; i < 30; i++) {
				new UseCallableStatement(3, 6);
				Thread.sleep(1000);
			}	// end for
		} catch (SQLException e) {
			e.printStackTrace();
		}	// end catch
	}	// main

}	// class