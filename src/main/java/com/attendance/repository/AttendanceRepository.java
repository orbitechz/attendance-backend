package com.attendance.repository;

import com.attendance.entity.Attendance;
import com.attendance.entity.Lesson;
import com.attendance.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudent(Student student);

    List<Attendance> findByLesson(Lesson lesson);
}
