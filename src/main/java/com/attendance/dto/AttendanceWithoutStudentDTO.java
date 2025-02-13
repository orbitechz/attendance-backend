package com.attendance.dto;


public record AttendanceWithoutStudentDTO(
        Long id,
        LessonWithoutAttendanceDTO lesson,
        Boolean open
) {}
