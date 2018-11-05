package com.example.android.notesapp.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.notesapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.android.notesapp.presenter.NoteListPresenter.NOTES_PREF_KEY;


public class NoteWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NoteRemoteViewsFactory(this.getApplicationContext());
    }
}

class NoteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<String> noteDataSet;

    public NoteRemoteViewsFactory(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> notes = prefs.getStringSet(NOTES_PREF_KEY, null);
        if(notes != null) {
            noteDataSet = new ArrayList<>(notes);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(noteDataSet == null) {
            return 0;
        } else {
            return noteDataSet.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(noteDataSet == null) {
            return null;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.note_widget_provider);
        views.setTextViewText(R.id.note_item_tv,noteDataSet.get(i));

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
