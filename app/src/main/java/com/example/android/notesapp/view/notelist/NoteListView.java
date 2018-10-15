package com.example.android.notesapp.view.notelist;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.notesapp.model.Note;

public interface NoteListView {

    Context getContext();

    void startAddNoteActivity();
    View.OnClickListener addNote();
    void startEditNoteActivity(Note note);

    void startShareIntent(Intent intent);
}
