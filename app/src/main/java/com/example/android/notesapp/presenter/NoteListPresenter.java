package com.example.android.notesapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.service.ReminderService;
import com.example.android.notesapp.view.notelist.NoteListAdapter;
import com.example.android.notesapp.view.notelist.NoteListView;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
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

    final List<Note> notes = new ArrayList<>();

    FirebaseJobDispatcher dispatcher;

    Context mContext;

    public NoteListPresenter(NoteListAdapter adapter) {
        this.mAdapter = adapter;
        this.mDb = FirebaseDatabase.getInstance().getReference(NOTES_REFERENCE);
    }


    @Override
    public void attachView(NoteListView view) {
        this.mView = view;
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(mView.getContext()));
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void addDbListener() {
        mDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notes.clear();
                for(DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    notes.add(note);
                }
                mAdapter.updateNotesList(notes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //not implemented
            }
        });
    }

    public void deleteNote(int position) {
        Note note = mAdapter.getNoteData(position);
        mDb.child(note.getId()).removeValue();
        dispatcher.cancel(note.getId());
    }


    public void editNote(int position) {
        Note note = mAdapter.getNoteData(position);
        mView.startEditNoteActivity(note);
    }

    public void shareNote(int position) {
        String note = mAdapter.getNoteData(position).getNote();

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);

        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, note);

        mView.startShareIntent(shareIntent);
    }

    public void addNote(String name, String content, int remain) {
        String id = mDb.push().getKey();
        final Note note = new Note(id, name, content, remain);

        int window = remain;

        scheduleJob(window, id);

        mDb.child(id).setValue(note);
    }

    public void updateNote(final Note note) {
        mDb.child(note.getId()).setValue(note);

        if(note.getMinutesLeft() == 0) {
            dispatcher.cancel(note.getId());
        } else {
            int window = note.getMinutesLeft();
            String id = note.getId();
            scheduleJob(window, id);
        }
    }

    private void scheduleJob(int window, String id) {
        Job job = dispatcher.newJobBuilder()
                .setService(ReminderService.class)
                .setTag(id)
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(window, window))
                .build();

        if(window > 0) {
            dispatcher.mustSchedule(job);
        }

    }
}
