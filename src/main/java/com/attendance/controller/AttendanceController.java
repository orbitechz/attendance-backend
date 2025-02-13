package com.attendance.controller;

import com.attendance.entity.Attendance;
import com.attendance.repository.AttendanceRepository;
import com.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService service;

    @Autowired
    private AttendanceRepository repository;

    @GetMapping
    public ResponseEntity<List<Attendance>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Attendance>> findByStudentId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByStudend(id));
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<List<Attendance>> findByLessonId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByLesson(id));
    }


    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(service.create(attendance));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        return ResponseEntity.ok(service.update(attendance, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Attendance> deleteAttendance(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
