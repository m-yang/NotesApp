package com.example.android.notesapp.model;

import java.util.ArrayList;
import java.util.List;

public class DummyNoteData {

    public static List<Note> getNotes() {

        List<Note> notes = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            String name = "Lorem ipsum";
            String note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
            int minutesLeft = i * 7;

            notes.add(new Note(name, note, minutesLeft));
        }
        return notes;
    }
}
