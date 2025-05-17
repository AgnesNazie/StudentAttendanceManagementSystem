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
        String sql = "INSERT INTO students (name, course) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getCourse());
            int rows = stmt.executeUpdate();


            if (rows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        student.setId(generatedId);  // Set ID in object
                        System.out.println(" Successfully saved student with ID: " + generatedId);
                    }
                }
            } else {
                System.out.println(" No rows inserted.");
            }

        } catch (SQLException e) {
            System.out.println(" Error saving student: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public Student findStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
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
        String query = "SELECT * FROM students ";
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
        String query = "DELETE FROM students WHERE id = ?";
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
