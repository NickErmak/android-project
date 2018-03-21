package com.senla.threads.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.senla.threads.R;
import com.senla.threads.utils.ThreadCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button mBtnRun;
    Thread t1, t2, t4;
    private volatile boolean shouldExit;

    public final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            mPb.incrementProgressBy(1);
            super.handleMessage(msg);
        }
    };

    private TextView mTvOutput;
    private ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnRun = (Button) findViewById(R.id.btn_start);
        mTvOutput = (TextView) findViewById(R.id.tv_output);
        mPb = (ProgressBar) findViewById(R.id.pb);
    }


    public void onClickStart(View view) {
        view.setEnabled(false);
        startThreadOne();
        startThreadTwo();
        handler.post(t3);
        startThreadFour();
    }







    List<Integer> primeNumbers = new ArrayList<>();

    public void startThreadOne() {
        t1 = new Thread(new Runnable() {

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
        t1.start();
    }

    public void startThreadTwo() {

       t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int lastNumber = 5;
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
                        primeNumbers.add(lastNumber);
                        synchronized (handler) {
                        handler.notify();
                        }
                        Log.e("TAG", "prime = "+String.valueOf(lastNumber));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        shouldExit = false;
        t2.start();
    }


       Runnable t3 = new Runnable() {
            @Override
            public void run() {
                int count = 5;
                while (count <= 10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }


                shouldExit = true;

                try {
                    t1.join();

                    t2.join();
                    Log.e("TAG", "before t4");
                    synchronized (handler) {
                    handler.notify();}
                    t4.join();
                    Log.e("TAG", "after t4");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mBtnRun.setEnabled(true);
            }
        };



    public void startThreadFour() {
        t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!shouldExit) {
                    Log.e("TAG", "yep");
                    try {
                        synchronized (handler) {
                            handler.wait();
                            Log.e("TAG", "ALIVE!");
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        shouldExit = false;
        t4.start();
    }

}
