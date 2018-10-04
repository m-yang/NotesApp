package com.example.android.notesapp.model;

public class Note {

    private String name;

    private String note;

    private int minutesLeft;

    public Note(String name, String note, int minutesLeft) {
        this.name = name;
        this.note = note;
        this.minutesLeft = minutesLeft;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
}
