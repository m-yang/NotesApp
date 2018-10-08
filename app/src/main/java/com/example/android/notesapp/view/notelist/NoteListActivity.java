package com.example.android.notesapp.view.notelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.presenter.NoteListPresenter;
import com.example.android.notesapp.view.addnote.AddNoteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteListActivity extends AppCompatActivity implements NoteListView, NoteListAdapter.ButtonActionListener {

    private String TAG = NoteListActivity.class.getSimpleName();
    public static String NOTE_ID = "NOTE_ID";
    public static String POSITION_ID = "POSITION_ID";
    public static int EDIT_NOTE_REQUEST_CODE = 1;
    public static String NOTE_UPDATE_ID = "NOTE_UPDATE_ID";
    public static String POSITION_UPDATE_ID = "POSITION_UPDATE_ID";

    @BindView(R.id.noteslist_rv)
    public RecyclerView mNoteList;

    @BindView(R.id.add_note_fab)
    public FloatingActionButton mAddNoteFab;

    private NoteListPresenter mPresenter;

    private static int NUM_COLUMNS = 2;


    /* lifecycle events */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);

        ButterKnife.bind(this);

        mAddNoteFab.setOnClickListener(addNote());

        // setup recycler view
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mNoteList.setLayoutManager(layoutManager);

        NoteListAdapter adapter = new NoteListAdapter(this);
        mNoteList.setAdapter(adapter);

        // setup presenter
        mPresenter = new NoteListPresenter(adapter);
        mPresenter.attachView(this);

        mPresenter.loadNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_NOTE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Note note = data.getParcelableExtra(NOTE_UPDATE_ID);
                int position = data.getIntExtra(POSITION_UPDATE_ID, -1);

                Log.d(TAG, "remain: " + note.getMinutesLeft());
                Log.d(TAG, "position: " + position);
                mPresenter.updateNote(note, position);
            }
            if (resultCode == RESULT_CANCELED) {
                // do nothing
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /* view interface implementation */
    @Override
    public void startAddNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void startEditNoteActivity(Note note, int position) {
        Intent intent = new Intent(this, AddNoteActivity.class);

        intent.putExtra(NOTE_ID, note);
        intent.putExtra(POSITION_ID, position);

        startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);

    }

    @Override
    public View.OnClickListener addNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddNoteActivity();
                Log.d(TAG, "add button clicked");
            }
        };
    }

    /* button listener implementation */
    @Override
    public void onDelete(int position) {
        Log.d(TAG, "delete called: " + position);
        mPresenter.deleteNote(position);
    }

    @Override
    public void onEdit(int position) {
        Log.d(TAG, "edit called: " + position);
        mPresenter.editNote(position);
    }

    @Override
    public void onShare(int position) {
        Log.d(TAG, "share called: " + position);
        mPresenter.shareNote(position);
    }
}
