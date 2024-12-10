package com.sdpproject.SDP.service;

import java.util.Optional;

import com.sdpproject.SDP.model.Student;

public interface StudentService {
	Student registerStudent(Student student);
	public Optional<Student> login(String email, String password);
	Optional<Student> getStudent(Long id);
}
