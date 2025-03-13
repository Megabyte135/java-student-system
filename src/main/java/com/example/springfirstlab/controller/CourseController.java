package com.example.springfirstlab.controller;

import com.example.springfirstlab.model.Course;
import com.example.springfirstlab.service.impl.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(courseService.updateCourse(id, updatedCourse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }
}
