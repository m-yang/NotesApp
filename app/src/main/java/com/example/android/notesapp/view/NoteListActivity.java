package com.example.android.notesapp.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }

    private View.OnClickListener addNote() {

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
    public void setupNotesList(List<Note> notes) {
        // set layout
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mNoteList.setLayoutManager(layoutManager);

        // set adapter
        NoteListAdapter adapter = new NoteListAdapter(notes, this);
        mNoteList.setAdapter(adapter);
    }

    @Override
    public void startAddNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNotes() {

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
