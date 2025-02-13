package com.attendance.dto;

public record LessonWithoutAttendanceDTO(
        Long id,
        String title,
        String date,
        Boolean open
) {}
