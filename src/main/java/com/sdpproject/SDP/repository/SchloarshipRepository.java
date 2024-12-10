package com.sdpproject.SDP.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sdpproject.SDP.model.Scholarship;
import com.sdpproject.SDP.model.Student;

@Repository
public interface SchloarshipRepository extends JpaRepository<Scholarship, Long> {
	@Query("SELECT s FROM Scholarship s WHERE s NOT IN " +
	           "(SELECT a.scholarship FROM Application a WHERE a.student = :student)")
	    List<Scholarship> findScholarshipsNotAppliedByStudent(@Param("student") Student student);

	
}
