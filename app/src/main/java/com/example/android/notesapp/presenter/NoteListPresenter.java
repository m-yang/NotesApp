package com.example.android.notesapp.presenter;

import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.view.NoteListView;

public class NoteListPresenter implements Presenter<NoteListView> {

    private NoteListView mView;
    private Note mModel;


    @Override
    public void attachView(NoteListView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadNotes() {

        // query firebase

        // create adapter

        // update view
    }

    public void deleteNote() {

    }


}
