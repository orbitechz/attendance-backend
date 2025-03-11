package com.attendance.service;

import com.attendance.entity.Professor;
import com.attendance.repository.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {
    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfessorService professorService;

    private Professor professor1;
    private Professor professor2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        professor1 = new Professor();
        professor1.setId(1L);
        professor1.setName("professor1");
        professor1.setEmail("email");
        professor1.setPassword("password1");

        professor2 = new Professor();
        professor2.setId(2L);
        professor2.setName("professor2");
        professor2.setEmail("email");
        professor2.setPassword("password2");
    }

    @Test
    void testGetById() {
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor1));

        Professor foundProfessor = professorService.getById(1L);

        assertNotNull(foundProfessor);
        assertEquals(professor1.getId(), foundProfessor.getId());

        verify(professorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        when(professorRepository.findAll()).thenReturn(Arrays.asList(professor1, professor2));

        List<Professor> professors = professorService.getAll();

        assertEquals(2, professors.size());

        verify(professorRepository, times(1)).findAll();
    }

    @Test
    void testCreate() {
        String encodedPassword = "encodedPassword1";
        when(passwordEncoder.encode("password1")).thenReturn(encodedPassword);
        when(professorRepository.save(any(Professor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Professor createdProfessor = professorService.create(professor1);

        assertNotNull(createdProfessor);
        assertEquals(encodedPassword, createdProfessor.getPassword(), "A senha não foi codificada corretamente.");

        verify(passwordEncoder, times(1)).encode("password1");
        verify(professorRepository, times(1)).save(professor1);
    }

    @Test
    void testUpdate() {
        Professor updatedProfessor = new Professor();
        updatedProfessor.setId(1L);
        updatedProfessor.setName("professor atualizado1");
        updatedProfessor.setPassword("newPassword");

        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor1));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(professorRepository.save(any(Professor.class))).thenReturn(updatedProfessor);

        Professor result = professorService.update(updatedProfessor, 1L);

        assertNotNull(result);
        assertEquals("professor atualizado1", result.getName());
        assertEquals("encodedNewPassword", result.getPassword());

        verify(professorRepository, times(1)).findById(1L);
        verify(professorRepository, times(1)).save(updatedProfessor);
    }

    @Test
    void testDelete() {
        doNothing().when(professorRepository).deleteById(1L);

        professorService.delete(1L);

        verify(professorRepository, times(1)).deleteById(1L);
    }
}
