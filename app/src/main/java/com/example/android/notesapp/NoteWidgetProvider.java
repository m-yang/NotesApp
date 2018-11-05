package com.example.android.notesapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.notesapp.service.NoteWidgetService;

/**
 * Implementation of App Widget functionality.
 */
public class NoteWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = getRecipeListRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    private static RemoteViews getRecipeListRemoteView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
        Intent intent = new Intent(context, NoteWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, intent);
        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
        return views;
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}


