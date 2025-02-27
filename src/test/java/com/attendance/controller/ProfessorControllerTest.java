package com.attendance.controller;

import com.attendance.entity.Professor;
import com.attendance.service.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfessorControllerTest {

    @Mock
    private ProfessorService service;

    @InjectMocks
    private ProfessorController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProfessors() {
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();
        List<Professor> professors = Arrays.asList(professor1, professor2);
        when(service.getAll()).thenReturn(professors);

        List<Professor> result = controller.getAllProfessors();

        assertEquals(professors, result);
        verify(service, times(1)).getAll();
    }

    @Test
    void testGetProfessorById() {
        Long id = 1L;
        Professor professor = new Professor();
        when(service.getById(id)).thenReturn(professor);

        Professor result = controller.getProfessorById(id);

        assertEquals(professor, result);
        verify(service, times(1)).getById(id);
    }

    @Test
    void testCreateProfessor() {
        Professor professor = new Professor();
        when(service.create(professor)).thenReturn(professor);

        Professor result = controller.createProfessor(professor);

        assertEquals(professor, result);
        verify(service, times(1)).create(professor);
    }

    @Test
    void testUpdateProfessor() {
        Long id = 1L;
        Professor professor = new Professor();
        when(service.update(professor, id)).thenReturn(professor);

        Professor result = controller.updateProfessor(professor, id);

        assertEquals(professor, result);
        verify(service, times(1)).update(professor, id);
    }

    @Test
    void testDeleteProfessor() {
        Long id = 1L;
        doNothing().when(service).delete(id);

        controller.deleteProfessor(id);

        verify(service, times(1)).delete(id);
    }

}