package day0308;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.sist.dao.DbConnection;

/**
 * 여러 개의 쿼리문이 하나의 Transaction으로 구성되는 경우
 */
public class TransactionDAO {
	
	private Connection con;
	
	public int transaction(String name, String addr) throws SQLException {
		int returnCnt = 0;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		String id = "scott";
		String pass = "tiger";
		
		con = dbCon.getConnection(id, pass);
		con.setAutoCommit(false);	// autoCommit 해제
		
		String insertTransaction = "insert into test_transaction(name, addr) values(?, ?)";
		PreparedStatement pstmt = con.prepareStatement(insertTransaction);
		
		pstmt.setString(1, name);
		pstmt.setString(2, addr);
		
		returnCnt += pstmt.executeUpdate();
		
		String insertTransaction2 = "insert into test_transaction2(name, addr) values(?, ?)";
		PreparedStatement pstmt2 = con.prepareStatement(insertTransaction2);
		
		pstmt2.setString(1, name);
		pstmt2.setString(2, addr);
		
		returnCnt += pstmt2.executeUpdate();
		
		return returnCnt;
	}	// transaction
	
	/**
	 * 목표로 하는 행수를 받아서 commit 또는 rollback을 수행하고, 연결 끊기 
	 */
	public void useTransaction() {
		String name = "김길동";
		String addr = "서울시 강남구 논현동";
		
		try {
			int cnt = transaction(name, addr);
			
			if(cnt == 2) {
				System.out.println("추가 성공");
				con.commit();
			} // end if
		} catch (SQLException e) {
			try {
				System.out.println("작업 취소");
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	// end catch
			e.printStackTrace();
		} finally {
			try {
				con.close();	// 연결 종료되면 insert가 정상 종료라고 판단하여 예외가 발생하기 전까지는 commit이 된다. => 원자성 위배 
			} catch (SQLException e) {
				e.printStackTrace();
			}	// end catch
		}	// end finally
	}	// useTransaction

	public static void main(String[] args) {
		TransactionDAO tDAO = new TransactionDAO();
		tDAO.useTransaction();
	}	// main

}	// class