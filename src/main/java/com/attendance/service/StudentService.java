package com.attendance.service;

import com.attendance.entity.Student;
import com.attendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Student getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student create(Student student){
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        return repository.save(student); }

    public Student update(Student student, Long id){
        Student foundStudent = repository.findById(id).orElse(null);
        Assert.notNull(foundStudent, "Student not found");
        Assert.isTrue(Objects.equals(student.getId(), id), "Student id not found");
        repository.save(student);
        return student;
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

}
