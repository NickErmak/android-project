package com.senla.gson_bolts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.senla.gson_bolts.models.requests.RequestLogin;
import com.senla.gson_bolts.models.requests.RequestProfile;
import com.senla.gson_bolts.models.responses.ResponseLogin;
import com.senla.gson_bolts.models.responses.ResponseProfile;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

import static com.senla.gson_bolts.utils.NetworkConnectionUtil.sendRequest;

public class ConnectionFragment extends Fragment {

    public static final String TAG = "TAG_CONNECTION_FRAGMENT";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void login(String login, String password) {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail("john@domain.tld");
        requestLogin.setPassword("123123");
        ResponseProfile responseProfile = executeTask(requestLogin).getResult();
        Log.e("TAG", "" + (responseProfile == null));//вот этот момент!
    }

    private Task<ResponseProfile> executeTask(final RequestLogin requestLogin) {
        return Task.callInBackground(new Callable<ResponseLogin>() {
            @Override
            public ResponseLogin call() throws Exception {
                return sendRequest("login", requestLogin, ResponseLogin.class);
            }
        }).onSuccess(new Continuation<ResponseLogin, ResponseProfile>() {
            @Override
            public ResponseProfile then(Task<ResponseLogin> task) throws Exception {
                Log.e("TAG", "onSuccess");
                String token = task.getResult().getToken();
                RequestProfile requestProfile = new RequestProfile();
                requestProfile.setToken(token);
                ResponseProfile responseProfile = sendRequest("profile", requestProfile, ResponseProfile.class);
                Log.e("TAG", responseProfile.toString());
                return responseProfile;
            }
        });
    }
}
