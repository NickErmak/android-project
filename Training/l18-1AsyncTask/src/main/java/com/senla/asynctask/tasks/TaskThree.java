package com.senla.asynctask.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import com.senla.asynctask.App;
import com.senla.asynctask.R;
import com.senla.asynctask.states.ThreadState;

public class TaskThree extends AsyncTask<Void, Void, Void> {

    public static final String BROADCAST_ACTION_FINISH = "local:TaskThree.BROADCAST_ACTION_FINISH";
    public static final String EXTRA_RESULT = "EXTRA_RESULT";

    private ThreadState state = App.self.getState();

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            int count = 5;
            while (count <= 10) {
                Thread.sleep(1000);
                state.appendMessage(String.valueOf(count));
                count++;
            }
            state.setShouldExit(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        LocalBroadcastManager.getInstance(App.self).sendBroadcastSync(new Intent(BROADCAST_ACTION_FINISH)
                .putExtra(EXTRA_RESULT, App.self.getString(R.string.finish_message)));
    }
}
