package com.attendance.service;

import com.attendance.entity.Lesson;
import com.attendance.entity.Professor;
import com.attendance.entity.User;
import com.attendance.repository.LessonRepository;
import com.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
public class LessonService {

    @Autowired
    private LessonRepository repository;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private UserRepository userRepository;

    public Lesson getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Lesson> getAll() {
        return repository.findAll();
    }

    public Lesson create(Lesson lesson, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElse(null);

        Assert.isTrue(user != null, "User not found");

        Assert.isTrue(user.getRole() == User.Role.PROFESSOR, "User is not professor");

        Professor professor = professorService.getById(user.getId());

        lesson.setProfessor(professor);

        return repository.save(lesson);
    }

    public Lesson update(Lesson lesson, Long id, Principal principal) {
        Lesson foundLesson = repository.findById(id).orElse(null);
        Assert.notNull(foundLesson, "Lesson not found");
        Assert.isTrue(Objects.equals(lesson.getId(), id), "Lesson id mismatch");


        User user = userRepository.findByUsername(principal.getName()).orElse(null);

        Assert.isTrue(user != null, "User not found");

        Assert.isTrue(user.getRole() == User.Role.PROFESSOR, "User is not professor");

        Professor professor = professorService.getById(user.getId());

        lesson.setProfessor(professor);
        return repository.save(lesson);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
