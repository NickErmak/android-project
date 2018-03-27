package com.senla.json_xml;

import android.app.Application;

public class App extends Application {

    public static App self;
    private State state;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        state = new State();
    }

    public State getState() {
        return state;
    }

    public class State {
        private String token, login, password;

        public void setToken(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }
        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void reset() {
            login="";
            password="";
            token="";
        }
    }
}
