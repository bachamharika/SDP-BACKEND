package com.sdpproject.SDP.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdpproject.SDP.model.Application;
import com.sdpproject.SDP.model.Student;
import com.sdpproject.SDP.repository.ApplicationRepository;

@Service
public class ApplicationServiceImplementation implements ApplicationService {
	@Autowired
    private ApplicationRepository applicationRepository;

    public void save(Application application) {
        applicationRepository.save(application);
    }

	@Override
	public List<Application> findAllStudentApplication(Student student) {
		return applicationRepository.findByStudent(student);
	}
	
	public List<Application> getApplicationsByScholarship(Long scholarshipId) 
	{
	      return applicationRepository.findByScholarshipId(scholarshipId);
	}
	public Optional<Application> getApplicationById(Long applicationId) 
	{
       return applicationRepository.findById(applicationId);
    }
	public boolean updateApplicationStatus(Long applicationId, String status) 
	{
	  Optional<Application> application = applicationRepository.findById(applicationId);
	    if (application.isPresent())
	    {
	       Application app = application.get();
	       app.setStatus(status);
	       applicationRepository.save(app);
	       return true;
	    }
	    return false;
	 }	
}
