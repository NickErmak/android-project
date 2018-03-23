package com.senla.asynctask.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senla.asynctask.App;
import com.senla.asynctask.R;
import com.senla.asynctask.fragments.TaskFragment;
import com.senla.asynctask.states.ThreadState;
import com.senla.asynctask.tasks.TaskThree;

import java.util.List;

import static com.senla.asynctask.utils.PrinterUtil.append;

public class MainActivity extends AppCompatActivity implements TaskFragment.TaskCallbacks {

    private static final String TAG_TASK_FRAGMENT = "TAG_TASK_FRAGMENT";

    private TextView mTv;
    private Button mBtnRun;
    private TaskFragment fragment;
    private FragmentManager fm;
    private BroadcastReceiver taskReceiverActionFinish = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            append(mTv, intent.getStringExtra(TaskThree.EXTRA_RESULT));
            mBtnRun.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.tv_output);
        mTv.setMovementMethod(new ScrollingMovementMethod());
        mBtnRun = (Button) findViewById(R.id.btn_start);

        fm = getSupportFragmentManager();
        fragment = (TaskFragment) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        if (fragment == null) {
            fragment = new TaskFragment();
            fm.beginTransaction().add(fragment, TAG_TASK_FRAGMENT).commit();
        }
    }

    @Override
    public void flushMessages(List<String> messages) {
        append(mTv, messages);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
        LocalBroadcastManager.getInstance(App.self).registerReceiver(taskReceiverActionFinish,
                new IntentFilter(TaskThree.BROADCAST_ACTION_FINISH));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(App.self).unregisterReceiver(taskReceiverActionFinish);
    }

    private void updateData() {
        ThreadState state = App.self.getState();
        mTv.setText("");
        append(mTv, state.getMessages());
        if (state.isShouldExit()) {
            mBtnRun.setEnabled(true);
        } else {
            mBtnRun.setEnabled(false);
        }
    }

    public void onClickStart(View view) {
        App.self.getState().startNewSession();
        updateData();
        fragment.start();
    }


}
