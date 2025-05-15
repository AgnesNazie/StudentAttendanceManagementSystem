package com.nazie.Sequencer;

public class AttendanceSequencer {
    private static int attendanceId = 0;

    public static int nextId() {
        return ++attendanceId;
    }

}
