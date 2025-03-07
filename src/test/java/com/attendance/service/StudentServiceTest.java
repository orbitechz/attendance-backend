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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentService studentService;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Attendance> studentAttendances = new ArrayList<>();

        Date dataNascimento = new Date();

        student1 = new Student(1L, "name1", "email", dataNascimento, studentAttendances);
        student2 = new Student(2L, "name2", "email", dataNascimento, studentAttendances);
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
    void create() {
        student1.setPassword("password");
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Student createdStudent = studentService.create(student1);

        assertNotNull(createdStudent);
        assertEquals(encodedPassword, createdStudent.getPassword(), "A senha não foi codificada corretamente.");

        verify(passwordEncoder, times(1)).encode("password");
        verify(studentRepository, times(1)).save(student1);
    }

    @Test
    void testUpdate() {
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("novo name1");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        Student result = studentService.update(updatedStudent, 1L);

        assertNotNull(result);
        assertEquals("novo name1", result.getName());
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
