package com.nazie.model;

import java.time.LocalDate;

public class Attendance {
    /*Attendance.java – Create model class with id, studentId, attendanceDate.
     */
    private int id;
    private int studentId;
    private LocalDate attendanceDate;
    private AttendanceStatus status;

    //constructor
    public Attendance(int id, int studentId, LocalDate attendanceDate, String status) {
        setId(id);
        setStudentId(studentId);
        setAttendanceDate(attendanceDate);
        setStatus(status);
    }

    //getter for id
    public int getId() {
        return id;
    }

    //setter for id
    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("ID cannot be null or negative");
        this.id = id;
    }

    //getter for student id
    public int getStudentId() {
        return studentId;
    }

    //setter for student id
    public void setStudentId(int studentId) {
        if (studentId < 0)
            throw new IllegalArgumentException("Student id cannot be null or negative");
        this.studentId = studentId;
    }

    //getter for attendance date
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    //setter for attendance date
    public void setAttendanceDate(LocalDate attendanceDate) {
        if (attendanceDate == null)
            throw new IllegalArgumentException("Local date can not be null or empty");
        this.attendanceDate = attendanceDate;
    }
    //getter for status

    public String getStatus() {
        return status;
    }
    //setter for attendance status

    public void setStatus(AttendanceStatus status) {
        if (status == null)
            throw new IllegalArgumentException("Status cannot be null");
        this.status = status;
    }

    //tp string


    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", attendanceDate=" + attendanceDate +
                ", status + " + status +
                '}';
    }
}
