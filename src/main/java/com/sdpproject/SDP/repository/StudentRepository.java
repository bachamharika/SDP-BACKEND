package com.sdpproject.SDP.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdpproject.SDP.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	Optional<Student> findByEmail(String email);
}
