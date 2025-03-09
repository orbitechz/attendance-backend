package com.attendance.controller;

import com.attendance.entity.Lesson;
import com.attendance.repository.LessonRepository;
import com.attendance.service.LessonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LessonControllerTest {

    @Mock
    private LessonService service;

    @Mock
    private LessonRepository repository;

    @Mock
    private Principal principal;

    @InjectMocks
    private LessonController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLessons() {
        Lesson lesson1 = new Lesson();
        Lesson lesson2 = new Lesson();
        List<Lesson> lessons = Arrays.asList(lesson1, lesson2);
        when(service.getAll()).thenReturn(lessons);

        ResponseEntity<List<Lesson>> response = controller.getLessons();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(lessons, response.getBody());
        verify(service, times(1)).getAll();
    }

    @Test
    void testGetOpenLessons() {
        Lesson lesson1 = new Lesson();
        lesson1.setOpen(true);
        Lesson lesson2 = new Lesson();
        lesson2.setOpen(true);
        List<Lesson> openLessons = Arrays.asList(lesson1, lesson2);
        when(repository.findByOpenTrue()).thenReturn(openLessons);

        ResponseEntity<List<Lesson>> response = controller.getOpenLessons();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(openLessons, response.getBody());
        verify(repository, times(1)).findByOpenTrue();
    }

    @Test
    void testGetLesson() {
        Long id = 1L;
        Lesson lesson = new Lesson();
        when(service.getById(id)).thenReturn(lesson);

        ResponseEntity<Lesson> response = controller.getLesson(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(lesson, response.getBody());
        verify(service, times(1)).getById(id);
    }

    @Test
    void testCreateLesson() {
        Lesson lesson = new Lesson();
        when(service.create(lesson, principal)).thenReturn(lesson);

        ResponseEntity<Lesson> response = controller.createLesson(principal, lesson);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(lesson, response.getBody());
        verify(service, times(1)).create(lesson, principal);
    }

    @Test
    void testUpdateLesson() {
        Long id = 1L;
        Lesson lesson = new Lesson();
        when(service.update(lesson, id, principal)).thenReturn(lesson);

        ResponseEntity<Lesson> response = controller.updateLesson(principal, lesson, id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(lesson, response.getBody());
        verify(service, times(1)).update(lesson, id, principal);
    }

    @Test
    void testCloseLesson() {
        Long id = 1L;
        Lesson lesson = new Lesson();
        lesson.setOpen(true);
        when(repository.findById(id)).thenReturn(Optional.of(lesson));
        when(repository.save(lesson)).thenReturn(lesson);

        ResponseEntity<Lesson> response = controller.closeLesson(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(lesson);
    }

    @Test
    void testCloseLessonNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Lesson> response = controller.closeLesson(id);

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode().value());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteLesson() {
        Long id = 1L;
        doNothing().when(service).delete(id);

        ResponseEntity<Lesson> response = controller.deleteLesson(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(service, times(1)).delete(id);
    }

}