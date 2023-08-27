package edu.mum.cs.cs425.demos.studentrecordsmgmtapp;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model.Student;

public class MyStudentRecordsMgmtApp {
	public List<Student> getListOfPlatinumAlumniStudents(List<Student> studentList) {
		LocalDate now = LocalDate.now();
		Period per30y = Period.ofYears(30);
		List<Student> platList = studentList.stream()
				.filter(s -> Period.between(s.getDateOfAdmission(), now).getYears() >= per30y.getYears())
				.sorted((s1, s2) -> s2.getDateOfAdmission().compareTo(s1.getDateOfAdmission()))
				.collect(Collectors.toList());
		return platList;
	}

	public void printListOfStudents(List<Student> studentList) {
		studentList.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
		studentList.forEach(s -> System.out.println(s));
	}

	public void printHelloWorld(int[] list) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] % 5 == 0)
				System.out.print("Hello");
			if (list[i] % 7 == 0)
				System.out.print("World");
			System.out.print(" ");
		}
		;
		System.out.println();
	}

	public int findSecondBiggest(int[] list) {
		int m1 = Integer.MIN_VALUE, m2 = Integer.MIN_VALUE;
		for (int i = 0; i < list.length; i++) {
			if (list[i] > m1) {
				m2 = m1;
				m1 = list[i];
			} else if (list[i] > m2)
				m2 = list[i];
		}
		return m2;
	}

	public static void main(String[] args) {
		List<Student> studentList = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		studentList.add(new Student("110002", "Anna", LocalDate.parse("12/07/1990", formatter)));
		studentList.add(new Student("110003", "Erica", LocalDate.parse("01/31/1974", formatter)));
		studentList.add(new Student("110001", "Dave", LocalDate.parse("11/18/1951", formatter)));
		studentList.add(new Student("110004", "Carlos", LocalDate.parse("08/22/2009", formatter)));
		studentList.add(new Student("110005", "Bob", LocalDate.parse("03/05/1990", formatter)));

		MyStudentRecordsMgmtApp app = new MyStudentRecordsMgmtApp();
		System.out.println("------- lab06 -------");
		System.out.println("------- Part 3.3 List of student -------");
		app.printListOfStudents(studentList);
		System.out.println();

		System.out.println("------- Part 3.4 List of Platinum Alumni student -------");
		List<Student> platList = app.getListOfPlatinumAlumniStudents(studentList);
		platList.forEach(s -> System.out.println(s));
		System.out.println();

		System.out.println("------- Part 3.5.1 Hello World -------");
		int[] list1 = { 2, 3, 5, 7, 35, 45, 70, 80 };
		for (int i = 0; i < list1.length; i++)
			System.out.print(list1[i] + " ");
		System.out.print("-> print: ");
		app.printHelloWorld(list1);
		System.out.println();

		System.out.println("------- Part 3.5.2 Second Biggest -------");
		int[] list2 = { 4, 8, 7, 6, 5, 20, 10 };
		for (int i = 0; i < list2.length; i++)
			System.out.print(list2[i] + " ");
		System.out.println("-> second biggest: " + app.findSecondBiggest(list2));

		int[] list3 = { 10, 9, 20, 15, 17, 13 };
		for (int i = 0; i < list3.length; i++)
			System.out.print(list3[i] + " ");
		System.out.println("-> second biggest: " + app.findSecondBiggest(list3));

		System.out.println();
	}
}
