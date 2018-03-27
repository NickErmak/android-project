package com.senla.server_client.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.senla.server_client.App;
import com.senla.server_client.R;
import com.senla.server_client.fragments.TaskHolderFragment;

public class MainActivity extends AppCompatActivity {

    private App.State state = App.self.getState();

    private EditText mEtParam;
    private TextView mTvResponse;
    private ProgressDialog mProgressDialog;
    private TaskHolderFragment mTaskHolder;
    private FragmentManager mFManager;
    private BroadcastReceiver mReceiverGetResponse = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(TaskHolderFragment.SendRequestTask.EXTRA_RESULT);
            state.setResponse(response);
            state.setLoading(false);
            mTvResponse.setText(response);
            dismissDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtParam = (EditText) findViewById(R.id.et_param);
        mTvResponse = (TextView) findViewById(R.id.tv_response);

        mFManager = getSupportFragmentManager();
        mTaskHolder = (TaskHolderFragment) mFManager.findFragmentByTag(TaskHolderFragment.TAG);
        if (mTaskHolder == null) {
            mTaskHolder = new TaskHolderFragment();
            mFManager.beginTransaction()
                    .add(mTaskHolder, TaskHolderFragment.TAG)
                    .commit();
        }
    }

    public void onClickSend(View view) {
        if (mProgressDialog == null) {
            showProgressDialog();
            mTaskHolder.startTask(mEtParam.getText().toString());
            App.self.getState().setLoading(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEtParam.setText(state.getRequest());
        mTvResponse.setText(state.getResponse());
        if (state.isLoading()) {
            showProgressDialog();
        }
        LocalBroadcastManager.getInstance(App.self).registerReceiver(mReceiverGetResponse,
                new IntentFilter(TaskHolderFragment.SendRequestTask.BROADCAST_ACTION_GET_RESPONSE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissDialog();
        state.setRequest(mEtParam.getText().toString());
        LocalBroadcastManager.getInstance(App.self).unregisterReceiver(mReceiverGetResponse);
    }

    public void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, "", getString(R.string.progress_dialog_message), true, false);
    }

    public void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
