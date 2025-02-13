package com.attendance.mapper;

import com.attendance.dto.AttendanceDTO;
import com.attendance.entity.Attendance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttendanceDTOMapper {

   private final StudentWithoutAttendanceMapper studentWithoutAttendanceMapper;
    private final LessonWithoutAttendanceMapper lessonWithoutAttendanceMapper;

    public AttendanceDTO convert(Attendance attendance){
        if (attendance == null) return null;
        return new AttendanceDTO(
                attendance.getId(),
                studentWithoutAttendanceMapper.convert(attendance.getStudent()),
                lessonWithoutAttendanceMapper.convert(attendance.getLesson()),
                attendance.getOpen());
    }

}
