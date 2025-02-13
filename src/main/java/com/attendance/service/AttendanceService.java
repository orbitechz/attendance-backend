package com.attendance.service;

import com.attendance.dto.AttendanceDTO;
import com.attendance.entity.Attendance;
import com.attendance.entity.Lesson;
import com.attendance.entity.Student;
import com.attendance.mapper.AttendanceDTOMapper;
import com.attendance.repository.AttendanceRepository;
import com.attendance.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    private final LessonService lessonService;

    private final AttendanceDTOMapper attendanceDTOMapper;

    public AttendanceDTO convertAttendanceDTO(Attendance attendance){
        if (attendance == null) return null;
        return attendanceDTOMapper.convert(attendance);
    }

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
            Optional<Long> studentId = studentRepository.getByRa(attendance.getStudent().getRa());
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
