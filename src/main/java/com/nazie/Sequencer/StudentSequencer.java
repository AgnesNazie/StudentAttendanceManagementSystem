package com.nazie.Sequencer;

public class StudentSequencer {

    private static int studentId = 0;

    public static int nextId() {
        return ++studentId;
    }
}
