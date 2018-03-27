package com.senla.json_xml.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.senla.json_xml.App;
import com.senla.json_xml.R;
import com.senla.json_xml.fragments.ConnectionFragment;
import com.senla.json_xml.utils.JsonParserUtil;
import com.senla.json_xml.utils.StringUtil;

import static com.senla.json_xml.utils.JsonParserUtil.getJsonValueByKey;
import static com.senla.json_xml.utils.StringUtil.checkEmail;

public class MainActivity extends AppCompatActivity {

    private App.State state;
    private ConnectionFragment mFrag;
    private TextView mTvError;
    private EditText mEtLogin, mEtPassword;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra(ConnectionFragment.SendRequestTask.EXTRA_RESULT);
            Log.e("TAG", response);
            JsonParserUtil.Param param = (JsonParserUtil.Param) intent.getSerializableExtra(ConnectionFragment.SendRequestTask.EXTRA_PARAM);
            JsonParserUtil.Status status = JsonParserUtil.Status.valueOf(getJsonValueByKey(response, JsonParserUtil.Key.status));
            switch (param) {
                case login:
                    switch (status) {
                        case ok:
                            mFrag.profile();
                            break;
                        case error:
                            String error = StringUtil.getError(status, getJsonValueByKey(response, JsonParserUtil.Key.message));
                            mTvError.setText(error);
                            break;
                    }
                    break;
                case profile:
                    Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class)
                            .putExtra(ProfileActivity.INFO_EXTRA, response);
                    startActivity(intentProfile);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtLogin = findViewById(R.id.main_et_login);
        mEtPassword = findViewById(R.id.main_et_password);
        mTvError = findViewById(R.id.main_tv_error);

        state = App.self.getState();
        FragmentManager fm = getSupportFragmentManager();
        mFrag = (ConnectionFragment) fm.findFragmentByTag(ConnectionFragment.TAG);
        if (mFrag == null) {
            mFrag = new ConnectionFragment();
            fm.beginTransaction()
                    .add(mFrag, ConnectionFragment.TAG)
                    .commit();
        }
    }

    public void onClickLogin(View view) {
        String login = mEtLogin.getText().toString();
        String password =   mEtPassword.getText().toString();

        boolean hasError = false;

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
            state.setLogin(login);
            state.setPassword(password);
            mFrag.login(login, password);
        }
    }

    @Override
    protected void onPause() {
        saveState();
        LocalBroadcastManager.getInstance(App.self).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        loadState();
        LocalBroadcastManager.getInstance(App.self).registerReceiver(
                receiver,
                new IntentFilter(ConnectionFragment.SendRequestTask.BROADCAST_ACTION_GET_RESPONSE)
        );
        super.onResume();
    }

    private void saveState() {
        state.setLogin(mEtLogin.getText().toString());
        state.setPassword(mEtPassword.getText().toString());
    }

    private void loadState() {
        mEtLogin.setText(state.getLogin());
        mEtPassword.setText(state.getPassword());
    }
}
