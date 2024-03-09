package day0307;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class LoginDesign extends JFrame implements ActionListener {
	
	private JTextField jtfId;
	private JPasswordField jpfPw;
	private JLabel jlResult;
	
	public LoginDesign() {
		super("SQLInjection 연습");
		
		addComponent();
		windowClose();
	}	// HomeWork0129_2
	
	public void addComponent() {
		jtfId = new JTextField();
		jpfPw = new JPasswordField();
		jlResult = new JLabel();
		
		JScrollPane jspId = new JScrollPane(jtfId);
		JScrollPane jspPw = new JScrollPane(jpfPw);
		JScrollPane jspResult = new JScrollPane(jlResult);
		
		jspId.setBorder(new TitledBorder("아이디"));
		jspPw.setBorder(new TitledBorder("비밀번호"));
		jspResult.setBorder(new TitledBorder("결과"));
		
		add(jspId);
		add(jspPw);
		add(jspResult);
		
		jtfId.addActionListener(this);
		jpfPw.addActionListener(this);
		jlResult.setText("아이디와 비밀번호를 입력하세요.");
		
		setLayout(new GridLayout(3, 1));
		setBounds(100, 100, 300, 200);
		setResizable(false);
		setVisible(true);
	}	// addComponent
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = jtfId.getText().trim();
		String pw = String.valueOf(jpfPw.getPassword()).trim();
		chkNull(id, pw);
	}	// actionPerformed
	
	private void chkNull(String id, String pw) {
		jlResult.setForeground(Color.red);
		
		if(id.isEmpty()) {
			jtfId.requestFocus();
			jlResult.setText("아이디를 입력하세요.");
			return;
		}	// end if
		if(pw.isEmpty()) {
			jpfPw.requestFocus();
			jlResult.setText("비밀번호를 입력하세요.");
			return;
		}	// end if
		
		login(id, pw);
	}	// chkNull
	
//	public String blockInjection(String str) {
//		return str.replace("'", "").replace(" ", "");
//	}	// blockInjection
	
	private void login(String id, String pw) {
		StringBuilder output = new StringBuilder("로그인 실패");
		Color color = Color.RED;
		
		// DBMS를 연동하여 로그인 작업을 수행
		LoginDAO lDAO = LoginDAO.getInstance();
		LoginResultVO lrVO = null;
		
//		id = blockInjection(id);
//		pw = blockInjection(pw);
		
		LoginVO lVO = new LoginVO(id, pw);
		
		try {
//			lrVO = lDAO.selectLogin(lVO);
			lrVO = lDAO.selectPreParedLogin(lVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}	// end catch
		
		if(lrVO != null)	{	// 로그인 결과 객체가 존재하면 로그인 성공
			output.delete(0, output.length());	// 기존에 output에 할당되었던 로그인 실패 제거
			output.append(lrVO.getName()).append("님 로그인 성공! ")
			.append("(가입일 : ").append(lrVO.getInput_date()).append(")");
			color = Color.BLUE;
		}	// end if
		
		// label 설정
		jlResult.setText(output.toString());
		jlResult.setForeground(color);
	}	// login
	
	public void windowClose() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}	// windowClosing
		});
	}	// windowClose

	public static void main(String[] args) {
		new LoginDesign();
	}	// main

}	// class