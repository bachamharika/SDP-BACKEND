package com.sdpproject.SDP.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdpproject.SDP.model.Admin;
import com.sdpproject.SDP.model.Student;
import com.sdpproject.SDP.repository.AdminRepository;
import com.sdpproject.SDP.repository.StudentRepository;

@Service
public class AdminServiceImplementation implements AdminService{

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public Optional<Admin> login(String email, String password) {
		Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return admin;
        }
        return Optional.empty();
	}

	@Override
	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}

	@Override
	public void deleteUser(Long id) {
		studentRepository.deleteById(id);
	}

}
