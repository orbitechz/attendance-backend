package com.attendance.dto;

public record StudentWithoutAttendanceDTO(
        Long id,
        String name,
        String email,
        String ra
) {}
