package com.attendance.service;

import com.attendance.entity.Lesson;
import com.attendance.entity.Professor;
import com.attendance.entity.User;
import com.attendance.repository.LessonRepository;
import com.attendance.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private ProfessorService professorService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson1;
    private Lesson lesson2;

    private Professor professor;
    private User professorUser;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.of(2023, 10, 5, 10, 30);

        professor = new Professor();
        professor.setId(1L);

        professorUser = new User();
        professorUser.setId(1L);
        professorUser.setUsername("professor");
        professorUser.setRole(User.Role.PROFESSOR);

        lesson1 = new Lesson(1L, "title1", now, true, null, professor);
        lesson2 = new Lesson(2L, "title2", now, true, null, professor);
    }


    @Test
    void testGetById() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson1));

        Lesson foundLesson = lessonService.getById(1L);

        assertNotNull(foundLesson);
        assertEquals(lesson1.getId(), foundLesson.getId());

        verify(lessonRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        when(lessonRepository.findAll()).thenReturn(Arrays.asList(lesson1, lesson2));

        List<Lesson> lessons = lessonService.getAll();

        assertEquals(2, lessons.size());
        assertTrue(lessons.contains(lesson1));
        assertTrue(lessons.contains(lesson2));
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(userRepository.findByUsername("professor")).thenReturn(Optional.of(professorUser));
        when(professorService.getById(professorUser.getId())).thenReturn(professor);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson1);

        when(principal.getName()).thenReturn("professor");

        Lesson createdLesson = lessonService.create(lesson1, principal);

        assertNotNull(createdLesson);
        assertEquals(professor, createdLesson.getProfessor());
        assertEquals(lesson1.getId(), createdLesson.getId());
        verify(userRepository, times(1)).findByUsername("professor");
        verify(professorService, times(1)).getById(professorUser.getId());
        verify(lessonRepository, times(1)).save(lesson1);
    }

    @Test
    void testUpdate() {
        Lesson updatedLesson = new Lesson();
        updatedLesson.setId(1L);
        updatedLesson.setTitle("novo titulo");

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson1));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(updatedLesson);;

        Lesson result = lessonService.update(updatedLesson, 1L);

        assertNotNull(result);
        assertEquals("novo titulo", result.getTitle());
        assertEquals(lesson1.getId(), result.getId());

        verify(lessonRepository, times(1)).findById(1L);
        verify(lessonRepository, times(1)).save(updatedLesson);
    }

    @Test
    void testDelete() {
        doNothing().when(lessonRepository).deleteById(1L);

        lessonService.delete(1L);

        verify(lessonRepository, times(1)).deleteById(1L);
    }

}
