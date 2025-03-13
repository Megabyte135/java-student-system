package com.example.springfirstlab.controller;


import com.example.springfirstlab.model.Student;

import com.example.springfirstlab.service.impl.EnrollmentService;
import com.example.springfirstlab.service.impl.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentsController {

    private final StudentService service;
    private final EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<Student> findAllStudent() {
        return service.findAllStudents();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or " +
                  "(hasRole('STUDENT') and @userSecurity.isCurrentUser(#id))")
    public Student readStudent(@PathVariable Long id) {
        return service.readStudent(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER') or " +
                  "(hasRole('STUDENT') and @userSecurity.isCurrentUser(#student.id))")
    public Student updateStudent(@RequestBody Student student) {
        return service.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
    }
}
