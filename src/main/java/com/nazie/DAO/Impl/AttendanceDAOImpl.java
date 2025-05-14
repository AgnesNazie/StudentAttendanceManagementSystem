package com.nazie.DAO.Impl;

import com.nazie.DAO.AttendanceDAO;
import com.nazie.JDBC.DBConnection;
import com.nazie.model.Attendance;

import java.sql.*;
import java.time.LocalDate;
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
        String query = "INSERT INTO attendance (id, studentId, attendanceDate, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, attendance.getId());
            stmt.setInt(2, attendance.getStudentId());
            stmt.setDate(3, Date.valueOf(attendance.getAttendanceDate()));
            stmt.setString(4, attendance.getStatus());
            stmt.executeUpdate();
            System.out.println("Successfully Mark attendance");

        } catch (SQLException e) {
            System.out.println("Error in marking attendance");
            e.printStackTrace();
        }

    }

    @Override
    public List<Attendance> getAttendanceByStudentId(int studentId) {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM attendances";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                attendances.add(new Attendance(
                        rs.getInt("id"),
                        rs.getInt("studentId"),
                        rs.getDate("attendanceDate").toLocalDate(),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println(" Error retrieving Attendance by student id");
            e.printStackTrace();
        }
        return attendances;
    }

    @Override
    public void deleteAttendance(int id) {
        String query = "DELETE FROM attendance WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Successfully deleted Attendance");

        } catch (SQLException e) {
            System.out.println("Error deleting Attendance");
            e.printStackTrace();

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
                        rs.getInt("studentId"),
                        rs.getDate("AttendanceDate").toLocalDate(),
                        rs.getString("Status"));
                System.out.println("Successfully  found marked attendance");
            }
        } catch (SQLException e) {
            System.out.println("Error finding Attendance by id");
            e.printStackTrace();

        }

        return attendance;
    }
}
