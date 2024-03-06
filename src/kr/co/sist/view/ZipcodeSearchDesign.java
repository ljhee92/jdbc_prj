package kr.co.sist.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kr.co.sist.prepared.event.ZipcodeSearchEvent;

@SuppressWarnings("serial")
public class ZipcodeSearchDesign extends JFrame {
	
	private JTextField jtfDong;
	private JButton jbtnSearch;
	private JTable jtabResult;
	private DefaultTableModel dtmJtabResult;
	private JScrollPane jspJtaResult;
	private JPanel jpNorth;
	private Font font;
	private JLabel jlblDong;
	
	public ZipcodeSearchDesign() {
		super("우편번호 검색");
		jpNorth = new JPanel();
		
		setJtf();
		setJbtn();
		setJtab();
		setFont();
		setAction();
		
		add("North", jpNorth);
		add("Center", jspJtaResult);
		
		setBounds(100, 100, 800, 600);
		setVisible(true);
	}	// ZipcodeSearchDesign
	
	public void setJtf() {
		jlblDong = new JLabel("동 이름) ");
		jtfDong = new JTextField(10);
		
		jpNorth.add(jlblDong);
		jpNorth.add(jtfDong);
	}	// setJtf
	
	public void setJbtn() {
		jbtnSearch = new JButton("검색");
		
		jpNorth.add(jbtnSearch);
	}	// setJbtn
	
	public void setJtab() {
		String[] columnName = {"우편번호", "주소"};
		
		// 행이 없고 컬럼명만 가진 DefualtTableModel 생성
		dtmJtabResult = new DefaultTableModel(columnName, 0);
		
		jtabResult = new JTable(dtmJtabResult);
		jspJtaResult = new JScrollPane(jtabResult);
		
		jtabResult.setEnabled(false);
		
		jspJtaResult.setBorder(new TitledBorder("검색 결과"));
		
		// JTable의 컬럼 넓이 변경
		jtabResult.getColumnModel().getColumn(0).setPreferredWidth(100);
		jtabResult.getColumnModel().getColumn(1).setPreferredWidth(700);
		
		// 테이블 내용 가운데 정렬
		tableCellCenter(jtabResult);
	}	// setJtab
	
	public void tableCellCenter(JTable t) {
		// 테이블 내용 가운데 정렬하기
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	// 디폴트 테이블센렌더러를 생성
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);	// 렌더러의 가로 정렬을 CENTER로
		
		TableColumnModel tcm = t.getColumnModel();	// 정렬할 테이블의 컬럼모델을 가져옴
		
		// 특정 열에 지정
		tcm.getColumn(0).setCellRenderer(dtcr);
		
		// 전체 열에 지정
//		for(int i = 0; i < tcm.getColumnCount(); i++) {
//			tcm.getColumn(i).setCellRenderer(dtcr);
		// 컬럼 모델에서 컬럼의 갯수만큼 컬럼을 가져와 for문을 이용하여 각각의 셀렌더러를 dtcr에 set해줌
//		}	// end for
	}	// tableCellCenter
	
	public void setFont() {
		font = new Font("맑은 고딕", Font.BOLD, 14);
		jtfDong.setFont(font);
		jlblDong.setFont(font);
		jbtnSearch.setFont(font);
	}	// setFont
	
	public void setAction() {
		ZipcodeSearchEvent zse = new ZipcodeSearchEvent(this);
		addWindowFocusListener(zse);
		jtfDong.addActionListener(zse);
		jbtnSearch.addActionListener(zse);
	}	// setAction

	public static void main(String[] args) {
		new ZipcodeSearchDesign();
	}	// main

	public JTextField getJtfDong() {
		return jtfDong;
	}

	public JButton getJbtnSearch() {
		return jbtnSearch;
	}

	public JTable getJtabResult() {
		return jtabResult;
	}

	public DefaultTableModel getDtmJtabResult() {
		return dtmJtabResult;
	}

}	// class