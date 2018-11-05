package com.example.android.notesapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.android.notesapp.R;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import static com.example.android.notesapp.presenter.NoteListPresenter.NOTE_NAME_KEY;

public class ReminderService extends JobService {

    AsyncTask mBackgroundTask;

    private static String CHANNEL_NAME = "REMINDER_CHANNEL";
    private static String CHANNEL_ID = "CHANNEL_ID_01";
    private static String CHANNEL_DESCRIPTION = "REMINDER";
    private static int NOTIFICATION_ID = 1;
    private static String NOTIFICATION_NAME = "Reminder App";


    @Override
    public boolean onStartJob(final JobParameters job) {

        final String noteName = job.getExtras().getString(NOTE_NAME_KEY);

        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = ReminderService.this;
                pushNotification(noteName);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };

        mBackgroundTask.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) {
            mBackgroundTask.cancel(true);
        }
        return true;
    }

    private void pushNotification(String noteName) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_24dp)
                .setContentTitle(NOTIFICATION_NAME)
                .setContentText(noteName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
