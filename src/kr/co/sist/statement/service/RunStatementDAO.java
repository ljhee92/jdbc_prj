package kr.co.sist.statement.service;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.sist.statement.dao.StatementDAO;
import kr.co.sist.statement.vo.EmployeeVO;


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
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "사원번호나 연봉은 숫자형식이어야 합니다.");
			}	// end catch
		}	// end if
	}	// addEmp
	
	/**
	 * update ~ set ~ from 
	 */
	public void modifyEmp() {
		String inputData = JOptionPane.showInputDialog("사원정보 변경\n사원번호와 변경할 정보의 분류, 내용을 입력해주세요."
				+ "\n사원명 변경 : 사원번호,'사원명',변경할 사원명 입력"
				+ "\n직무 변경 : 사원번호,'직무', 변경할 직무 입력"
				+ "\n연봉 변경 : 사원번호, '연봉', 변경할 연봉 입력"
				+ "\n입사일 변경 : 사원번호, '입사일', 변경할 입사일(예: 2024-03-02) 입력");
		
		if(inputData != null) {
			String[] tempData = inputData.split(",");
			
			if(tempData.length != 3) {
				JOptionPane.showMessageDialog(null, "입력은 사원번호,변경할 정보의 분류,내용 형식이어야 합니다.");
			}	// end if
			
			try {
				// DBMS 테이블의 update를 수행
				int empno = parseInt(tempData[0]);
				StatementDAO sDAO = new StatementDAO();
				EmployeeVO eVOSelect = sDAO.selectOneEmp(empno);
				EmployeeVO eVOInput = null;
				
				switch(tempData[1]) {
				case "사원명" : eVOInput = new EmployeeVO(empno, tempData[2], eVOSelect.getJob(), eVOSelect.getSal(), eVOSelect.getHiredate()); break;
				case "직무" : eVOInput = new EmployeeVO(empno, eVOSelect.getEname(), tempData[2], eVOSelect.getSal(), eVOSelect.getHiredate()); break;
				case "연봉" : eVOInput = new EmployeeVO(empno, eVOSelect.getEname(), eVOSelect.getJob(), parseDouble(tempData[2]), eVOSelect.getHiredate()); break;
				case "입사일" : 
					if(tempData[2].split("-").length != 3) {
						JOptionPane.showMessageDialog(null, "입사일의 형식은 '년-월-일(예: 2024-03-02)'여야합니다.");
						return;
					}	// end if
					eVOInput = new EmployeeVO(empno, eVOSelect.getEname(), eVOSelect.getJob(), eVOSelect.getSal(), Date.valueOf(tempData[2])); break;
				}	// end case 
				
				int cnt = sDAO.updateEmp(eVOInput);	// 0 ~ n건 변경
				String msg = "";
				
				if(cnt == 0) {	// 변경되지 않았을 때 
					msg = tempData[0] + "번 사원은 존재하지 않습니다. 사원 번호를 확인해주세요.";
					return;
				}	// end if
				msg = tempData[0] + "번으로 " + cnt + "건 변경되었습니다."	;	
				JOptionPane.showMessageDialog(null, msg);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "사원번호나 연봉은 숫자형식이어야 합니다.");
			} catch (SQLException se) {
				se.printStackTrace();
				String errMsg = "";
				switch(se.getErrorCode()) {
				case 1438 : errMsg = "연봉은 정수 5자리, 실수 2자리로 입력 가능합니다."; break;
				case 12899 : errMsg = "사원명과 직무는 한글 3자리까지만 입력 가능합니다."; break;
				default : errMsg = "문제가 발생했습니다. 잠시 후에 다시 시도해주세요.";
				}	// end case
				JOptionPane.showMessageDialog(null, errMsg);
			} // end catch
		}	// end if
	}	// modifyEmp
	
	/**
	 * delete from
	 */
	public void removeEmp() {
		String inputData = JOptionPane.showInputDialog("삭제할 사원번호를 입력해주세요.");
		
		if(inputData == null) {
			JOptionPane.showMessageDialog(null, "사원번호를 입력해주세요.");	
			return;
		}	// end if

		// 유효성 검증(사원번호는 숫자 4자리)
		if(inputData.length() > 4) {
			JOptionPane.showMessageDialog(null, "사원번호는 숫자 4자리입니다.");
			return;
		}	// end if
		
		// DBMS의 delete를 수행
		try {
			int empno = parseInt(inputData);
			StatementDAO sDAO = new StatementDAO();
			EmployeeVO eVO = sDAO.selectOneEmp(empno);
			
			if(eVO == null) {
				JOptionPane.showMessageDialog(null, empno + "번 사원은 존재하지 않습니다. 사원번호를 확인해주세요.");
				return;
			}	// end if
			
			int chkDelete = JOptionPane.showConfirmDialog(null, empno + "번 사원 : " + eVO.getEname() + "님의 사원정보를 삭제하시겠습니까?");
			
			switch(chkDelete) {
			case JOptionPane.NO_OPTION :
			case JOptionPane.CANCEL_OPTION :
				return;
			case JOptionPane.OK_OPTION :
				int cnt = sDAO.deleteEmp(empno);
				if(cnt != 0) {
					JOptionPane.showMessageDialog(null, empno + "번으로 " + cnt + "건 삭제되었습니다.");
				}	// end if 
				break;
			}	// end case
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "사원번호는 숫자형태여야 합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}	// end catch 
		
	}	// remeveEmp
	
	/**
	 * select * from
	 */
	public void searchAllEmp() {
		// DBMS에서 조회된 결과를 받아서 사용자에게 보여준다.
		StatementDAO sDAO = new StatementDAO();
		try {
			List<EmployeeVO> listAllEmp = sDAO.selectAllEmp();
			
			StringBuilder output = new StringBuilder();
			output.append("사원번호\t사원명\t직무\t연봉\t입사일\n");
			
			if(listAllEmp.isEmpty()) {
				output.append("데이터가 없습니다.");
			} else {
				for(EmployeeVO eVO : listAllEmp) {
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
					output.append(eVO.getEmpno()).append("\t")
					.append(eVO.getEname()).append("\t")
					.append(eVO.getJob()).append("\t")
					.append(eVO.getSal()).append("\t")
					.append(sdf.format(eVO.getHiredate())).append("\n");
				}	// end for
			}	// end else
			
			JTextArea jta = new JTextArea(output.toString(), 7, 50);
			JScrollPane jsp = new JScrollPane(jta);
			jta.setEditable(false);
			JOptionPane.showMessageDialog(null, jsp);
		} catch (SQLException e) {
			e.printStackTrace();
		}	// end catch
	}	// searchAllEmp
	
	/**
	 * select * from ~ where
	 */
	public void searchOneEmp() {
		String inputData = JOptionPane.showInputDialog("특정 사원정보 검색\n검색할 사원번호를 입력해주세요.");
		
		if(inputData == null) {
			JOptionPane.showMessageDialog(null, "사원번호를 입력해주세요.");
		}	// end if
		
		try {
			int empno = parseInt(inputData);
			
			// DBMS에서 조회된 결과를 받아서 사용자에게 보여준다.
			StatementDAO sDAO = new StatementDAO();
			EmployeeVO eVO = sDAO.selectOneEmp(empno);
			
			StringBuilder output = new StringBuilder();
			output.append(empno).append("번 사원번호 검색 결과\n\n");
			if(eVO == null) {
				JOptionPane.showMessageDialog(null, empno + "번 사원번호가 존재하지 않습니다. 사원번호를 확인해주시기 바랍니다.");
			} else {
				output.append("사원명 : ").append(eVO.getEname()).append("\n");
				output.append("직무 : ").append(eVO.getJob()).append("\n");
				output.append("연봉 : ").append(eVO.getSal()).append("\n");
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
				output.append("입사일 : ").append(sdf.format(eVO.getHiredate()));
				
				JTextArea jta = new JTextArea(output.toString(), 7, 30);
				jta.setEditable(false);
				JScrollPane jsp = new JScrollPane(jta);
				JOptionPane.showMessageDialog(null, jsp);
			}	// end else
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "사원번호는 숫자형식으로 입력해야 합니다.");
		} catch (SQLException se) {
			se.printStackTrace();
		}	// end catch
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