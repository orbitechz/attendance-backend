package com.attendance.dto;

public record AttendanceDTO(
        Long id,
        StudentWithoutAttendanceDTO student,
        LessonWithoutAttendanceDTO lesson,
        Boolean open
) {}
