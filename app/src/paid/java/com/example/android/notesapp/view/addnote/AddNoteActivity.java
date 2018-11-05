package com.example.android.notesapp.view.addnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.TextView;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.presenter.AddNotePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.notesapp.view.notelist.NoteListActivity.ADD_NOTE_ACTION;
import static com.example.android.notesapp.view.notelist.NoteListActivity.ADD_NOTE_CONTENT;
import static com.example.android.notesapp.view.notelist.NoteListActivity.ADD_NOTE_NAME;
import static com.example.android.notesapp.view.notelist.NoteListActivity.ADD_NOTE_REMAIN;
import static com.example.android.notesapp.view.notelist.NoteListActivity.EDIT_NOTE_ACTION;
import static com.example.android.notesapp.view.notelist.NoteListActivity.NOTE_ID;
import static com.example.android.notesapp.view.notelist.NoteListActivity.NOTE_UPDATE_ID;

public class AddNoteActivity extends AppCompatActivity implements AddNoteView {

    private String TAG = AddNoteActivity.class.getSimpleName();


    @BindView(R.id.note_et)
    public TextView mNoteEditText;

    @BindView(R.id.name_et)
    public TextView mNameEditText;

    @BindView(R.id.reminder_sb)
    public AppCompatSeekBar mReminderSeekbar;

    @BindView(R.id.confirm_note_fab)
    public FloatingActionButton mConfirmNoteFab;

    private AddNotePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ButterKnife.bind(this);

        mConfirmNoteFab.setOnClickListener(confirmNote());

        // attach presenter
        mPresenter = new AddNotePresenter();
        mPresenter.attachView(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Note note = bundle.getParcelable(NOTE_ID);
            mPresenter.loadNote(note);
        }
    }

    public View.OnClickListener confirmNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getAction().equals(EDIT_NOTE_ACTION)) {
                    updateNote();
                } else if (getIntent().getAction().equals(ADD_NOTE_ACTION)) {
                    addNote();
                }
            }
        };
    }


    public void addNote() {
        String name = mNameEditText.getText().toString();
        String content = mNoteEditText.getText().toString();
        int remain = mReminderSeekbar.getProgress();

        Intent intent = new Intent();
        intent.putExtra(ADD_NOTE_NAME, name);
        intent.putExtra(ADD_NOTE_CONTENT, content);
        intent.putExtra(ADD_NOTE_REMAIN, remain);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void updateNote() {
        String name = mNameEditText.getText().toString();
        String content = mNoteEditText.getText().toString();
        int remain = mReminderSeekbar.getProgress();
        Intent intent = new Intent();

        if (mPresenter.isNoteChanged(name, content, remain)) {
            Note note = mPresenter.updateNote(name, content, remain);
            intent.putExtra(NOTE_UPDATE_ID, note);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        }
    }

    @Override
    public void populateViews(String name, String content, int remain) {

        remain = remain < 60 ? remain : 60;

        mNameEditText.setText(name);
        mNoteEditText.setText(content);
        mReminderSeekbar.setProgress(remain);

    }
}
