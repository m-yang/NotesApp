package com.example.android.notesapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;

import java.util.List;

import butterknife.OnClick;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final String TAG = NoteListAdapter.class.getSimpleName();

    private int mNumItems;
    private List<Note> mNotes;
    private ButtonActionListener mButtonActionListener;

    public interface ButtonActionListener {
        void onDelete();
        void onEdit();
        void onShare();
    }


    public NoteListAdapter(List<Note> notes, ButtonActionListener context) {
        this.mButtonActionListener = context;
        this.mNotes = notes;
        this.mNumItems = mNotes.size();
    }

    @NonNull
    @Override
    public NoteListAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.note_card_item, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);


    }

    @Override
    public int getItemCount() {
        return mNumItems;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        ImageButton deleteBtn;
        ImageButton editBtn;
        ImageButton shareBtn;

        public NoteViewHolder(View v) {
            super(v);

            deleteBtn = v.findViewById(R.id.btn_delete);
            editBtn = v.findViewById(R.id.btn_edit);
            shareBtn = v.findViewById(R.id.btn_share);

            deleteBtn.setOnClickListener(new DeleteClickListener());
            editBtn.setOnClickListener(new EditClickListener());
            shareBtn.setOnClickListener(new ShareClickListener());

        }

        class DeleteClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                mButtonActionListener.onDelete();
            }
        }

        class EditClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                mButtonActionListener.onEdit();
            }
        }

        class ShareClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                mButtonActionListener.onShare();
            }
        }

    }
}
