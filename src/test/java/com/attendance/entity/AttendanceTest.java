package com.attendance.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AttendanceTest {

    private Attendance attendance;
    private Student studentMock;
    private Lesson lessonMock;

    @BeforeEach
    public void setUp() {
        studentMock = mock(Student.class);
        lessonMock = mock(Lesson.class);
        attendance = new Attendance();
    }

    @Test
    public void testAttendanceDefaultConstructor() {
        assertNotNull(attendance);
        assertNull(attendance.getStudent());
        assertNull(attendance.getLesson());
        assertNull(attendance.getOpen());
    }

    @Test
    public void testAttendanceParameterizedConstructor() {
        attendance = new Attendance(1L, studentMock, lessonMock, true);
        
        assertEquals(1L, attendance.getId());
        assertEquals(studentMock, attendance.getStudent());
        assertEquals(lessonMock, attendance.getLesson());
        assertTrue(attendance.getOpen());
    }

    @Test
    public void testSetId() {
        attendance.setId(2L);
        assertEquals(2L, attendance.getId());
    }

    @Test
    public void testSetStudent() {
        attendance.setStudent(studentMock);
        assertEquals(studentMock, attendance.getStudent());
    }

    @Test
    public void testSetLesson() {
        attendance.setLesson(lessonMock);
        assertEquals(lessonMock, attendance.getLesson());
    }

    @Test
    public void testSetOpen() { 
        attendance.setOpen(false);
        assertFalse(attendance.getOpen());
        
        attendance.setOpen(true);
        assertTrue(attendance.getOpen());
    }
}