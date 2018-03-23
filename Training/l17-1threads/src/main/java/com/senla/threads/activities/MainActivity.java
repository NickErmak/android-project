package com.senla.threads.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senla.threads.R;
import com.senla.threads.utils.ThreadHandler;

public class MainActivity extends AppCompatActivity {

    private ThreadHandler threadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRun = (Button) findViewById(R.id.btn_start);
        TextView tvOutput = (TextView) findViewById(R.id.tv_output);
        threadHandler = new ThreadHandler(tvOutput, btnRun);
    }

    public void onClickStart(View view) {
        view.setEnabled(false);
        threadHandler.startThreads();
    }
}
