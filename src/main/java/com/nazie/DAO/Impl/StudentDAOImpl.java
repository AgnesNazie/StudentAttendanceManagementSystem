package com.nazie.DAO.Impl;

import com.nazie.DAO.StudentDAO;
import com.nazie.JDBC.DBConnection;
import com.nazie.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private Connection connection;

    public StudentDAOImpl() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Connected to database successfully");
            e.printStackTrace();
        }
    }

    @Override
    public void saveStudent(Student student) {
        String query = "INSERT INTO student (id, name, course) VALUES(?,?,?) ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getCourse());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error saving student");
            e.printStackTrace();
        }
        System.out.println("Successfully save student");

    }

    @Override
    public Student findStudentById(int id) {
        String query = "SELECT * FROM student WHERE id = ?";
        Student student = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                student = new Student(rs.getInt("id"), rs.getString("name"), rs.getString("course"));
            }
        } catch (SQLException e) {
            System.out.println("Successfully find student ");
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM student ";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("course")));
            }

        } catch (SQLException e) {
            System.out.println("Successfully Retrieve all student");
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurred");
            e.printStackTrace();
        }
        System.out.println("Successfully Deleted student");
    }
}
