package com.example.android.notesapp.presenter;

import android.util.Log;

import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.view.addnote.AddNoteView;

public class AddNotePresenter implements Presenter<AddNoteView> {

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
        this.note = note;

        String name = note.getName();
        String content = note.getNote();
        int remain = note.getMinutesLeft();

        mView.populateViews(name, content, remain);
    }

    public Note updateNote(String name, String content, int remain) {
        this.note.setName(name);
        this.note.setNote(content);
        this.note.setMinutesLeft(remain);

        return this.note;
    }

    public boolean isNoteChanged(String name, String content, int remain) {
        if (note != null) {
            boolean identical = name.equals(note.getName())
                    && content.equals(note.getNote())
                    && remain == note.getMinutesLeft();
            return !identical;
        } else {
            return false;
        }
    }
}
