package com.attendance.service;

import com.attendance.entity.Student;
import com.attendance.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public Student create(Student student){

        return student;
    }
}
