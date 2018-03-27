package com.senla.asynctask.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.senla.asynctask.App;
import com.senla.asynctask.states.ThreadState;
import com.senla.asynctask.tasks.TaskFour;
import com.senla.asynctask.tasks.TaskThree;
import com.senla.asynctask.tasks.TaskTwo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskFragment extends Fragment {

    public interface TaskCallbacks {
        void flushMessages(List<String> messages);
    }

    private TaskCallbacks mTaskCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTaskCallbacks = (TaskCallbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void start() {
        ExecutorService service = Executors.newFixedThreadPool(4);
        new TaskOneFragment().executeOnExecutor(service);
        new TaskTwo().executeOnExecutor(service);
        new TaskThree().executeOnExecutor(service);
        new TaskFour().executeOnExecutor(service);
    }

    private class TaskOneFragment extends AsyncTask<Void, List<String>, Void> {

        private ThreadState state;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            state = App.self.getState();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                while (!state.isShouldExit()) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    publishProgress(state.pullOutMessages());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(List<String>[] values) {
            super.onProgressUpdate(values);
            if (mTaskCallbacks != null) {
                mTaskCallbacks.flushMessages(values[0]);
            }
        }
    }
}
