package kr.co.sist.statement.service;

import javax.swing.JOptionPane;

import kr.co.sist.statement.dao.StatementDAO;
import kr.co.sist.statement.vo.EmployeeVO;

import static java.lang.Integer.parseInt;

import java.sql.SQLException;

import static java.lang.Double.parseDouble;


/**
 * CRUD를 사용한 클래스 <br>
 * StatementDAO를 실행하고, 받아온 데이터를 사용자에게 보여주는 class
 */
public class RunStatementDAO {
	
	/**
	 * insert into ~ values 
	 */
	public void addEmp() {
		String inputData = JOptionPane.showInputDialog("추가할 사원정보를 입력해주세요.\n사원번호,사원명,직무,연봉");
		
		if(inputData != null) {
			String[] tempData = inputData.split(",");
			
			if(tempData.length != 4) {
				JOptionPane.showMessageDialog(null, "입력형식을 확인해주세요.");
				return;
			}	// end if
			
			int empno = parseInt(tempData[0]);
			String ename = tempData[1];
			String job = tempData[2];
			double sal = parseDouble(tempData[3]);
			
			// 입력된 데이터를 하나로 묶어서 관리(VO 사용)
			try {
				EmployeeVO eVO = new EmployeeVO(empno, ename, job, sal, null);
				
				// DB에 추가
				StatementDAO sDAO = new StatementDAO();
				
//				System.out.println(eVO.toString());
				try {
					sDAO.insertEmp(eVO);
					JOptionPane.showMessageDialog(null, empno + "번 사원정보가 추가되었습니다.");
				} catch (SQLException se) {
					se.printStackTrace();
					String errMsg = "";
					switch(se.getErrorCode()) {
					case 1 : errMsg = empno + "번 사원번호가 중복되었습니다."; break;
					case 1438 : errMsg = "연봉은 정수 5자리, 실수 2자리로 입력 가능합니다."; break;
					case 12899 : errMsg = "사원명과 직무는 한글 3자리까지만 입력 가능합니다."; break;
					default : errMsg = "문제가 발생했습니다. 잠시 후에 다시 시도해주세요.";
					}	// end case
					JOptionPane.showMessageDialog(null, errMsg);
				}	// end catch
			} catch (NumberFormatException efn) {
				JOptionPane.showMessageDialog(null, "사원번호나 연봉은 숫자형식이어야 합니다.");
			}	// end catch
		}	// end if
	}	// addEmp
	
	/**
	 * update ~ set ~ from 
	 */
	public void modifyEmp() {
		
	}	// modifyEmp
	
	/**
	 * delete from
	 */
	public void removeEmp() {
		
	}	// remeveEmp
	
	/**
	 * select * from
	 */
	public void searchAllEmp() {
		
	}	// searchAllEmp
	
	/**
	 * select * from ~ where
	 */
	public void searchOneEmp() {
		
	}	// searchOneEmp
	
	/**
	 * 사용자에게 최초로 보여지는 메뉴
	 */
	public void menu() {
		boolean exitFlag = false;
		String inputMenu = "";
		int exitMenu = 0;
		
		do {
			inputMenu = JOptionPane.showInputDialog("메뉴 선택\n1. 사원정보 추가\n2. 사원정보 변경"
					+ "\n3. 사원정보 삭제\n4. 전체 사원정보 검색\n5. 특정 사원정보 검색\n6. 종료");
			
			if(inputMenu != null) {
				switch(inputMenu){
					case "1" : addEmp(); break;
					case "2" : modifyEmp(); break;
					case "3" : removeEmp(); break;
					case "4" : searchAllEmp(); break;
					case "5" : searchOneEmp(); break;
					case "6" : exitMenu = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?");
						switch(exitMenu) {
						case JOptionPane.NO_OPTION : 
						case JOptionPane.CANCEL_OPTION : break;
						case JOptionPane.OK_OPTION : exitFlag = true;
						}	// end case
						break;
					default : JOptionPane.showMessageDialog(null, "메뉴는 1,2,3,4,5,6 중 하나만 입력해주세요.");
				}	// end case 
			}	// end if
		} while(!exitFlag);
	}	// menu

	public static void main(String[] args) {
		RunStatementDAO rsDAO = new RunStatementDAO();
		rsDAO.menu();
	}	// main

}	// class