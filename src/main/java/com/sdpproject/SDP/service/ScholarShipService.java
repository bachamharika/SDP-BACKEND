package com.sdpproject.SDP.service;

import java.util.List;

import com.sdpproject.SDP.model.Scholarship;
import com.sdpproject.SDP.model.Student;

public interface ScholarShipService {
	public void postScholarShip(Scholarship scholarship);
	public List<Scholarship> getAllScholarShip();
	public Scholarship getScholarshipById(Long id);
	public void updateScholarship(Scholarship scholarship);
	public void deleteScholarship(Long id) ;
	public List<Scholarship> getScholarshipsNotAppliedByStudent(Student student);
}
