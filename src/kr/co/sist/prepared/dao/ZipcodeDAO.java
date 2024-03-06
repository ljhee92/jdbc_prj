package kr.co.sist.prepared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;
import kr.co.sist.vo.ZipcodeVO;

public class ZipcodeDAO {
	
	private static ZipcodeDAO zDAO;
	
	private ZipcodeDAO() {
	}	// ZipcodeDAO
	
	public static ZipcodeDAO getInstance() {
		if(zDAO == null) {
			zDAO = new ZipcodeDAO();
		}	// end if
		return zDAO;
	}	// getInstance
	
	public List<ZipcodeVO> selectZipcode(String dong) throws Exception {
		List<ZipcodeVO> listZVO = new ArrayList<ZipcodeVO>();
		DbConnection dbCon = DbConnection.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String id = "scott";
			String pass = "tiger";
			
			con = dbCon.getConnection(id, pass);
			
			String selectZipcode = "select zipcode, sido, gugun, dong, nvl(bunji, ' ') bunji from zipcode where dong like ?||'%'";
			pstmt = con.prepareStatement(selectZipcode);
			
			pstmt.setString(1, dong);
			
			rs = pstmt.executeQuery();
			
			ZipcodeVO zVO = null;
			
			while(rs.next()) {
				zVO = new ZipcodeVO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"), rs.getString("dong"), rs.getString("bunji"));
				listZVO.add(zVO);
			}	// end while
		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}	// end finally
		
		return listZVO;
	}	// selectZipcode

}	// ZipcodeDAO