package com.sdpproject.SDP.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdpproject.SDP.model.Student;
import com.sdpproject.SDP.repository.StudentRepository;

@Service
public class StudentServiceImplementation implements StudentService{

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public Student registerStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Optional<Student> login(String email, String password) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent() && student.get().getPassword().equals(password)) {
            return student;
        }
        return Optional.empty();
    }

	@Override
	public Optional<Student> getStudent(Long id) {
		return studentRepository.findById(id);
	}
}
