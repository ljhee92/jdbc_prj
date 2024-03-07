package kr.co.sist.vo;

public class ResultVO {
	
	private int cnt;
	private String errMsg;
	
	public ResultVO() {
	}

	public ResultVO(int cnt, String errMsg) {
		this.cnt = cnt;
		this.errMsg = errMsg;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "ResultVO [cnt=" + cnt + ", errMsg=" + errMsg + "]";
	}
	
}	// class