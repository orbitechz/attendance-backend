package com.attendance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Getter @Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime date;

    private Boolean open;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    public Lesson(Long id, String title, LocalDateTime date, Boolean open) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.open = open;
    }
    public Lesson() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}