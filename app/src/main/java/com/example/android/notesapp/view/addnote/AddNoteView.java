package com.example.android.notesapp.view.addnote;

import android.view.View;

public interface AddNoteView {

    void populateViews(String name, String content, int remain);
    View.OnClickListener confirmNote();
    void updateNote();
}
