package kr.co.sist.callable.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import kr.co.sist.dao.DbConnection;
import kr.co.sist.statement.vo.EmployeeVO;
import kr.co.sist.vo.ResultVO;

public class CallableStatementDAO {
	
	private static CallableStatementDAO csDAO;
	
	private CallableStatementDAO() {
	}
	
	public static CallableStatementDAO getInstance() {
		if(csDAO == null) {
			csDAO = new CallableStatementDAO();
		}	// end if
		return csDAO;
	}	// getInstance
	
	public ResultVO insertEmp(EmployeeVO eVO) throws SQLException {
		ResultVO rVO = null;
		DbConnection dbCon = DbConnection.getInstance();
		
		Connection con = null;
		CallableStatement cstmt = null;
		
		try {
			String id = "scott";
			String pass = "tiger";
			con = dbCon.getConnection(id, pass);
			
			cstmt = con.prepareCall("{call insert_employee(?, ?, ?, ?, ?, ?)}");
			
			// in parameter
			cstmt.setInt(1, eVO.getEmpno());
			cstmt.setString(2, eVO.getEname());
			cstmt.setString(3, eVO.getJob());
			cstmt.setDouble(4, eVO.getSal());
			
			// out parameter
			cstmt.registerOutParameter(5, Types.NUMERIC);
			cstmt.registerOutParameter(6, Types.VARCHAR);
			
			cstmt.execute();
			
			rVO = new ResultVO(cstmt.getInt(5), cstmt.getString(6));
		} finally {
			dbCon.dbClose(null, cstmt, con);
		}	// end finally
		
		return rVO;
	}	// insertEmp
	
	public ResultVO updateEmp(EmployeeVO eVO) throws SQLException {
		ResultVO rVO = null;
		DbConnection dbCon = DbConnection.getInstance();
		
		Connection con = null;
		CallableStatement cstmt = null;
		
		try {
			String id = "scott";
			String pass = "tiger";
			con = dbCon.getConnection(id, pass);
			
			cstmt = con.prepareCall("{call update_employee(?, ?, ?, ?, ?, ?, ?)}");
			
			// in parameter
			cstmt.setInt(1, eVO.getEmpno());
			cstmt.setString(2, eVO.getEname());
			cstmt.setString(3, eVO.getJob());
			cstmt.setDouble(4, eVO.getSal());
			cstmt.setDate(5, eVO.getHiredate());
			
			// out parameter
			cstmt.registerOutParameter(6, Types.NUMERIC);
			cstmt.registerOutParameter(7, Types.VARCHAR);
			
			cstmt.execute();
			
			rVO = new ResultVO(cstmt.getInt(6), cstmt.getString(7));
		} finally {
			dbCon.dbClose(null, cstmt, con);
		}	// end finally
		return rVO;
	}	// updateEmp
	
	public EmployeeVO selectOneEmp(int empno) throws SQLException {
		EmployeeVO eVO = null;
		DbConnection dbCon = DbConnection.getInstance();
		
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String id = "scott";
			String pass = "tiger";
			con = dbCon.getConnection(id, pass);
			
			cstmt = con.prepareCall("{call select_one_employee(?, ?, ?)}");
			
			// in parameter
			cstmt.setInt(1, empno);
			
			// out parameter
			cstmt.registerOutParameter(2, Types.REF_CURSOR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			
			cstmt.execute();
			
			rs = (ResultSet) cstmt.getObject(2);
			if(rs.next()) {
				eVO = new EmployeeVO(empno, rs.getString("ename"), rs.getString("job"),
						rs.getDouble("sal"), rs.getDate("hiredate"), rs.getString("hiredate2"));
			}	// end if
			System.out.println(cstmt.getString(3));
		} finally {
			dbCon.dbClose(null, cstmt, con);
		}	// end finally
		return eVO;
	}	// selectOneEmp

}	// class