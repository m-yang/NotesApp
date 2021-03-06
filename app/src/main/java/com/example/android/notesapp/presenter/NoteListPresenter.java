package com.example.android.notesapp.presenter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.android.notesapp.NoteWidgetProvider;
import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.service.ReminderService;
import com.example.android.notesapp.view.notelist.NoteListAdapter;
import com.example.android.notesapp.view.notelist.NoteListView;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class NoteListPresenter implements Presenter<NoteListView> {

    private static String TAG = NoteListPresenter.class.getSimpleName();
    private NoteListView mView;
    private NoteListAdapter mAdapter;
    public static String NOTE_NAME_KEY = "NOTE_NAME_KEY";
    public static String NOTES_PREF_KEY = "NOTES_PREF_KEY";

    private static String NOTES_REFERENCE = "notes";

    DatabaseReference mDb;

    String uid;

    final List<Note> notes = new ArrayList<>();

    FirebaseJobDispatcher dispatcher;

    static SharedPreferences sharedpreferences;

    public NoteListPresenter(NoteListAdapter adapter) {
        this.mAdapter = adapter;
        uid = FirebaseAuth.getInstance().getUid();
        this.mDb = FirebaseDatabase.getInstance().getReference(NOTES_REFERENCE).child(uid);
    }


    @Override
    public void attachView(NoteListView view) {
        this.mView = view;
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(mView.getContext()));
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(mView.getContext());
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public void addDbListener() {
        mDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Set<String> noteSet = new HashSet<>();
                notes.clear();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    notes.add(note);
                    noteSet.add(note.getName());
                }

                editor.putStringSet(NOTES_PREF_KEY, noteSet);
                editor.apply();

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mView.getContext());
                int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                        new ComponentName(mView.getContext().getApplicationContext(), NoteWidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);

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


        scheduleJob(remain, id, getBundleExtra(name));

        mDb.child(id).setValue(note);
    }

    public void updateNote(final Note note) {
        mDb.child(note.getId()).setValue(note);

        if (note.getMinutesLeft() == 0) {
            dispatcher.cancel(note.getId());
        } else {
            int window = note.getMinutesLeft();
            String id = note.getId();
            scheduleJob(window, id, getBundleExtra(note.getName()));
        }
    }

    private Bundle getBundleExtra(String noteName) {

        Bundle bundle = new Bundle();
        bundle.putString(NOTE_NAME_KEY, noteName);

        return bundle;
    }

    private void scheduleJob(int window, String id, Bundle bundle) {
        Job job = dispatcher.newJobBuilder()
                .setExtras(bundle)
                .setService(ReminderService.class)
                .setTag(id)
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(window, window))
                .build();

        String message;
        if (window > 0) {
            message = "Will remind you in " + window + " minutes";
            dispatcher.mustSchedule(job);
        } else {
            message = "Will not remind you";
        }
        toast(message);
    }

    private void toast(String message) {
        Toast.makeText(mView.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
