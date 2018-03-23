package com.senla.server_client.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.senla.server_client.App;
import com.senla.server_client.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        Log.e("TAG", "startTask");
        new SendRequestTask().execute(param);
    }

    public class SendRequestTask extends AsyncTask<String, Void, String> {

        public static final String BROADCAST_ACTION_GET_RESPONSE = "local:SendRequestTask.BROADCAST_ACTION_GET_RESPONSE";
        public static final String EXTRA_RESULT = "EXTRA_RESULT";

        @Override
        protected String doInBackground(String... strings) {
            String responseValue = null;
            try {
                String encodedParam = Uri.encode(strings[0]);
                Request request = new Request.Builder()
                        .url(getString(R.string.url) + encodedParam)
                        .build();

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                responseValue = response.body().string();
            } catch (IOException e) {
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
