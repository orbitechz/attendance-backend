package com.attendance.controller;

import com.attendance.entity.Student;
import com.attendance.repository.StudentRepository;
import com.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudent() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Validated @RequestBody Student student) {
        return ResponseEntity.ok(service.create(student));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Validated @RequestBody Student student) {
        return ResponseEntity.ok(service.update(student, id));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
