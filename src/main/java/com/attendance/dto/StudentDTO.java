package com.attendance.dto;

import java.util.List;

public record StudentDTO(
        Long id,
        String name,
        String email,
        String ra,
        List<AttendanceWithoutStudentDTO> attendances
) {}
