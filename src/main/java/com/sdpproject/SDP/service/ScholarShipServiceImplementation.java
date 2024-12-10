package com.sdpproject.SDP.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdpproject.SDP.model.Scholarship;
import com.sdpproject.SDP.model.Student;
import com.sdpproject.SDP.repository.SchloarshipRepository;

@Service
public class ScholarShipServiceImplementation implements ScholarShipService{

	@Autowired
	SchloarshipRepository schloarshipRepository;
	
	@Override
	public void postScholarShip(Scholarship scholarship) {
		schloarshipRepository.save(scholarship);
	}

	@Override
	public List<Scholarship> getAllScholarShip() {
		return schloarshipRepository.findAll();
	}
	
	public Scholarship getScholarshipById(Long id) {
        Optional<Scholarship> scholarship = schloarshipRepository.findById(id);
        return scholarship.orElse(null);
    }

    public void updateScholarship(Scholarship scholarship) {
    	schloarshipRepository.save(scholarship); 
    }

    public void deleteScholarship(Long id) {
    	schloarshipRepository.deleteById(id); 
    }

	@Override
	public List<Scholarship> getScholarshipsNotAppliedByStudent(Student student) {
		return schloarshipRepository.findScholarshipsNotAppliedByStudent(student);
				
	}

}
