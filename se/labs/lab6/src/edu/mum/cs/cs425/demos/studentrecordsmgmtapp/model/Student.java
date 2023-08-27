package edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model;

import java.time.LocalDate;

public class Student {
	private String studentId;
	private String name;
	private LocalDate dateOfAdmission;
	
	public Student() {
		super();
	}

	public Student(String studentId, String name) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.dateOfAdmission = LocalDate.now();
	}

	public Student(String studentId, String name, LocalDate dateOfAdmission) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.dateOfAdmission = dateOfAdmission;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfAdmission() {
		return dateOfAdmission;
	}
	public void setDateOfAdmission(LocalDate dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", dateOfAdmission=" + dateOfAdmission + "]";
	}
	
}
