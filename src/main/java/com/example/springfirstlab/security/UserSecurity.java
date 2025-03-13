package com.example.springfirstlab.security;

import com.example.springfirstlab.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    private final StudentRepository studentRepository;

    public UserSecurity(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean isCurrentUser(Long studentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return studentRepository.findById(studentId)
                .map(student -> student.getUser().getUsername().equals(username))
                .orElse(false);
    }
} 