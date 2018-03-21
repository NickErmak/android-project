package com.senla.threads.utils;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadCreator {

    private static boolean shouldExit;

    public static void startThreadOne(final Handler handler) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!shouldExit) {
                    List<String> messages = new ArrayList<>();

                    handler.sendEmptyMessage(1);
                    try {
                        TimeUnit.MILLISECONDS.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        shouldExit = false;
        thread.start();
    }
}
