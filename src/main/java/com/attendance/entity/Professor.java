package com.attendance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends User {

    private String name;

    private String email;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<Lesson> lessonList;
}