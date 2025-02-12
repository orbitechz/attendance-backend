package com.attendance.controller;

import com.attendance.entity.Lesson;
import com.attendance.repository.LessonRepository;
import com.attendance.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    private LessonService service;

    @Autowired
    private LessonRepository repository;

    @GetMapping()
    public ResponseEntity<List<Lesson>> getLessons() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id).orElse(null));
    }

    @PostMapping()
    public ResponseEntity<Lesson> createLesson(@Validated @RequestBody Lesson lesson) {
        return ResponseEntity.ok(service.create(lesson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@Validated @RequestBody Lesson lesson,@PathVariable Long id) {
        return ResponseEntity.ok(service.update(lesson, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
