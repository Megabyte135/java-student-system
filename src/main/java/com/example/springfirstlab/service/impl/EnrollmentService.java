package com.example.springfirstlab.service.impl;

import com.example.springfirstlab.model.Enrollment;
import com.example.springfirstlab.repository.CourseRepository;
import com.example.springfirstlab.repository.EnrollmentRepository;
import com.example.springfirstlab.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollment(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found for student ID " + studentId + " and course ID " + courseId));
    }

    public void enrollStudent(Long studentId, Long courseId) {
        ensureExist(studentId, courseId);
        Enrollment enrollment = new Enrollment(studentId, courseId);
        enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Enrollment updatedEnrollment) {
        return enrollmentRepository.save(updatedEnrollment);
    }

    public void delistStudent(Long id) {
        enrollmentRepository.deleteById(id);
    }

    private void ensureExist(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Student with ID " + studentId + " not found.");
        }
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Course with ID " + courseId + " not found.");
        }
    }
}