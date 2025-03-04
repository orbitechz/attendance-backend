package com.attendance.service;

import com.attendance.entity.Professor;
import com.attendance.repository.ProfessorRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Professor> getAll() {
        return repository.findAll();
    }

    public Professor getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Professor create(Professor professor) {
        professor.setPassword(passwordEncoder.encode(professor.getPassword()));

        return repository.save(professor);
    }

    public Professor update(Professor professor, Long id) {
        Professor professorSaved = repository.findById(id).orElse(null);
        Assert.isTrue(Objects.equals(professor.getId(), id), "Professor id mismatch");
        Assert.notNull(professorSaved, "Professor not found");
        if (passwordEncoder.matches(professor.getPassword(), professorSaved.getPassword())) {
            professor.setPassword(professorSaved.getPassword());
        }else{
            professor.setPassword(passwordEncoder.encode(professor.getPassword()));
        }
        repository.save(professor);

        return professor;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
