package kr.co.sist.prepared.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.prepared.dao.ZipcodeDAO;
import kr.co.sist.view.ZipcodeSearchDesign;
import kr.co.sist.vo.ZipcodeVO;

public class ZipcodeSearchEvent extends WindowAdapter implements ActionListener {
	
	private ZipcodeSearchDesign zsd;
	
	public ZipcodeSearchEvent(ZipcodeSearchDesign zsd) {
		this.zsd = zsd;
	}	// ZipcodeSearchEvent

	@Override
	public void actionPerformed(ActionEvent e) {
		// 버튼을 누르거나, 엔터를 치거나 모두 적용
		String dong = zsd.getJtfDong().getText().trim();
		
		if(dong.isEmpty()) {
			JOptionPane.showMessageDialog(zsd, "동 이름은 필수 입력입니다.");
			return;
		}	// end if
		
		setZipcode(dong);
		
		zsd.getJtfDong().setText("");
	}	// actionPerformed
	
	private void setZipcode(String dong) {
		ZipcodeDAO zDAO = ZipcodeDAO.getInstance();
		DefaultTableModel dtmJtabResult = zsd.getDtmJtabResult();
		
		try {
			// 입력된 동을 사용한 검색 결과를 받아와서
			List<ZipcodeVO> listZVO = zDAO.selectZipcode(dong);
			
			String[] rowDataArr = new String[2];
			ZipcodeVO zVO = null;
			StringBuilder sbAddr = new StringBuilder();
			
			// 값을 설정하기 전에 모델 객체를 초기화 한다.
			dtmJtabResult.setRowCount(0);
			
			if(listZVO.isEmpty()) {
				JOptionPane.showMessageDialog(zsd, dong + "은 없는 동입니다.");
				// list가 없어서 for를 타지 않으므로 early return 하지 않아도 됨.
			}	// end if
			
			for(int i = 0; i < listZVO.size(); i++) {
				zVO = listZVO.get(i);
				
				sbAddr.append(zVO.getSido()).append(" ")
				.append(zVO.getGugun()).append(" ")
				.append(zVO.getDong()).append(" ")
				.append(zVO.getBunji());
				
				rowDataArr[0] = zVO.getZipcode();
				rowDataArr[1] = sbAddr.toString();
				
				sbAddr.delete(0, sbAddr.length());	// StringBuilder 초기화
				
				dtmJtabResult.addRow(rowDataArr);
			}	// end for
		} catch (Exception e) {
			JOptionPane.showMessageDialog(zsd, "테이블 검색 중 문제 발생");
			e.printStackTrace();
		}	// end catch
	}	// setZipcode

}	// ZipcodeSearchEvent