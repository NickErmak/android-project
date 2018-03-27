package com.senla.server_client.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.senla.server_client.App;

import java.io.IOException;

import static com.senla.server_client.utils.ServerConnectionUtil.sendRequest;

public class TaskHolderFragment extends Fragment {

    public static final String TAG = "TAG_TASK_FRAGMENT";
    private Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startTask(String param) {
        new SendRequestTask().execute(param);
    }

    public class SendRequestTask extends AsyncTask<String, Void, String> {

        public static final String BROADCAST_ACTION_GET_RESPONSE = "local:SendRequestTask.BROADCAST_ACTION_GET_RESPONSE";
        public static final String EXTRA_RESULT = "EXTRA_RESULT";

        @Override
        protected String doInBackground(String... strings) {
            String responseValue = null;
            try {
                responseValue = sendRequest(strings[0]).body().string();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return responseValue;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            LocalBroadcastManager.getInstance(App.self).sendBroadcastSync(new Intent(BROADCAST_ACTION_GET_RESPONSE)
                    .putExtra(EXTRA_RESULT, s));
        }
    }
}
