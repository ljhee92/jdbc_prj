package kr.co.sist.statement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import kr.co.sist.statement.vo.EmployeeVO;

/**
 * DAO : Data Access Object - 데이터에 접근하기 위한 객체
 */
public class StatementDAO {
	
	/**
	 * 사원정보를 추가하는 일
	 * @param eVO
	 * @throws SQLException 
	 */
	public void insertEmp(EmployeeVO eVO) throws SQLException {
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
		Statement stmt = null;
		
		try {
			// 2. 로딩된 드라이버로부터 커넥션 얻기
			con = DriverManager.getConnection(url, id, pass);
			
			// 3. 커넥션으로부터 쿼리문 생성 객체 얻기
			stmt = con.createStatement();
			
			// 4. 쿼리문 수행 후 결과 얻기
			StringBuilder insertEmp = new StringBuilder();
			// insert into employee(empno, ename, job, sal) values(1111, '홍길동', '개발자', 4000)
			insertEmp.append("insert into employee(empno, ename, job, sal) values(")
			.append(eVO.getEmpno()).append(", '").append(eVO.getEname()).append("', '")
			.append(eVO.getJob()).append("', ").append(eVO.getSal()).append(")");
			
			// insert는 1건 추가 성공 또는 예외이므로 변수에 별도로 저장하지 않는다.
			stmt.executeUpdate(insertEmp.toString());
		} finally {
			// 5. 연결 끊기 
			if(stmt != null) {
				stmt.close();
			}	// end if
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
		
	}	// insertEmp
	
	/**
	 * 사원정보를 변경하는 일
	 * @param eVO
	 * @return 변경된 건 수
	 */
	public int updateEmp(EmployeeVO eVO) {
		int cnt = 0;
		
		return cnt;
	}	// updateEmp
	
	/**
	 * 사원정보를 삭제하는 일
	 * @param empno
	 * @return 삭제된 건 수
	 */
	public int deleteEmp(int empno) {
		int cnt = 0;
		
		return cnt;
	}	// deleteEmp
	
	/**
	 * 모든 사원정보를 검색하는 일
	 * @return 모든 사원정보 리스트
	 */
	public List<EmployeeVO> selectAllEmp(){
		List<EmployeeVO> listEmp = null;
		
		return listEmp;
	}	//selectAllEmp
	
	/**
	 * 사원 한 명의 정보를 검색하는 일
	 * @param empno
	 * @return 사원 한 명의 정보 
	 */
	public EmployeeVO selectOneEmp(int empno) {
		EmployeeVO eVO = null;
		
		return eVO;
	}	// selectOne
 
}	// class