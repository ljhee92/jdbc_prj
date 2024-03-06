package kr.co.sist.vo;

import java.sql.Date;

public class TestClobVO {
	
	private int num;
	private String title, content, writer;
	private Date input_date;
	
	public TestClobVO() {
	}

	public TestClobVO(int num, String title, String content, String writer, Date input_date) {
		super();
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.input_date = input_date;
	}


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getInput_date() {
		return input_date;
	}

	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

	@Override
	public String toString() {
		return "TestClobVO [num=" + num + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", input_date=" + input_date + "]";
	}
	
}	// class