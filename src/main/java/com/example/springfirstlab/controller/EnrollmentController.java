package com.example.springfirstlab.controller;


import com.example.springfirstlab.model.Enrollment;
import com.example.springfirstlab.model.Student;

import com.example.springfirstlab.service.impl.EnrollmentService;
import com.example.springfirstlab.service.impl.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@AllArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or " +
                  "(hasRole('STUDENT') and @userSecurity.isCurrentUser(#studentId))")
    public List<Enrollment> getStudentEnrollments(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudentId(studentId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        enrollmentService.enrollStudent(studentId, courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student enrolled successfully.");
    }

    @PutMapping
    public ResponseEntity<Enrollment> updateEnrollment(@RequestBody Enrollment updatedEnrollment) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(updatedEnrollment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> delistStudent(@PathVariable Long id) {
        enrollmentService.delistStudent(id);
        return ResponseEntity.ok("Student delisted successfully.");
    }
}