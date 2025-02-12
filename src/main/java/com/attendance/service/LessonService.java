package com.attendance.service;

import com.attendance.entity.Lesson;
import com.attendance.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@Service
public class LessonService {

    @Autowired
    private LessonRepository repository;

    public Lesson create(Lesson lesson) {
        return repository.save(lesson);
    }

    public Lesson update(Lesson lesson, Long id) {
        Lesson lessonsaved = repository.findById(id).orElse(null);
        Assert.notNull(lessonsaved, "Lesson not found");
        Assert.isTrue(!Objects.equals(lesson.getId(), id), "Lesson id mismatch");
        return repository.save(lesson);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
