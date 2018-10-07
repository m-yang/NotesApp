package com.example.android.notesapp.presenter;

import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.view.addnote.AddNoteView;

public class AddNotePresenter implements Presenter<AddNoteView>{

    private AddNoteView mView;
    private Note note;

    @Override
    public void attachView(AddNoteView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadNote(Note note) {
        String name = note.getName();
        String content = note.getNote();
        int remain = note.getMinutesLeft();

        mView.populateViews(name, content, remain);
    }
}
