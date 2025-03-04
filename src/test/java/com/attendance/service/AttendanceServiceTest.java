package com.attendance.service;

import com.attendance.entity.Attendance;
import com.attendance.entity.Lesson;
import com.attendance.entity.Professor;
import com.attendance.entity.Student;
import com.attendance.repository.AttendanceRepository;
import com.attendance.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private AttendanceService attendanceService;

    private Student student;
    private Attendance attendance1;
    private Attendance attendance2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Attendance> studentAttendances = new ArrayList<>();
        student = new Student(1L, "nome", "email", new Date(), studentAttendances);
        Lesson lesson = new Lesson(1L, "title", LocalDateTime.now(), true, null, new Professor());

        attendance1 = new Attendance(1L, student, lesson, true);
        attendance2 = new Attendance(2L, student, lesson, true);
    }

    @Test
    void mustReturnPresenceList() {
        when(attendanceRepository.findAll()).thenReturn(Arrays.asList(attendance1, attendance2));

        List<Attendance> result = attendanceService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    void mustReturnIdWhenAttendanceExist() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));

        Attendance result = attendanceService.getById(1L);

        assertNotNull(result);
        assertEquals(attendance1, result);
        verify(attendanceRepository, times(1)).findById(1L);
    }

    @Test
    void mustReturnNullWhenAttendanceDoesntExist() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.empty());

        Attendance result = attendanceService.getById(1L);

        assertNull(result);
        verify(attendanceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByStudent() {
        when(studentService.getById(1L)).thenReturn(student);

        when(attendanceRepository.findByStudent(student)).thenReturn(Arrays.asList(attendance1, attendance2));

        List<Attendance> attendancesList = attendanceService.getByStudent(1L);
        assertNotNull(attendancesList);
        assertEquals(2, attendancesList.size());
        assertEquals(attendance1, attendancesList.get(0));
        assertEquals(attendance2, attendancesList.get(1));

        verify(studentService).getById(1L);
        verify(attendanceRepository).findByStudent(student);
    }
}
