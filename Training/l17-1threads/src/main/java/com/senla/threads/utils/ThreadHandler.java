package com.senla.threads.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadHandler {

    private static final String THREAD_FOUR_MESSAGE = "Yup!";
    private static final String NEW_LINE = "\n";
    private static final int BTN_STATUS_ENABLED = 1;

    private volatile boolean shouldExit;
    private Thread threadOne, threadTwo, threadFour;
    private final Object lock = new Object();
    private Handler handler;
    private List<String> messagesBuffer;
    private TextView mTvOutput;

    public ThreadHandler(TextView tvOutput, final Button btnRun) {
        messagesBuffer = new ArrayList<>();
        shouldExit = false;
        mTvOutput = tvOutput;

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case BTN_STATUS_ENABLED:
                        btnRun.setEnabled(true);
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public void startThreads() {
        startThreadOne();
        startThreadTwo();
        startThreadThree();
        startThreadFour();
    }

    private synchronized void appendMessage(String message) {
        messagesBuffer.add(message);
    }

    private synchronized List<String> pullOutMessages() {
        List<String> pulledMessages = new ArrayList<>(messagesBuffer);
        messagesBuffer.clear();
        return pulledMessages;
    }

    private void startThreadOne() {
        threadOne = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (!shouldExit) {
                        TimeUnit.MILLISECONDS.sleep(100);
                        handler.post(flushMessages);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        shouldExit = false;
        threadOne.start();
    }

    private Runnable flushMessages = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            Iterator<String> iterator = pullOutMessages().iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next())
                        .append(NEW_LINE);
            }
            mTvOutput.append(sb.toString());
        }
    };

    private void startThreadTwo() {
        threadTwo = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    int lastNumber = 66666;
                    while (!shouldExit) {
                        boolean isPrime = true;
                        lastNumber++;
                        for (int i = 2; i < lastNumber; i++) {
                            if (lastNumber % i == 0) {
                                isPrime = false;
                                break;
                            }
                        }
                        if (isPrime) {
                            appendMessage(String.valueOf(lastNumber));
                            synchronized (lock) {
                                lock.notify();
                            }
                            Thread.sleep(300);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadTwo.start();
    }

    private void startThreadThree() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    int count = 5;
                    while (count <= 10) {
                        Thread.sleep(1000);
                        appendMessage(String.valueOf(count));
                        count++;
                    }
                    shouldExit = true;
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    threadOne.join();
                    threadTwo.join();
                    threadFour.join();
                    handler.sendEmptyMessage(BTN_STATUS_ENABLED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startThreadFour() {
        threadFour = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (!shouldExit) {
                        synchronized (lock) {
                            lock.wait();
                        }
                        appendMessage(THREAD_FOUR_MESSAGE);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadFour.start();
    }
}
