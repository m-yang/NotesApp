package com.example.android.notesapp.view.addnote;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.presenter.AddNotePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.notesapp.view.notelist.NoteListActivity.NOTE_ID;
import static com.example.android.notesapp.view.notelist.NoteListActivity.NOTE_UPDATE_ID;
import static com.example.android.notesapp.view.notelist.NoteListActivity.POSITION_ID;
import static com.example.android.notesapp.view.notelist.NoteListActivity.POSITION_UPDATE_ID;

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

    private int position;

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
            position = bundle.getInt(POSITION_ID);
            mPresenter.loadNote(note);
        }
    }

    public View.OnClickListener confirmNote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "confirm button clicked");
                updateNote();
            }
        };
    }

    public void updateNote() {
        String name = mNameEditText.getText().toString();
        String content = mNoteEditText.getText().toString();
        int remain = mReminderSeekbar.getProgress();

        Note note = new Note(name, content, remain);

        Intent intent = new Intent();
        intent.putExtra(NOTE_UPDATE_ID, note);
        intent.putExtra(POSITION_UPDATE_ID, position);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void populateViews(String name, String content, int remain) {

        remain = remain < 60 ? remain : 60;

        mNameEditText.setText(name);
        mNoteEditText.setText(content);
        mReminderSeekbar.setProgress(remain);

    }
}
