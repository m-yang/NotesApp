package com.example.android.notesapp.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.android.notesapp.model.DummyNoteData;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.view.notelist.NoteListAdapter;
import com.example.android.notesapp.view.notelist.NoteListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoteListPresenter implements Presenter<NoteListView> {

    private static String TAG = NoteListPresenter.class.getSimpleName();
    private NoteListView mView;
    private NoteListAdapter mAdapter;

    private static String NOTES_REFERENCE = "notes";

    DatabaseReference mDb;

    public NoteListPresenter(NoteListAdapter adapter) {
        this.mAdapter = adapter;
        this.mDb = FirebaseDatabase.getInstance().getReference(NOTES_REFERENCE);
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
        final List<Note> notes = new ArrayList<>();

        mDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);

                    Log.d(TAG, note.getName());
                    notes.add(note);
                }
                mAdapter.updateNotesList(notes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Error: " + databaseError.getMessage());
            }
        });

    }

    public void deleteNote(int position) {
        mAdapter.deleteNote(position);
    }


    public void editNote(int position) {
        Note note = mAdapter.getNoteData(position);
        mView.startEditNoteActivity(note, position);
    }

    public void shareNote(int position) {

        String note = mAdapter.getNoteData(position).getNote();

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);

        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, note);

        mView.startShareIntent(shareIntent);

    }

    public void addNote(Note note) {
        mAdapter.addNote(note);

        String id = mDb.push().getKey();

        mDb.child(id).setValue(note, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d(TAG, "Error: " + databaseError.getMessage());
                } else {
                    Log.d(TAG, "Save success");
                }
            }
        });

    }

    public void updateNote(Note note, int position) {
        mAdapter.updateNote(note, position);

    }
}
