package com.senla.gson_bolts.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.senla.gson_bolts.App;
import com.senla.gson_bolts.R;
import com.senla.gson_bolts.models.State;
import com.senla.gson_bolts.models.requests.RequestLogin;
import com.senla.gson_bolts.providers.NetworkConnectionProvider;
import com.senla.gson_bolts.utils.StringUtil;

import static com.senla.gson_bolts.utils.StringUtil.checkEmail;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private TextView mTvError;
    private EditText mEtLogin, mEtPassword;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            State.RESPONSE_STATUS status = (State.RESPONSE_STATUS) intent.getSerializableExtra(NetworkConnectionProvider.EXTRA_STATUS);
            switch (status) {
                case ok:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    break;
                case error:
                    setError();
                    break;
            }
            dismissProgress();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtLogin = findViewById(R.id.main_et_login);
        mEtPassword = findViewById(R.id.main_et_password);
        mTvError = findViewById(R.id.main_tv_error);
    }

    public void onClickLogin(View view) {
        resetError();
        boolean hasError = false;
        String login = mEtLogin.getText().toString();
        String password = mEtPassword.getText().toString();

        if (login.isEmpty()) {
            mEtLogin.setError(getString(R.string.error_empty));
        } else if (!checkEmail(login)) {
            mEtLogin.setError(getString(R.string.error_incorrect_email));
            hasError = true;
        }

        if (password.isEmpty()) {
            mEtPassword.setError(getString(R.string.error_empty));
            hasError = true;
        }

        if (!hasError) {
            RequestLogin request = new RequestLogin();
            request.setEmail(login);
            request.setPassword(password);
            NetworkConnectionProvider.login(request);
            showProgress();
        }
    }

    private void setError() {
        mTvError.setText(StringUtil.getOutputError(App.state.getErrorMessage()));
    }

    private void resetError() {
        App.state.setErrorMessage("");
        mTvError.setText("");
        mEtLogin.setError(null);
        mEtPassword.setError(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.state.setLogin(mEtLogin.getText().toString());
        App.state.setPassword(mEtPassword.getText().toString());
        dismissProgress();
        LocalBroadcastManager.getInstance(App.self).unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFromState();
        LocalBroadcastManager.getInstance(App.self).registerReceiver(
                receiver,
                new IntentFilter(NetworkConnectionProvider.BROADCAST_ACTION)
        );
    }

    private void updateFromState() {
        if (App.state.isRunning()) {
            showProgress();
        }
        mEtLogin.setText(App.state.getLogin());
        mEtPassword.setText(App.state.getPassword());
        mTvError.setText(App.state.getErrorMessage());
    }

    private void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(
                    this,
                    getString(R.string.progress_dialog_title),
                    getString(R.string.progress_dialog_message),
                    true,
                    false
            );
        }
    }
}
