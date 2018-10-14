package com.example.android.notesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String id;

    private String name;

    private String note;

    private int minutesLeft;

    public Note(){
        // empty constructor
    }

    public Note(String id, String name, String note, int minutesLeft) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.minutesLeft = minutesLeft;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.note);
        dest.writeInt(this.minutesLeft);
    }

    protected Note(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.note = in.readString();
        this.minutesLeft = in.readInt();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
