package com.senla.asynctask.tasks;

import android.os.AsyncTask;

import com.senla.asynctask.App;
import com.senla.asynctask.R;
import com.senla.asynctask.states.ThreadState;

public class TaskFour extends AsyncTask<Void, Void, Void> {

    private ThreadState state = App.self.getState();

    @Override
    protected Void doInBackground(Void... voids) {
        String message = App.self.getString(R.string.task_four_message);

        try {
            while (!state.isShouldExit()) {
                synchronized (state.getLock()) {
                    state.getLock().wait();
                }
                state.appendMessage(message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
