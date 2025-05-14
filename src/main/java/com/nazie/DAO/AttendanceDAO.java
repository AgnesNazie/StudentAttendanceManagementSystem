package com.nazie.DAO;

import com.nazie.model.Attendance;

import java.util.List;

public interface AttendanceDAO {
    void markAttendance(Attendance attendance);
    List<Attendance> getAttendanceByStudentId(int studentId);
    void deleteAttendance(int id);
    Attendance findById(int id);
}
