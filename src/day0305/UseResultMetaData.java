package day0305;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.dao.DbConnection;

/**
 * 조회하는 테이블의 Schema 정보를 얻을 때 사용하는 객체 <br>
 * desc 테이블명 수준의 정보를 얻는다. 더 자세한 정보를 얻을 때에는 DD를 사용해야 한다.
 */
public class UseResultMetaData {
	
	public UseResultMetaData() throws SQLException {
		// EMP 테이블의 MetaData를 얻기
		DbConnection dbCon = DbConnection.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 1. 드라이버 로딩
		
		try {
			// 2. 커넥션 얻기
			String id = "scott";
			String pass = "tiger";
			con = dbCon.getConnection(id, pass);
			
			// 3. 쿼리문 생성 객체 얻기
			String tname = "EMP";
			String selectEmp = "select * from " + tname;
			pstmt = con.prepareStatement(selectEmp);
			
			rs = pstmt.executeQuery();
			
			// 4. 바인드 변수 값 설정
			// 바인드 변수는 값과 묶이는 변수로, 값은 설정할 수 있지만, 컬럼명이나 테이블 명에는 사용할 수 없다.
//			pstmt.setString(1, tname);	// bind 변수로는 사용할 수 없다.
			
			// ResultSetMetaData를 얻는다.
			ResultSetMetaData rsmd = rs.getMetaData();
//			System.out.println("컬럼의 수: " + rsmd.getColumnCount());
//			System.out.println("컬럼명: " + rsmd.getColumnName(1));
//			System.out.println("컬럼 데이터형: " + rsmd.getColumnTypeName(1));
//			System.out.println("컬럼 크기: " + rsmd.getPrecision(1));
//			System.out.println("컬럼 Null 허용: " + rsmd.isNullable(1));
			
			StringBuilder sbOutput = new StringBuilder();
			
			sbOutput.append(tname).append(" 테이블의 정보\n");
			sbOutput.append("컬럼명\tNull 허용 여부\t데이터형 및 크기\n")
			.append("------------------------------------\n");
			
			int size = 0;
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				size = rsmd.getPrecision(i);
				sbOutput.append(rsmd.getColumnName(i)).append("\t");
				sbOutput.append(rsmd.isNullable(i) == 0 ? "Not Null" : "").append("\t");
				sbOutput.append(rsmd.getColumnType(i));
				if(size != 0) {
					sbOutput.append("(").append(size).append(")\n");
				} else {
					sbOutput.append("\n");
				}	// end else

			}	// end for
			
			JTextArea jta = new JTextArea(sbOutput.toString(), 10, 30);
			jta.setEditable(false);
			JScrollPane jsp = new JScrollPane(jta);
			JOptionPane.showMessageDialog(null, jsp);
			// 5. 쿼리문 수행 후 결과 얻기
		} finally {	
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		}	// end finally
	}	// UseResultMetaData

	public static void main(String[] args) {
		try {
			new UseResultMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}	// end catch
	}	// main

}	// class