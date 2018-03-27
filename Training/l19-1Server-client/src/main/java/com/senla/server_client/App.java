package com.senla.server_client;

import android.app.Application;

public class App extends Application {

    public static App self;
    private State state = new State();

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }

    public State getState() {
        return state;
    }

    public class State {

        private Boolean isLoading = false;
        private String request, response;

        public Boolean isLoading() {
            return isLoading;
        }

        public void setLoading(Boolean loading) {
            isLoading = loading;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}

