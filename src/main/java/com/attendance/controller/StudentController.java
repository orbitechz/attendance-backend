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
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentService service;

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudent() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping()
    public ResponseEntity<Student> createStudent(@Validated @RequestBody Student student) {
        return ResponseEntity.ok(service.create(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Validated @RequestBody Student student) {
        return ResponseEntity.ok(service.update(student, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
