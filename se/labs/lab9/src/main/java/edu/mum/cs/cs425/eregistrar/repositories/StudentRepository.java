package edu.mum.cs.cs425.eregistrar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.cs.cs425.eregistrar.model.Student;


@Transactional

public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String query, String query2);

}
