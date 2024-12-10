package com.sdpproject.SDP.service;

import java.util.List;
import java.util.Optional;

import com.sdpproject.SDP.model.Application;
import com.sdpproject.SDP.model.Student;

public interface ApplicationService {
	public void save(Application application);
	List<Application> findAllStudentApplication(Student student);
	public List<Application> getApplicationsByScholarship(Long scholarshipId);
	public Optional<Application> getApplicationById(Long applicationId) ;
	 public boolean updateApplicationStatus(Long applicationId, String status);
}
