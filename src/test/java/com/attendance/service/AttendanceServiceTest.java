package com.attendance.service;

import com.attendance.entity.Attendance;
import com.attendance.entity.Lesson;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private Lesson lesson;
    private Attendance attendance1;
    private Attendance attendance2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Attendance> studentAttendances = new ArrayList<>();
        student = new Student(1L, "name", "email", "123456", studentAttendances);
        lesson = new Lesson(1L, "title", LocalDateTime.now(), true);

        attendance1 = new Attendance(1L, student, lesson, true);
        attendance2 = new Attendance(2L, student, lesson, true);
    }

    @Test
    void testGetById() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));

        Attendance foundAttendance = attendanceService.getById(1L);

        assertNotNull(foundAttendance);
        assertEquals(attendance1.getId(), foundAttendance.getId());

        verify(attendanceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        when(attendanceRepository.findAll()).thenReturn(Arrays.asList(attendance1, attendance2));

        List<Attendance> attendances = attendanceService.getAll();

        assertEquals(2, attendances.size());

        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    void testGetByStudent() {
        when(studentService.getById(1L)).thenReturn(student);
        when(attendanceRepository.findByStudent(student)).thenReturn(Arrays.asList(attendance1, attendance2));

        List<Attendance> attendances = attendanceService.getByStudent(1L);

        assertEquals(2, attendances.size());

        verify(studentService, times(1)).getById(1L);
        verify(attendanceRepository, times(1)).findByStudent(student);
    }

    @Test
    void testGetByLesson() {
        when(lessonService.getById(1L)).thenReturn(lesson);
        when(attendanceRepository.findByLesson(lesson)).thenReturn(Arrays.asList(attendance1));

        List<Attendance> attendances = attendanceService.getByLesson(1L);

        assertEquals(1, attendances.size());

        verify(lessonService, times(1)).getById(1L);
        verify(attendanceRepository, times(1)).findByLesson(lesson);
    }

    @Test
    void testCreate() {
        when(studentRepository.getByRa(any())).thenReturn(Arrays.asList(student.getId()));
        when(attendanceRepository.save(any())).thenReturn(attendance1);

        Attendance createdAttendance = attendanceService.create(attendance1);

        assertNotNull(createdAttendance);
        assertEquals(attendance1.getId(), createdAttendance.getId());

        verify(studentRepository, times(1)).getByRa(any());
        verify(attendanceRepository, times(1)).save(any());
    }

    @Test
    void testUpdate() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));
        when(attendanceRepository.save(any())).thenReturn(attendance1);

        Attendance updatedAttendance = new Attendance();
        updatedAttendance.setId(1L);

        Attendance result = attendanceService.update(updatedAttendance, 1L);

        assertNotNull(result);
        assertEquals(updatedAttendance.getId(), result.getId());

        verify(attendanceRepository, times(1)).findById(1L);
        verify(attendanceRepository, times(1)).save(any());
    }

    @Test
    void testDelete() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));

        attendanceService.delete(1L);

        verify(attendanceRepository, times(1)).deleteById(1L);
    }
}
