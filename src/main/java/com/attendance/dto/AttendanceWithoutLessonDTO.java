package com.attendance.dto;

public record AttendanceWithoutLessonDTO(
        Long id,
        StudentWithoutAttendanceDTO student,
        Boolean open
) {}
