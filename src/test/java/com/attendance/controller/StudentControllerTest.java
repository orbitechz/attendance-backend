package com.attendance.controller;

import com.attendance.entity.Student;
import com.attendance.service.StudentService;
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

class StudentControllerTest {

    @Mock
    private StudentService service;

    @InjectMocks
    private StudentController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudent() {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = Arrays.asList(student1, student2);
        when(service.getAll()).thenReturn(students);

        ResponseEntity<List<Student>> response = controller.getAllStudent();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(students, response.getBody());
        verify(service, times(1)).getAll();
    }

    @Test
    void testGetStudent() {
        Long id = 1L;
        Student student = new Student();
        when(service.getById(id)).thenReturn(student);

        ResponseEntity<Student> response = controller.getStudent(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(student, response.getBody());
        verify(service, times(1)).getById(id);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        when(service.create(student)).thenReturn(student);

        ResponseEntity<Student> response = controller.createStudent(student);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(student, response.getBody());
        verify(service, times(1)).create(student);
    }

    @Test
    void testUpdateStudent() {
        Long id = 1L;
        Student student = new Student();
        when(service.update(student, id)).thenReturn(student);

        ResponseEntity<Student> response = controller.updateStudent(id, student);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(student, response.getBody());
        verify(service, times(1)).update(student, id);
    }

    @Test
    void testDeleteStudent() {
        Long id = 1L;
        doNothing().when(service).delete(id);

        ResponseEntity<Student> response = controller.deleteStudent(id);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        verify(service, times(1)).delete(id);
    }

}