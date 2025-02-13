package com.attendance.mapper;

import com.attendance.dto.StudentWithoutAttendanceDTO;
import com.attendance.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentWithoutAttendanceMapper {

    public StudentWithoutAttendanceDTO convert(Student student){
        if (student == null) return null;
        return new StudentWithoutAttendanceDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getRa()
        );
    }

}
