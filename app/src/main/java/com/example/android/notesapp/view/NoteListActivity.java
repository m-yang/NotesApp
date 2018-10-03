package com.example.android.notesapp.view;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteListActivity extends AppCompatActivity implements NoteListView, NoteListAdapter.ButtonActionListener {

    private String TAG = NoteListActivity.class.getSimpleName();

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

        // setup presenter
        mPresenter = new NoteListPresenter();
        mPresenter.attachView(this);

        mAddNoteFab.setOnClickListener(addNote());

        // setup recycler view
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        mNoteList.setLayoutManager(layoutManager);

        NoteListAdapter adapter = new NoteListAdapter(this);
        mNoteList.setAdapter(adapter);

        mPresenter.loadNotes();
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
    public void showNotes(List<Note> notes) {
        NoteListAdapter adapter = (NoteListAdapter) mNoteList.getAdapter();
        adapter.updateNotesList(notes);
    }

    /* button listener implementation */
    @Override
    public void onDelete() {

    }

    @Override
    public void onEdit() {

    }

    @Override
    public void onShare() {

    }
}
