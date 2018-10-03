package com.example.android.notesapp.model;

public class Note {

    String name;

    String note;

    int minutesLeft;

    public Note(String name, String note, int minutesLeft) {
        this.name = name;
        this.note = note;
        this.minutesLeft = minutesLeft;
    }
}
