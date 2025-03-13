package com.example.springfirstlab.controller;


import com.example.springfirstlab.model.Student;

import com.example.springfirstlab.service.impl.EnrollmentService;
import com.example.springfirstlab.service.impl.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //автоматически сериализует объекты в JSON/XML.
@RequestMapping("/api/v1/students")//маппинг запросов на метод (GET, POST и т. д.)
@AllArgsConstructor
public class StudentsController {

    private final StudentService service;
    private final EnrollmentService enrollmentService;

    @GetMapping
    public List<Student> findAllStudent() {
        return service.findAllStudents();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student readStudent(@PathVariable Long id) {
        return service.readStudent(id);
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student){
        return service.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        service.deleteStudent(id);
    }
}
