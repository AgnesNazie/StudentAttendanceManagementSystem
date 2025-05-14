package com.nazie.DAO;

import com.nazie.model.Student;

import java.util.List;

public interface StudentDAO {
    void saveStudent(Student student);
    Student findStudentById(int id);
    List<Student> getAllStudents();
    void deleteStudent(int id);
}
