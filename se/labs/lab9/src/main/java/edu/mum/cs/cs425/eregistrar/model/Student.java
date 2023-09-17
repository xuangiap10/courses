package edu.mum.cs.cs425.eregistrar.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;



//import jakarta.persistence.GeneratedValue;


@Entity
public class Student {

	@Id
	@GeneratedValue
	private long studentId;
	
	@Column(unique = true)
	private String studentNumber;
	private String firstName;
	private String middleName;
	private String lastName;
	private double cgpa;
	
	//@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfEnrollment;
	private String isInternational;//Yes/No
	
	public String getIsInternational() {
		return isInternational;
	}
	public void setIsInternational(String isInternational) {
		this.isInternational = isInternational;
	}
	/*public List<Transcript> getTranscripts() {
		return transcripts;
	}
	public void setTranscripts(List<Transcript> transcripts) {
		this.transcripts = transcripts;
	}
	@OneToMany (cascade = {CascadeType.PERSIST})
	private List<Transcript> transcripts;
	
	@OneToMany (cascade = {CascadeType.PERSIST})
	private List<Classroom> classRooms;*/
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(long studentId, String studentNumber, String firstName, String middleName, String lastName,
			double cgpa, LocalDate dateOfEnrollment) {
		super();
		this.studentId = studentId;
		this.studentNumber = studentNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.cgpa = cgpa;
		this.dateOfEnrollment = dateOfEnrollment;
		//this.transcripts = new ArrayList<>();
		//this.classRooms = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentNumber=" + studentNumber + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", cgpa=" + cgpa + ", dateOfEnrollment="
				+ dateOfEnrollment + ", isInternational=" + isInternational + "]";
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getCgpa() {
		return cgpa;
	}
	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}
	public LocalDate getDateOfEnrollment() {
		return dateOfEnrollment;
	}
	public void setDateOfEnrollment(LocalDate dateOfEnrollment) {
		this.dateOfEnrollment = dateOfEnrollment;
	}
	//Transcript
	/*public void addTranscript(Transcript transcript) {
		transcripts.add(transcript);
	}
	public List<Transcript> getTranscript() {
		return transcripts;
	}
	public void setTranscript(List<Transcript> transcripts) {
		this.transcripts = transcripts;
	}
	////classRoom
	public void addClassroom(Classroom classroom) {
		classRooms.add(classroom);
	}
	public List<Classroom> getClassRooms() {
		return classRooms;
	}
	public void setClassRooms(List<Classroom> classRooms) {
		this.classRooms = classRooms;
	}
	*/
	
}
