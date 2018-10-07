package com.example.android.notesapp.view.addnote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.TextView;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;
import com.example.android.notesapp.presenter.AddNotePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.notesapp.view.notelist.NoteListActivity.NOTE_ID;

public class AddNoteActivity extends AppCompatActivity implements AddNoteView {

    private String TAG = AddNoteActivity.class.getSimpleName();


    @BindView(R.id.note_et)
    public TextView mNoteEditText;

    @BindView(R.id.name_et)
    public TextView mNameEditText;

    @BindView(R.id.reminder_sb)
    public AppCompatSeekBar mReminderSeekbar;


    private AddNotePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ButterKnife.bind(this);

        // attach presenter
        mPresenter = new AddNotePresenter();
        mPresenter.attachView(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Note note = bundle.getParcelable(NOTE_ID);
            mPresenter.loadNote(note);
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
