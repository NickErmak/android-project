package com.senla.asynctask;

import android.app.Application;

import com.senla.asynctask.states.ThreadState;

public class App extends Application {

    public static App self;
    private ThreadState threadState = new ThreadState();

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }

    public ThreadState getState() {
        return threadState;
    }
}

