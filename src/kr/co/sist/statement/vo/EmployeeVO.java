package kr.co.sist.statement.vo;

import java.sql.Date;

public class EmployeeVO {
	
	private int empno;
	private String ename, job;
	private double sal;
	private Date hiredate;
	private String hiredate2;
	
	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getHiredate2() {
		return hiredate2;
	}

	public void setHiredate2(String hiredate2) {
		this.hiredate2 = hiredate2;
	}

	public EmployeeVO() {
	
	}

	public EmployeeVO(int empno, String ename, String job, double sal, Date hiredate) {
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.sal = sal;
		this.hiredate = hiredate;
	}

	public EmployeeVO(int empno, String ename, String job, double sal, Date hiredate, String hiredate2) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.sal = sal;
		this.hiredate = hiredate;
		this.hiredate2 = hiredate2;
	}

	@Override
	public String toString() {
		return "EmployeeVO [empno=" + empno + ", ename=" + ename + ", job=" + job + ", sal=" + sal + ", hiredate="
				+ hiredate + ", hiredate2=" + hiredate2 + "]";
	}

}	// calss