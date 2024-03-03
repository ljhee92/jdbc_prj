package kr.co.sist.statement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	 * @throws SQLException 
	 */
	public int updateEmp(EmployeeVO eVO) throws SQLException {
		int cnt = 0;
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
			
			// 3. 커넥션으로부터 쿼리 수행 객체 얻기
			stmt = con.createStatement();
			
			// 4. 쿼리 수행 후 결과 얻기
			// update employee set ename = '', job = '', sal = xx, hiredate = xx where empno = 1111
			StringBuilder updateEmp = new StringBuilder();
			updateEmp.append("update employee")
			.append(" set ename = '").append(eVO.getEname())
			.append("', job = '").append(eVO.getJob())
			.append("', sal = ").append(eVO.getSal())
			.append(", hiredate = to_date('").append(eVO.getHiredate()).append("', 'yyyy-mm-dd')")
			.append(" where empno = ").append(eVO.getEmpno());
			
			cnt = stmt.executeUpdate(updateEmp.toString());
		} finally {
			// 5. 연결 끊기
			if(stmt != null) {
				stmt.close();
			}	// end if
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
		
		return cnt;
	}	// updateEmp
	
	/**
	 * 사원정보를 삭제하는 일
	 * @param empno
	 * @return 삭제된 건 수
	 * @throws SQLException 
	 */
	public int deleteEmp(int empno) throws SQLException {
		int cnt = 0;
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
			// 2. 커넥션 얻기
			con = DriverManager.getConnection(url, id, pass);
			
			// 3. 쿼리문 수행 객체 얻기
			stmt = con.createStatement();
			
			// 4. 쿼리문 수행 후 결과 얻기
			StringBuilder deleteEmp = new StringBuilder();
			// delete from employee where empno = xx
			deleteEmp.append("delete from employee where empno = ").append(empno);
			
			cnt = stmt.executeUpdate(deleteEmp.toString());
		} finally {
			// 5. 연결 끊기
			if(stmt != null) {
				stmt.close();
			}	// end if
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
		
		return cnt;
	}	// deleteEmp
	
	/**
	 * 모든 사원정보를 검색하는 일
	 * @return 모든 사원정보 리스트
	 * @throws SQLException 
	 */
	public List<EmployeeVO> selectAllEmp() throws SQLException{
		List<EmployeeVO> listEVO = new ArrayList<EmployeeVO>();
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
		ResultSet rs = null;
		
		try {
			// 2. 로딩된 드라이버에서 커넥션 얻기
			con = DriverManager.getConnection(url, id, pass);
			
			// 3. 커넥션에서 쿼리문 수행 객체 얻기
			stmt = con.createStatement();
			
			// 4. 쿼리문 수행 후 결과 얻기
			String selectAllEmp = "select * from employee";
			
			rs = stmt.executeQuery(selectAllEmp);
			
			EmployeeVO eVO = null;
			int empno = 0;
			String ename = "", job = "";
			double sal = 0.0;
			Date hiredate = null;
			
			while(rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				sal = rs.getDouble("sal");
				hiredate = rs.getDate("hiredate");
				
				eVO = new EmployeeVO(empno, ename, job, sal, hiredate);
				listEVO.add(eVO);
			}	// end while
		} finally {
			// 5. 연결 끊기
			if(rs != null) {
				rs.close();
			}	// end if
			if(stmt != null) {
				stmt.close();
			}	// end if
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
		return listEVO;
	}	//selectAllEmp
	
	/**
	 * 사원 한 명의 정보를 검색하는 일
	 * @param empno
	 * @return 사원 한 명의 정보 
	 * @throws SQLException 
	 */
	public EmployeeVO selectOneEmp(int empno) throws SQLException {
		EmployeeVO eVO = null;
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
		ResultSet rs = null;
		
		try {
			// 2. 로딩된 드라이버로부터 커넥션 얻기
			con = DriverManager.getConnection(url, id, pass);
			
			// 3. 커넥션으로부터 쿼리문 생성 객체 얻기
			stmt = con.createStatement();
			
			// 4. 쿼리문 수행 후 결과 얻기
			// select * from employee where empno = 1111;
			StringBuilder selectOneEmp = new StringBuilder();
			selectOneEmp.append("select * from employee where empno = ").append(empno);
			
			rs = stmt.executeQuery(selectOneEmp.toString());
			
			// 쿼리문이 실행되고 결과가 나왔을 때, 레코드 포인터(cursor) 다음에 레코드가 존재하는지?
			String ename = "", job ="";
			double sal = 0.0;
			Date hireDate = null;
			
			if(rs.next()) {
				// 레코드가 있다면 레코드 포인터는 다음으로 이동하고, 이동된 위치의 컬럼 값을 얻어
				ename = rs.getString("ename");
				job = rs.getString("job");
				sal = rs.getDouble("sal");
				hireDate = rs.getDate("hiredate");
				
				// EmployeeVO Class에 넣는다.
				eVO = new EmployeeVO(empno, ename, job, sal, hireDate);
			}	// end if
			
		} finally {
			// 5. 연결 끊기
			if(rs != null) {
				rs.close();
			}	// end if
			if(stmt != null) {
				stmt.close();
			}	// end if
			if(con != null) {
				con.close();
			}	// end if
		}	// end finally
		return eVO;
	}	// selectOne
 
}	// class