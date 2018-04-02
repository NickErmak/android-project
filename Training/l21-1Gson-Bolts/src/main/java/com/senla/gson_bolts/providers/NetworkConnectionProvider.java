package com.senla.gson_bolts.providers;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.senla.gson_bolts.App;
import com.senla.gson_bolts.R;
import com.senla.gson_bolts.models.State;
import com.senla.gson_bolts.models.requests.RequestLogin;
import com.senla.gson_bolts.models.requests.RequestProfile;
import com.senla.gson_bolts.models.responses.ResponseLogin;
import com.senla.gson_bolts.models.responses.ResponseProfile;

import java.util.concurrent.Callable;

import bolts.Continuation;
import bolts.Task;

import static com.senla.gson_bolts.utils.NetworkConnectionUtil.sendRequest;

public class NetworkConnectionProvider {

    public static final String BROADCAST_ACTION = "local:NetworkConnectionProvider.BROADCAST_ACTION";
    public static final String EXTRA_STATUS = "EXTRA_STATUS";

    private enum URL_METHOD {
        login, profile
    }

    private static void sendBroadCast(State.RESPONSE_STATUS status) {
        LocalBroadcastManager.getInstance(App.self).sendBroadcast(
                new Intent(BROADCAST_ACTION)
                        .putExtra(EXTRA_STATUS, status)
        );
    }

    private static Task<Void> getProfileAsync(final RequestProfile request) {
        App.state.setRunning(true);
        return Task.callInBackground(new Callable<ResponseProfile>() {
            @Override
            public ResponseProfile call() throws Exception {
                return sendRequest(URL_METHOD.profile.name(), request, ResponseProfile.class);
            }
        }).onSuccess(new Continuation<ResponseProfile, Void>() {
            @Override
            public Void then(Task<ResponseProfile> task) throws Exception {
                ResponseProfile response = task.getResult();
                State.RESPONSE_STATUS status = State.RESPONSE_STATUS.valueOf(response.getStatus());
                switch (status) {
                    case ok:
                        App.state.setFirstName(response.getFirstName());
                        App.state.setLastName(response.getLastName());
                        App.state.setBirthDate(response.getBirthDate());
                        App.state.setNotes(response.getNotes());
                        break;
                    case error:
                        App.state.setErrorMessage(response.getMessage());
                        break;
                    default:
                        return null;
                }
                sendBroadCast(status);
                return null;
            }
        }).continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                if (task.isFaulted()) {
                    App.state.setErrorMessage(App.self.getString(R.string.error_message));
                    task.getError().printStackTrace();
                    sendBroadCast(State.RESPONSE_STATUS.error);
                }
                App.state.setRunning(false);
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    private static Task<Void> loginAsync(final RequestLogin request) {
        App.state.setRunning(true);
        return Task.callInBackground(new Callable<ResponseLogin>() {
            @Override
            public ResponseLogin call() throws Exception {
                return sendRequest(URL_METHOD.login.name(), request, ResponseLogin.class);
            }

        }).onSuccessTask(new Continuation<ResponseLogin, Task<Void>>() {
            @Override
            public Task<Void> then(Task<ResponseLogin> task) throws Exception {
                ResponseLogin response = task.getResult();
                State.RESPONSE_STATUS status = State.RESPONSE_STATUS.valueOf(response.getStatus());
                switch (status) {
                    case ok:
                        String token = task.getResult().getToken();
                        App.state.setToken(token);
                        RequestProfile request = new RequestProfile();
                        request.setToken(token);
                        return getProfileAsync(request);
                    case error:
                        App.state.setErrorMessage(response.getMessage());
                        sendBroadCast(status);
                    default:
                        return null;
                }
            }

        }).continueWith(new Continuation<Void, Void>() {
            @Override
            public Void then(Task<Void> task) throws Exception {
                if (task.isFaulted()) {
                    App.state.setErrorMessage(App.self.getString(R.string.error_message));
                    sendBroadCast(State.RESPONSE_STATUS.error);
                }
                App.state.setRunning(false);
                return null;
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public static void login(RequestLogin request) {
        if (!App.state.isRunning()) {
            loginAsync(request);
        }
    }

    public static void getProfile(RequestProfile request) {
        if (!App.state.isRunning()) {
            getProfileAsync(request);
        }
    }
}


