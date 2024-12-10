package com.sdpproject.SDP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdpproject.SDP.model.Application;
import com.sdpproject.SDP.model.Student;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findByStudent(Student student);
	 List<Application> findByScholarshipId(Long scholarshipId);
}
