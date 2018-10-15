package com.example.android.notesapp.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class ReminderService extends JobService {

    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = ReminderService.this;
                Log.d("TAG", "onStartJob");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                Log.d("TAG", "onStartJob- OnPost");
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask != null) {
            mBackgroundTask.cancel(true);
        }
        /* true means, we're not done, please reschedule */
        return true;
    }
}
