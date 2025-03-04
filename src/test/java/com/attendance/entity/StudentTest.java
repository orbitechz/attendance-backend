package com.attendance.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
  
  @InjectMocks
  private Student student;

  @BeforeEach
  public void setUp(){
    MockitoAnnotations.openMocks(this);
    List<Attendance> attendances = new ArrayList<>();
    student = new Student(1l, "Guilherme Santos", "guilherme.santos@exemple.com", new Date(), attendances
    );
  }

  @Test
  public void testStudentCreation(){
    assertNotNull(student);
    assertEquals(1L, student.getId());
    assertEquals("Guilherme Santos", student.getName());
    assertEquals("guilherme.santos@exemple.com", student.getEmail());
    assertNotNull(student.getAttendances());
    assertEquals(0, student.getAttendances().size());
  }

  @Test
  public void testSetName() {
      student.setName("Jailson Cruz");
      assertEquals("Jailson Cruz", student.getName());
  }

  @Test
  public void testSetEmail() {
      student.setEmail("messias.oliveira@exemple.com");
      assertEquals("messias.oliveira@exemple.com", student.getEmail());
  }

  @Test
  public void testSetRa() {
      student.setRa("654321");
      assertEquals("654321", student.getRa());
  }

  @Test
  public void testSetAttendances() {
      List<Attendance> attendances = new ArrayList<>();
      student.setAttendances(attendances);
      assertEquals(attendances, student.getAttendances());
  }
}
