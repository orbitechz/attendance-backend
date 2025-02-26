package com.attendance.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LessonTest {

    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        lesson = new Lesson();
    }

    @Test
    public void testLessonDefaultConstructor() {
        assertNotNull(lesson);
        assertNull(lesson.getId());
        assertNull(lesson.getTitle());
        assertNull(lesson.getDate());
        assertNull(lesson.getOpen());
        assertNull(lesson.getAttendances());
    }

    @Test
    public void testLessonParameterizedConstructor() {
        LocalDateTime now = LocalDateTime.now();
        lesson = new Lesson(1L, "Math", now, true, null, new Professor());

        assertEquals(1L, lesson.getId());
        assertEquals("Math", lesson.getTitle());
        assertEquals(now, lesson.getDate());
        assertTrue(lesson.getOpen());
    }

    @Test
    public void testSetId() {
        lesson.setId(2L);
        assertEquals(2L, lesson.getId());
    }

    @Test
    public void testSetTitle() {
        lesson.setTitle("Science");
        assertEquals("Science", lesson.getTitle());
    }

    @Test
    public void testSetDate() {
        LocalDateTime date = LocalDateTime.of(2025, 2, 20, 10, 0);
        lesson.setDate(date);
        assertEquals(date, lesson.getDate());
    }

    @Test
    public void testSetOpen() {
        lesson.setOpen(false);
        assertFalse(lesson.getOpen());

        lesson.setOpen(true);
        assertTrue(lesson.getOpen());
    }

    @Test
    public void testSetAttendances() {
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance()); // Adicionando uma instância de Attendance para o teste
        lesson.setAttendances(attendances);
        
        assertNotNull(lesson.getAttendances());
        assertEquals(1, lesson.getAttendances().size());
    }
}
