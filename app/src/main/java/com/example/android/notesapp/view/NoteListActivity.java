package com.example.android.notesapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.presenter.NoteListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteListActivity extends AppCompatActivity implements NoteListView, NoteListAdapter.ButtonActionListener {

    @BindView(R.id.noteslist_rv)
    private RecyclerView mNoteList;

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
