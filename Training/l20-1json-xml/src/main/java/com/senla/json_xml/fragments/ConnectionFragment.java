package com.senla.json_xml.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.senla.json_xml.App;
import com.senla.json_xml.utils.JsonParserUtil;
import com.senla.json_xml.utils.ServerConnectionUtil;

import java.io.IOException;

import static com.senla.json_xml.utils.JsonParserUtil.getJsonLogin;
import static com.senla.json_xml.utils.JsonParserUtil.getJsonProfile;
import static com.senla.json_xml.utils.JsonParserUtil.getJsonUrl;
import static com.senla.json_xml.utils.JsonParserUtil.getJsonValueByKey;

public class ConnectionFragment extends Fragment {

    public static final String TAG = "TAG_CONNECTION_FRAGMENT";
    private ServerConnectionUtil serverConnection;
    private App.State state;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serverConnection = new ServerConnectionUtil();
        state = App.self.getState();
        setRetainInstance(true);
    }

    public void login(String login, String password) {
        String url = getJsonUrl(JsonParserUtil.Param.login);
        String json = getJsonLogin(login, password);
        new SendRequestTask(JsonParserUtil.Param.login, url, json).execute();
    }

    public void profile() {
        String url = getJsonUrl(JsonParserUtil.Param.profile);
        String json = getJsonProfile(state.getToken());
        new SendRequestTask(JsonParserUtil.Param.profile, url, json).execute();
    }

    public class SendRequestTask extends AsyncTask<Void, Void, String> {

        public static final String BROADCAST_ACTION_GET_RESPONSE = "local:SendRequestTask.BROADCAST_ACTION_GET_RESPONSE";
        public static final String EXTRA_RESULT = "EXTRA_RESULT";
        public static final String EXTRA_PARAM = "EXTRA_PARAM";

        private JsonParserUtil.Param param;
        private String url, json;

        SendRequestTask(JsonParserUtil.Param param, String url, String json) {
            this.param = param;
            this.url = url;
            this.json = json;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String responseValue = null;
            try {
                responseValue = serverConnection.post(url, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseValue;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            String token = getJsonValueByKey(response, JsonParserUtil.Key.token);
            App.self.getState().setToken(token);
            Log.e("TAG", param.name());
            LocalBroadcastManager.getInstance(App.self).sendBroadcastSync(
                    new Intent(BROADCAST_ACTION_GET_RESPONSE)
                            .putExtra(EXTRA_RESULT, response)
                            .putExtra(EXTRA_PARAM, param)
            );
        }
    }
}
