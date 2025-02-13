package com.attendance.mapper;

import com.attendance.dto.LessonWithoutAttendanceDTO;
import com.attendance.entity.Lesson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonWithoutAttendanceMapper {

    public LessonWithoutAttendanceDTO convert(Lesson lesson){
        if (lesson == null) return null;
        return new LessonWithoutAttendanceDTO(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getDate().toString(),
                lesson.getOpen());
    }

}
