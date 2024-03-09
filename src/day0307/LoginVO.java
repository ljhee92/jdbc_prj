package day0307;

public class LoginVO {
	
	private String id, pass;
	
	public LoginVO() {
	}

	public LoginVO(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "LoginVO [id=" + id + ", pass=" + pass + "]";
	}

}	// class