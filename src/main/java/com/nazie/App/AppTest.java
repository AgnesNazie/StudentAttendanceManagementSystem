package com.nazie.App;

import com.nazie.DAO.AttendanceDAO;
import com.nazie.DAO.Impl.AttendanceDAOImpl;
import com.nazie.DAO.Impl.StudentDAOImpl;
import com.nazie.DAO.StudentDAO;
import com.nazie.model.Attendance;
import com.nazie.model.AttendanceStatus;
import com.nazie.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.nazie.model.AttendanceStatus.PRESENT;

public class AppTest {
    public static void main(String[] args) {
        // Create DAO objects
        StudentDAO studentDAO = new StudentDAOImpl();
        AttendanceDAO attendanceDAO = new AttendanceDAOImpl();

        // Add a new student
        Student newStudent = new Student("Agnes", "Java");
        studentDAO.saveStudent(newStudent);
        int studentId = newStudent.getId();

        System.out.println("Student saved:");
        System.out.println("ID: " + newStudent.getId() +
                ", Name: " + newStudent.getName() +
                ", Course: " + newStudent.getCourse());
        System.out.println("-----------------------------");

        // Add student attendance
        Attendance newAttendance = new Attendance(0, studentId, LocalDate.now(), PRESENT.name());
        attendanceDAO.markAttendance(newAttendance);

        System.out.println("Attendance marked for Student ID: " + studentId);
        System.out.println("-----------------------------");

        // Find student by ID
        Student foundStudent = studentDAO.findStudentById(studentId);
        System.out.println("Found Student:");
        System.out.println("ID: " + foundStudent.getId() +
                ", Name: " + foundStudent.getName() +
                ", Course: " + foundStudent.getCourse());
        System.out.println("-----------------------------");

        // Get all student records
        List<Student> students = studentDAO.getAllStudents();
        System.out.println("All Students:");
        for (Student s : students) {
            System.out.println("ID: " + s.getId() +
                    ", Name: " + s.getName() +
                    ", Course: " + s.getCourse());
        }
        System.out.println("-----------------------------");

        // Get attendance by student ID
        List<Attendance> attendanceList = attendanceDAO.getAttendanceByStudentId(studentId);
        System.out.println("All Attendance Records for Student ID " + studentId + ":");
        for (Attendance a : attendanceList) {
            System.out.println("ID: " + a.getId() +
                    ", Date: " + a.getAttendanceDate() +
                    ", Status: " + a.getStatus());
        }
        System.out.println("-----------------------------");

        // Get a specific attendance record by ID (e.g., first one from list)
        if (!attendanceList.isEmpty()) {
            Attendance foundAttendance = attendanceDAO.findById(attendanceList.get(0).getId());
            System.out.println("Found Attendance:");
            System.out.println("ID: " + foundAttendance.getId() +
                    ", Student ID: " + foundAttendance.getStudentId() +
                    ", Date: " + foundAttendance.getAttendanceDate() +
                    ", Status: " + foundAttendance.getStatus());
            System.out.println("-----------------------------");

            // Delete attendance
            attendanceDAO.deleteAttendance(foundAttendance.getId());
            System.out.println("Deleted Attendance with ID: " + foundAttendance.getId());
        }

        // Delete student
        studentDAO.deleteStudent(studentId);
        System.out.println("Deleted Student with ID: " + studentId);
        System.out.println("-----------------------------");
    }
}
