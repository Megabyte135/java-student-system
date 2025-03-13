package com.example.springfirstlab.repository;

import com.example.springfirstlab.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
