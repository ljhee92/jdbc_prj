package kr.co.sist.prepared.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;
import kr.co.sist.statement.vo.EmployeeVO;

public class PreparedStatementDAO {
	
	private static PreparedStatementDAO psDAO;
	
	private PreparedStatementDAO() {
	}	// PreparedStatementDAO
	
	public static PreparedStatementDAO getInstance() {
		if(psDAO == null) {
			psDAO = new PreparedStatementDAO();
		}	// end if
		return psDAO;
	}	// getInstance
	
	public void insertEmp(EmployeeVO eVO) throws SQLException {
		DbConnection dbCon = DbConnection.getInstance();
		
		// 1. 드라이버 로딩
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 2. 커넥션 얻기
			String id = "scott";
			String pass = "tiger";
			
			con = dbCon.getConnection(id, pass);
			
			// 3. 쿼리문 생성 객체 얻기
			String insertEmp = "insert into employee(empno, ename, job, sal) values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(insertEmp);
			
			// 4. 바인드 변수 값 설정
			pstmt.setInt(1, eVO.getEmpno());
			pstmt.setString(2, eVO.getEname());
			pstmt.setString(3, eVO.getJob());
			pstmt.setDouble(4, eVO.getSal());
			
			// 5. 쿼리문 수행 후 결과 얻기 (주의 : Statement의 executeXXX(SQL) method는 사용하지 않는다.)
			pstmt.executeUpdate();
		} finally {
			// 6. 연결 끊기 
			dbCon.dbClose(null, pstmt, con);
		}	// end finally
	}	// insertEmp
	
	/**
	 * 사원번호에 해당하는 레코드를 찾아 정보를 변경
	 * @param eVO
	 * @return
	 * @throws SQLException
	 */
	public int updateEmp(EmployeeVO eVO) throws SQLException {
		int cnt = 0;
		DbConnection dbCon = DbConnection.getInstance();
		
		// 1. 드라이버 로딩
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 2. 커넥션 얻기
			String id = "scott";
			String pass = "tiger";
			con = dbCon.getConnection(id, pass);
			
			// 3. 쿼리문 생성 객체 얻기
			String updateEmp = "update employee set ename = ?, job = ?, sal = ?, hiredate = ? where empno = ?";
			pstmt = con.prepareStatement(updateEmp);
			
			// 4. 바인드 변수 값 설정
			pstmt.setString(1, eVO.getEname());
			pstmt.setString(2, eVO.getJob());
			pstmt.setDouble(3, eVO.getSal());
			pstmt.setDate(4, eVO.getHiredate());
			pstmt.setInt(5, eVO.getEmpno());
			
			// 5. 쿼리문 수행 후 결과 얻기
			cnt = pstmt.executeUpdate();
			
		} finally {
			// 6. 연결 끊기 
			dbCon.dbClose(null, pstmt, con);
		}	// end finally
		return cnt;
	}	// updateEmp
	
	public int deleteEmp(int empno) throws SQLException {
		int cnt = 0;
		DbConnection dbCon = DbConnection.getInstance();
		
		// 1. 드라이버 로딩
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			// 2. 커넥션 얻기
			String id = "scott";
			String pass = "tiger";
			
			con = dbCon.getConnection(id, pass);
			
			// 3. 쿼리문 생성 객체 얻기
			String deleteEmp = "delete from employee where empno = ?";
			pstmt = con.prepareStatement(deleteEmp);
			
			// 4. 바인드 변수 값 설정
			pstmt.setInt(1, empno);
			
			// 5. 쿼리문 수행 후 결과 얻기 
			cnt = pstmt.executeUpdate();
			
		} finally {
			// 6. 연결 끊기 
			dbCon.dbClose(null, pstmt, con);
		}	// end finally
		return cnt;
	}	// deleteEmp
	
	public List<EmployeeVO> selectAllEmp() throws SQLException {
		List<EmployeeVO> listAllEmp = new ArrayList<EmployeeVO>();
		DbConnection dbCon = DbConnection.getInstance();
		
		// 1. 드라이버 로딩
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 2. 커넥션 얻기
			String id = "scott";
			String pw = "tiger";
			
			con = dbCon.getConnection(id, pw);
			
			// 3. 쿼리문 생성 객체 얻기
			String selectAllEmp = "select empno, ename, job, sal, to_char(hiredate, 'yyyy-mm-dd q\"분기\"') hiredate2 from employee";
			pstmt = con.prepareStatement(selectAllEmp);
			
			// 4. 바인드 변수 값 설정
			// 설정할 값 없음
			
			// 5. 쿼리문 수행 후 결과 얻기
			rs = pstmt.executeQuery();
			
			EmployeeVO eVO = null;
			int empno = 0;
			String ename = "", job = "", hiredate2 = "";
			double sal = 0.0;
			
			while(rs.next()) {
				empno = rs.getInt("empno");
				ename = rs.getString("ename");
				job = rs.getString("job");
				sal = rs.getDouble("sal");
				hiredate2 = rs.getString("hiredate2");
				
				eVO = new EmployeeVO(empno, ename, job, sal, null, hiredate2);
				listAllEmp.add(eVO);
			}	// end while
			
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		}	// end finally
		return listAllEmp;
	}	// selectAllEmp
	
	public EmployeeVO selectOneEmp(int empno) throws SQLException {
		EmployeeVO eVO = null;
		DbConnection dbCon = DbConnection.getInstance();
		
		// 1. 드라이버 로딩
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 2. 커넥션 얻기
			String id = "scott";
			String pass = "tiger";
			
			con = dbCon.getConnection(id, pass);
			
			// 3. 쿼리문 생성 객체 얻기
			String selectOneEmp = "select ename, job, sal, hiredate, to_char(hiredate, 'yyyy-mm-dd') hiredate2 from employee where empno = ?";
			pstmt = con.prepareStatement(selectOneEmp);
			
			// 4. 바인드 변수 값 설정
			pstmt.setInt(1, empno);
			
			// 5. 쿼리문 수행 후 결과 얻기
			rs = pstmt.executeQuery();	// 조회된 결과를 움직일 수 있는 커서의 제어권을 받는다.
			
			String ename = "", job = "", hiredate2 = "";
			double sal = 0.0;
			Date hiredate = null;
			
			if(rs.next()) {
				ename = rs.getString("ename");
				job = rs.getString("job");
				sal = rs.getDouble("sal");
				hiredate = rs.getDate("hiredate");
				hiredate2 = rs.getString("hiredate2");
				
				eVO = new EmployeeVO(empno, ename, job, sal, hiredate, hiredate2);
			}	// end if
		} finally {
			// 6. 연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		}	// end finally
		return eVO;
	}	// selectOneEmp

}	// PreparedStatementDAO