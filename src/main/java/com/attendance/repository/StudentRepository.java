package com.attendance.repository;

import com.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.id FROM Student s WHERE s.ra = :ra")
    List<Long> getByRa(@Param("ra") String ra);
}
