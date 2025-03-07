package com.attendance.controller;

import com.attendance.entity.Professor;
import com.attendance.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
public class ProfessorController {


    @Autowired
    private ProfessorService service;

    @GetMapping
    public List<Professor> getAllProfessors() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Professor getProfessorById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Professor createProfessor(@RequestBody Professor professor) {
        return service.create(professor);
    }

    @PutMapping("/{id}")
    public Professor updateProfessor(@RequestBody Professor professor, @PathVariable Long id) {
        return service.update(professor, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProfessor(@PathVariable Long id) {
        service.delete(id);
    }

}
