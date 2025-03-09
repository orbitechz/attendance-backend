package com.attendance.controller;

import com.attendance.entity.Lesson;
import com.attendance.repository.LessonRepository;
import com.attendance.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    @Autowired
    private LessonService service;

    @Autowired
    private LessonRepository repository;

    @GetMapping()
    public ResponseEntity< List<Lesson>> getLessons() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/open")
    public ResponseEntity<List<Lesson>> getOpenLessons() {
        List<Lesson> openLessons = repository.findByOpenTrue();
        return ResponseEntity.ok(openLessons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping()
    public ResponseEntity<Lesson> createLesson(Principal principal, @Validated @RequestBody Lesson lesson) {
        return ResponseEntity.ok(service.create(lesson, principal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(Principal principal, @Validated @RequestBody Lesson lesson,@PathVariable Long id) {
        return ResponseEntity.ok(service.update(lesson, id, principal));
    }

    @PutMapping("/close/{id}")
    public ResponseEntity<Lesson> closeLesson(@PathVariable Long id) {
        Lesson lesson = repository.findById(id).orElse(null);
        if (lesson == null) {
            return ResponseEntity.notFound().build();
        }
        lesson.setOpen(false);
        repository.save(lesson);
        return ResponseEntity.ok(lesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Lesson> deleteLesson(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
