package com.attendance.controller;

import com.attendance.entity.Attendance;
import com.attendance.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AttendanceControllerTest {

    @Mock
    private AttendanceService service;

    @InjectMocks
    private AttendanceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Attendance attendance1 = new Attendance();
        Attendance attendance2 = new Attendance();
        List<Attendance> attendances = Arrays.asList(attendance1, attendance2);
        when(service.getAll()).thenReturn(attendances);

        ResponseEntity<List<Attendance>> response = controller.findAll();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(attendances, response.getBody());
        verify(service, times(1)).getAll();
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Attendance attendance = new Attendance();
        when(service.getById(id)).thenReturn(attendance);

        ResponseEntity<Attendance> response = controller.findById(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(attendance, response.getBody());
        verify(service, times(1)).getById(id);
    }

    @Test
    void testFindByStudentId() {
        Long studentId = 1L;
        Attendance attendance1 = new Attendance();
        Attendance attendance2 = new Attendance();
        List<Attendance> attendances = Arrays.asList(attendance1, attendance2);
        when(service.getByStudent(studentId)).thenReturn(attendances);

        ResponseEntity<List<Attendance>> response = controller.findByStudentId(studentId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(attendances, response.getBody());
        verify(service, times(1)).getByStudent(studentId);
    }

    @Test
    void testFindByLessonId() {
        Long lessonId = 1L;
        Attendance attendance1 = new Attendance();
        Attendance attendance2 = new Attendance();
        List<Attendance> attendances = Arrays.asList(attendance1, attendance2);
        when(service.getByLesson(lessonId)).thenReturn(attendances);

        ResponseEntity<List<Attendance>> response = controller.findByLessonId(lessonId);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(attendances, response.getBody());
        verify(service, times(1)).getByLesson(lessonId);
    }

    @Test
    void testCreateAttendance() {
        Attendance attendance = new Attendance();
        when(service.create(attendance)).thenReturn(attendance);

        ResponseEntity<Attendance> response = controller.createAttendance(attendance);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(attendance, response.getBody());
        verify(service, times(1)).create(attendance);
    }

    @Test
    void testUpdateAttendance() {
        Long id = 1L;
        Attendance attendance = new Attendance();
        when(service.update(attendance, id)).thenReturn(attendance);

        ResponseEntity<Attendance> response = controller.updateAttendance(id, attendance);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(attendance, response.getBody());
        verify(service, times(1)).update(attendance, id);
    }

    @Test
    void testDeleteAttendance() {
        Long id = 1L;
        doNothing().when(service).delete(id);

        ResponseEntity<Attendance> response = controller.deleteAttendance(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(service, times(1)).delete(id);
    }

}