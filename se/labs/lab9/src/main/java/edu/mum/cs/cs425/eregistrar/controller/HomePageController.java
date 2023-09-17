package edu.mum.cs.cs425.eregistrar.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mum.cs.cs425.eregistrar.model.Student;
import edu.mum.cs.cs425.eregistrar.repositories.StudentRepository;

@Controller
@RequestMapping("/eregistrar")
public class HomePageController {
    @Autowired
    private StudentRepository studentRepository;
	
	@GetMapping(value= {"/",  "/home"})
	public  String displayHomePage() {
		return "home/index";
	}
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "student/list"; // Thymeleaf template name for listing students
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/register"; // Thymeleaf template name for registration form
    }
    
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute("student") Student student) {
        // Validate and save the new student data to the database
        studentRepository.save(student);
        System.out.println(student);
        //return "/home/index";
        return "redirect:/eregistrar/students";
    }

    @GetMapping("/edit/{studentId}")
    public String showEditForm(@PathVariable Long studentId, Model model) {
    	System.out.println("Student ID: " + studentId);
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
        	System.out.println("FOUND EDIT Student ID: " + studentId);

            model.addAttribute("student", student.get());
            return "student/edit"; // edit.html
        } else {
        	System.out.println("NOT FOUND Student ID: " + studentId);

            return "redirect:/eregistrar/students"; // Handle not found case
        }
    }

    @PostMapping("/edit/{studentId}")
    public String editStudent(@PathVariable Long studentId, @ModelAttribute("student") Student updatedStudent) {
        // Validate and update the student data in the database
    	System.out.println("Student ID: " + studentId);
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            // Update fields as needed
        	System.out.println("Student is not null(EDIT): " + studentId);
            //student.setStudentNumber(updatedStudent.getStudentNumber());
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            studentRepository.save(student);
        }
        return "home/index";
        //return "redirect:/eregistrar/students";

    }
    
    @PostMapping("/edit")
    public String editStudent( @ModelAttribute("student") Student updatedStudent) {
        // Validate and update the student data in the database
    	System.out.println("Student ID: " + updatedStudent.getStudentId());
        Student student = studentRepository.findById(   updatedStudent.getStudentId()  ).orElse(null);
        if (student != null) {
            // Update fields as needed
        	System.out.println("Student is not null(EDIT): " + updatedStudent.getStudentId());
            //student.setStudentNumber(updatedStudent.getStudentNumber());
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            // Update other fields
            studentRepository.save(student);
        }
        //return "home/index";
        return "redirect:/eregistrar/students";

    }

    @GetMapping("/delete/{studentId}")
    public String showDeleteConfirmation(@PathVariable Long studentId, Model model) {
    	System.out.println("Delete Student ID: " + studentId);
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            System.out.println("Delete FOUND Student ID: " + studentId);
            studentRepository.deleteById(studentId);
            //return "student/delete"; // 
            return "home/index";
        } else {
            return "redirect:/eregistrar/students"; // Handle not found case
        }
    }

    @PostMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {
    	System.out.println("Post Mapping delelte Student ID: " + studentId);
        studentRepository.deleteById(studentId);
        //return "redirect:/eregistrar/students";
        return "home/index";
    }

    @GetMapping("/student/search")
    public String searchStudents(@RequestParam(name = "query", required = false) String query, Model model) {
    	
    	return "student/search";
    }
    @GetMapping("/search")
    public String showSearch(@RequestParam(name = "query", required = false) String query, Model model) {
    	
        List<Student> students;
        if (query != null && !query.isEmpty()) {
            students = studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
        } else {
            students = studentRepository.findAll();
        }
        model.addAttribute("students", students);
        return "student/list"; 
    }
}
