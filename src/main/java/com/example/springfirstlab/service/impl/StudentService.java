package com.example.springfirstlab.service.impl;

import com.example.springfirstlab.model.Student;

import com.example.springfirstlab.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAllStudents() {
        log.info("Fetching all students...");
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        if (student.getId() != null) {
            throw new IllegalArgumentException("ID must be null when creating a new student");
        }
        log.info("Creating student: {}", student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        if (student.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }

        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalArgumentException("Student with ID " + student.getId() + " not found");
        }

        log.info("Updating student: {}", student);
        return studentRepository.save(student);
    }

    public Student readStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Student with ID {} not found", id);
                    return new IllegalArgumentException("Student with ID " + id + " not found");
                });
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
        log.info("Deleting student with ID {}", id);
        studentRepository.deleteById(id);
    }
}



