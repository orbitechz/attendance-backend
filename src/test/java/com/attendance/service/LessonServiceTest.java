package com.attendance.service;

import com.attendance.entity.Lesson;
import com.attendance.repository.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    void setUp() {
        lesson1 = new Lesson(1L, "title1", LocalDateTime.now(), true);
        lesson2 = new Lesson(2L, "title2", LocalDateTime.now(), true);
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

        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(lessonRepository.save(any())).thenReturn(lesson1);

        Lesson createdLesson = lessonService.create(lesson1);

        assertNotNull(createdLesson);
        assertEquals(lesson1.getId(), createdLesson.getId());

        verify(lessonRepository, times(1)).save(any());
    }

    @Test
    void testUpdate() {
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson1));
        when(lessonRepository.save(any())).thenReturn(lesson1);

        Lesson updatedLesson = new Lesson();
        updatedLesson.setId(1L);
        updatedLesson.setTitle("Updated title1");

        Lesson result = lessonService.update(updatedLesson, 1L);

        assertNotNull(result);
        assertEquals(updatedLesson.getId(), result.getId());

        verify(lessonRepository, times(1)).findById(1L);
        verify(lessonRepository, times(1)).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(lessonRepository).deleteById(1L);

        lessonService.delete(1L);

        verify(lessonRepository, times(1)).deleteById(1L);
    }
}
