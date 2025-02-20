package com.attendance.service;

import com.attendance.entity.Attendance;
import com.attendance.entity.Student;
import com.attendance.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Attendance> studentAttendances = new ArrayList<>();

        student1 = new Student(1L, "name1", "email", "12345", studentAttendances);
        student2 = new Student(2L, "name2", "email", "54321", studentAttendances);
    }

    @Test
    void testGetById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        Student foundStudent = studentService.getById(1L);

        assertNotNull(foundStudent);
        assertEquals(student1.getId(), foundStudent.getId());

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studentService.getAll();

        assertEquals(2, students.size());

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        Student createdStudent = studentService.create(student1);

        assertNotNull(createdStudent);
        assertEquals(student1.getId(), createdStudent.getId());

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdate() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Updated name1");

        Student result = studentService.update(updatedStudent, 1L);

        assertNotNull(result);
        assertEquals(updatedStudent.getId(), result.getId());

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDelete() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.delete(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
