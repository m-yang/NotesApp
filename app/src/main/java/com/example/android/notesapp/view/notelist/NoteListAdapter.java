package com.example.android.notesapp.view.notelist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.notesapp.R;
import com.example.android.notesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final String TAG = NoteListAdapter.class.getSimpleName();

    private int mNumItems;
    private List<Note> mNotes;
    private ButtonActionListener mButtonActionListener;

    public void deleteNote(int position) {
        this.mNotes.remove(position);
        notifyDataSetChanged();
    }

    public Note getNoteData(int position) {
        return mNotes.get(position);
    }

    public void updateNote(Note note, int position) {
        Log.d(TAG, "remain: " + note.getMinutesLeft());
        mNotes.set(position, note);
        notifyDataSetChanged();
    }

    public void addNote(Note note) {
        mNotes.add(0, note);
        notifyDataSetChanged();
    }


    public interface ButtonActionListener {
        void onDelete(int position);
        void onEdit(int position);
        void onShare(int position);
    }

    public NoteListAdapter(ButtonActionListener context) {
        this.mNotes = new ArrayList<>();
        this.mButtonActionListener = context;
        this.mNumItems = mNotes.size();
    }

    public void updateNotesList(List<Note> notes) {
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
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

        holder.nameTv.setText(note.getName());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        ImageButton deleteBtn;
        ImageButton editBtn;
        ImageButton shareBtn;

        public NoteViewHolder(View v) {
            super(v);

            nameTv = v.findViewById(R.id.tv_name_name);
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

                mButtonActionListener.onDelete(getAdapterPosition());
            }
        }

        class EditClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                mButtonActionListener.onEdit(getAdapterPosition());
            }
        }

        class ShareClickListener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                mButtonActionListener.onShare(getAdapterPosition());
            }
        }

    }
}
