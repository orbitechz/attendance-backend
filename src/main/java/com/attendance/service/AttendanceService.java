package com.attendance.service;

import com.attendance.entity.Attendance;
import com.attendance.entity.Lesson;
import com.attendance.entity.Student;
import com.attendance.repository.AttendanceRepository;
import com.attendance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LessonService lessonService;

    public List<Attendance> getAll() {
        return repository.findAll();
    }

    public Attendance getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Attendance> getByStudend(Long id) {
        Student student = studentService.getById(id);
        return repository.findByStudent(student);
    }

    public List<Attendance> getByLesson(Long id){
        Lesson lesson = lessonService.getById(id);
        return repository.findByLesson(lesson);

    }

    public Attendance create(Attendance attendance) {
        if (attendance.getStudent() != null && attendance.getStudent().getRa() != null) {
            Optional<Long> studentId = studentRepository.getByRa(attendance.getStudent().getRa()).stream().findFirst();
            studentId.ifPresent(id -> attendance.getStudent().setId(id));
        }
        return repository.save(attendance);
    }
    public Attendance update(Attendance attendance, Long id) {
        Attendance attendanceSaved = repository.findById(id).orElse(null);
        Assert.isTrue(!Objects.equals(attendance.getId(), id), "Attedance id mismatch");
        Assert.notNull(attendanceSaved, "Attendance not found");
        return repository.save(attendance);
    }

    public void delete(Long id) {
        Attendance attendanceSaved = repository.findById(id).orElse(null);
        Assert.notNull(attendanceSaved, "Attendance not found");
        repository.deleteById(id);
    }

}
