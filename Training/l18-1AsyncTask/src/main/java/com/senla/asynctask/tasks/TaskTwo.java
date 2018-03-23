package com.senla.asynctask.tasks;

import android.os.AsyncTask;

import com.senla.asynctask.App;
import com.senla.asynctask.states.ThreadState;

public class TaskTwo extends AsyncTask<Void, Void, Void> {

    private ThreadState state =App.self.getState();

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            int lastNumber = 66666;
            while (!state.isShouldExit()) {
                boolean isPrime = true;
                lastNumber++;
                for (int i = 2; i < lastNumber; i++) {
                    if (lastNumber % i == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    state.appendMessage(String.valueOf(lastNumber));
                    synchronized (state.getLock()) {
                        state.getLock().notify();
                    }
                    Thread.sleep(300);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
