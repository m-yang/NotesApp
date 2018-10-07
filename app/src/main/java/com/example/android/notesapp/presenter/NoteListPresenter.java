package com.example.android.notesapp.presenter;

import com.example.android.notesapp.model.DummyNoteData;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.view.NoteListAdapter;
import com.example.android.notesapp.view.NoteListView;

import java.util.List;

public class NoteListPresenter implements Presenter<NoteListView> {

    private NoteListView mView;
    private Note mModel;
    private NoteListAdapter mAdapter;

    public NoteListPresenter(NoteListAdapter adapter) {
        this.mAdapter = adapter;
    }


    @Override
    public void attachView(NoteListView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void loadNotes() {
        List<Note> notes = DummyNoteData.getNotes();

        mAdapter.updateNotesList(notes);
    }

    public void deleteNote(int position) {
        mAdapter.deleteNote(position);
    }


    public void editNote() {
        //TODO
    }

    public void shareNote() {
        // TODO
    }
}
