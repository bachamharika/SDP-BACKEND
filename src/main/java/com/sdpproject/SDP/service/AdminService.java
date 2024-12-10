package com.sdpproject.SDP.service;

import java.util.List;
import java.util.Optional;

import com.sdpproject.SDP.model.Admin;
import com.sdpproject.SDP.model.Student;

public interface AdminService {
	public Optional<Admin> login(String email, String password);
	public List<Student> getAllStudent();
	public void deleteUser(Long id);
}
