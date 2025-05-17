package com.nazie.DAO.Impl;

import com.nazie.DAO.AttendanceDAO;
import com.nazie.JDBC.DBConnection;
import com.nazie.model.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    private Connection connection;

    public AttendanceDAOImpl() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Connected to database successfully");
            e.printStackTrace();
        }
    }

    @Override
    public void markAttendance(Attendance attendance) {
        String query = "INSERT INTO attendance (id, student_Id, attendance_date, status) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);


            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, attendance.getId());
                stmt.setInt(2, attendance.getStudentId());
                stmt.setDate(3, Date.valueOf(attendance.getAttendanceDate()));
                stmt.setString(4, attendance.getStatus().name());
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    connection.commit();
                    System.out.println("Successfully Mark attendance");
                }

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error in marking Attendance");
            }
        } catch (SQLException e) {

            throw new RuntimeException("Error in marking attendance");

        }

    }

    @Override
    public List<Attendance> getAttendanceByStudentId(int studentId) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE student_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attendances.add(new Attendance(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getDate("attendance_date").toLocalDate(),
                        rs.getString("status")
                ));
            }

        } catch ( SQLException e) {
            System.out.println("Failed to retrieve Attendance with student id  " + studentId);
            throw new RuntimeException("Error retrieving Attendance");

        }
        return attendances;
    }

    @Override
    public void deleteAttendance(int id) {
        String query = "DELETE FROM attendance WHERE id = ?";
        try {
            connection.setAutoCommit(false);
            if (findById(id) == null) {
                throw new RuntimeException("Attendance not found");

            }
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    connection.commit();
                    System.out.println("Successfully deleted Attendance");

                } else {
                    connection.rollback();
                }

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error deleting Attendance");
                e.printStackTrace();

            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while deleting Attendance");
        }
    }

    @Override
    public Attendance findById(int id) {
        String query = "SELECT * FROM attendance WHERE id = ?";
        Attendance attendance = null;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                attendance = new Attendance(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getDate("attendance_date").toLocalDate(),
                        rs.getString("status"));
                System.out.println("Successfully  found marked attendance");
            }
        } catch (SQLException e) {
            System.out.println("Error finding Attendance by id");
            throw new RuntimeException("Failed to find with id" + id, e);

        }

        return attendance;
    }
}
