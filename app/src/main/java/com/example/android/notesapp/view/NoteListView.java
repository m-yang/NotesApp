package com.example.android.notesapp.view;

import android.view.View;

import com.example.android.notesapp.model.Note;

import java.util.List;

public interface NoteListView {

    void showNotes();
    void setupNotesList(List<Note> notes);
    void startAddNoteActivity();
    View.OnClickListener addNote();

}
