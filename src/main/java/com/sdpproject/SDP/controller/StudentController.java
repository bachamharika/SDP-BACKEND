package com.sdpproject.SDP.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sdpproject.SDP.model.Application;
import com.sdpproject.SDP.model.Scholarship;
import com.sdpproject.SDP.model.Student;
import com.sdpproject.SDP.service.ApplicationService;
import com.sdpproject.SDP.service.ScholarShipService;
import com.sdpproject.SDP.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScholarShipService scholarShipService;

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/register")
    public ResponseEntity<Student> registerByStudent(@RequestBody Student student) {
        Student savedStudent = studentService.registerStudent(student);
        return ResponseEntity.ok(savedStudent);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<Student> student = studentService.login(email, password);
        if (student.isPresent()) {
            session.setAttribute("student", student.get());
            return ResponseEntity.ok("Login successful! Welcome " + student.get().getFirstName());
        }
        return ResponseEntity.status(401).body("Invalid email or password.");
    }

    @PostMapping("/submitApplication")
    public ResponseEntity<String> submitApplication(
            @RequestParam("scholarshipId") Long scholarshipId,
            @RequestPart("pdfFile") MultipartFile file,
            HttpSession session) {

        Student loggedInStudent = (Student) session.getAttribute("student");
        if (loggedInStudent == null) {
            return ResponseEntity.status(403).body("You must be logged in to submit an application.");
        }

        if (file.getSize() > 2 * 1024 * 1024 || !file.getContentType().equals("application/pdf")) {
            return ResponseEntity.status(400).body("File must be a PDF and less than 2MB.");
        }

        try {
            Application application = new Application();
            application.setStudent(loggedInStudent);
            application.setScholarship(scholarShipService.getScholarshipById(scholarshipId));
            application.setPdfFile(file.getBytes());
            applicationService.save(application);

            return ResponseEntity.ok("Application submitted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing application: " + e.getMessage());
        }
    }

    @GetMapping("/notAppliedScholarships")
    public ResponseEntity<List<Scholarship>> getNotAppliedScholarships(HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("student");
        if (loggedInStudent == null) {
            return ResponseEntity.status(403).body(null);
        }
        List<Scholarship> scholarships = scholarShipService.getScholarshipsNotAppliedByStudent(loggedInStudent);
        return ResponseEntity.ok(scholarships);
    }
    
    @GetMapping("/AppliedScholarships")
    public ResponseEntity<List<Application>> getAppliedScholarships(HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("student");
        if (loggedInStudent == null) {
            return ResponseEntity.status(403).body(null);
        }
        List<Application> applications = applicationService.findAllStudentApplication(loggedInStudent);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/getProfile")
    public ResponseEntity<Student> getProfile(HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("student");
        if (loggedInStudent == null) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(loggedInStudent);
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String contactNumber,
            @RequestParam String address,
            @RequestParam(required = false) MultipartFile profileImage,
            HttpSession session) {

        Student loggedInStudent = (Student) session.getAttribute("student");
        if (loggedInStudent == null) {
            return ResponseEntity.status(403).body("User not logged in.");
        }

        try {
            loggedInStudent.setFirstName(firstName);
            loggedInStudent.setLastName(lastName);
            loggedInStudent.setContactNumber(contactNumber);
            loggedInStudent.setAddress(address);

            if (profileImage != null && !profileImage.isEmpty()) {
                loggedInStudent.setProfileImage(profileImage.getBytes());
            }

            studentService.registerStudent(loggedInStudent);
            session.setAttribute("student", loggedInStudent);

            return ResponseEntity.ok("Profile updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating profile: " + e.getMessage());
        }
    }
}
