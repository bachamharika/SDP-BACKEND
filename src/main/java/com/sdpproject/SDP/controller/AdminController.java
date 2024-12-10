package com.sdpproject.SDP.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sdpproject.SDP.model.Admin;
import com.sdpproject.SDP.model.Application;
import com.sdpproject.SDP.model.Scholarship;
import com.sdpproject.SDP.model.Student;
import com.sdpproject.SDP.service.AdminService;
import com.sdpproject.SDP.service.ApplicationService;
import com.sdpproject.SDP.service.ScholarShipService;

@RestController
@RequestMapping("/admin") 
@CrossOrigin(origins = "http://localhost:3000") 
public class AdminController {
	
	@Autowired
    private AdminService adminService;
	
	@Autowired 
	ScholarShipService scholarShipService;
	
	@Autowired
	private ApplicationService applicationService;
	
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<Admin> admin = adminService.login(email, password);
        if (admin.isPresent()) {
            return ResponseEntity.ok("Login successful! Welcome " + admin.get().getEmail());
        }
        return ResponseEntity.status(401).body("Invalid email or password.");
    }
	
	@GetMapping("/manageUsers")
	public ResponseEntity<List<Student>> getAllUsers()
	{
		return ResponseEntity.ok(adminService.getAllStudent());
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> DeleteUser(@PathVariable Long id)
	{
		adminService.deleteUser(id);
		return ResponseEntity.ok("Deleted Succesfully!!!");
	}
	
	
	@PostMapping("/postScholarShip")
	public ResponseEntity<String> postScholarship(@RequestBody Scholarship scholarship)
	{
		scholarship.setPostedOn(LocalDate.now());
		scholarShipService.postScholarShip(scholarship);
		return ResponseEntity.ok("posted Succesfully!!!");
	}
	
	
	@GetMapping("/manageScholarship")
	public ResponseEntity<List<Scholarship>> getAllScholarship()
	{
		return ResponseEntity.ok(scholarShipService.getAllScholarShip());
	}
	
	@PutMapping("/updateScholarship/{id}")
    public ResponseEntity<String> updateScholarship(@PathVariable Long id, @RequestBody Scholarship scholarship) {
        try {
            // Check if the scholarship exists
            Scholarship existingScholarship = scholarShipService.getScholarshipById(id);
            if (existingScholarship == null) {
                return ResponseEntity.status(404).body("Scholarship not found.");
            }

            // Update the scholarship details
            existingScholarship.setName(scholarship.getName());
            existingScholarship.setDescription(scholarship.getDescription());
            existingScholarship.setDeadline(scholarship.getDeadline());
            existingScholarship.setAmount(scholarship.getAmount());

            scholarShipService.updateScholarship(existingScholarship);

            return ResponseEntity.ok("Scholarship updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating scholarship: " + e.getMessage());
        }
    }
	@DeleteMapping("/deleteScholarship/{id}")
    public ResponseEntity<String> deleteScholarship(@PathVariable Long id) {
        try {
            // Check if the scholarship exists
            Scholarship scholarship = scholarShipService.getScholarshipById(id);
            if (scholarship == null) {
                return ResponseEntity.status(404).body("Scholarship not found.");
            }

            // Delete the scholarship
            scholarShipService.deleteScholarship(id);

            return ResponseEntity.ok("Scholarship deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting scholarship: " + e.getMessage());
        }
    }
	
	@GetMapping("/{scholarshipId}/applications")
    public List<Application> getApplications(@PathVariable Long scholarshipId) {
        return applicationService.getApplicationsByScholarship(scholarshipId);
    }

    // Get a specific application by ID
    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<Application> getApplication(@PathVariable Long applicationId) {
        Optional<Application> application = applicationService.getApplicationById(applicationId);
        return application.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Approve or Reject an application
    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {
        boolean updated = applicationService.updateApplicationStatus(applicationId, status);
        if (updated) {
            return ResponseEntity.ok("Application status updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update application status.");
        }
    }
    
    @GetMapping("/{applicationId}/pdf")
    public ResponseEntity<byte[]> getPdf(@PathVariable Long applicationId) {
    	Optional<Application> application = applicationService.getApplicationById(applicationId);
    	if(application.isPresent())
    	{
    		byte[] pdfData = application.get().getPdfFile();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfData);
    	}
		return null;
        
    }
}
