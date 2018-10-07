package com.example.android.notesapp.view.notelist;

import android.view.View;

import com.example.android.notesapp.model.Note;

public interface NoteListView {

    void startAddNoteActivity();
    View.OnClickListener addNote();
    void startEditNoteActivity(Note note);
}
