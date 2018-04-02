package com.senla.retrofit.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.senla.retrofit.App;
import com.senla.retrofit.R;
import com.senla.retrofit.models.State;
import com.senla.retrofit.models.requests.RequestProfile;
import com.senla.retrofit.providers.NetworkConnectionProvider;

import static com.senla.retrofit.utils.StringUtil.getBirthDate;

public class ProfileActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            State.RESPONSE_STATUS status = (State.RESPONSE_STATUS) intent.getSerializableExtra(NetworkConnectionProvider.EXTRA_STATUS);
            switch (status) {
                case ok:
                    updateFromState();
                    break;
                case error:
                    Toast.makeText(
                            getApplicationContext(),
                            App.self.getText(R.string.error_message),
                            Toast.LENGTH_SHORT
                    ).show();
                    break;
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.profile_swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestProfile request = new RequestProfile();
                request.setToken(App.state.getToken());
                NetworkConnectionProvider.getProfile(request);
            }
        });
        updateFromState();
    }

    private void updateFromState() {
        ((TextView) findViewById(R.id.profile_tv_email_value)).setText(App.state.getLogin());
        ((TextView) findViewById(R.id.profile_tv_first_name_value)).setText(App.state.getFirstName());
        ((TextView) findViewById(R.id.profile_tv_last_name_value)).setText(App.state.getLastName());
        ((TextView) findViewById(R.id.profile_tv_birth_date_value)).setText(getBirthDate(App.state.getBirthDate()));
        ((TextView) findViewById(R.id.profile_tv_notes_value)).setText(App.state.getNotes());
    }

    public void onClickLogOut(View view) {
        App.state = new State();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(App.self).unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(App.self).registerReceiver(
                receiver,
                new IntentFilter(NetworkConnectionProvider.BROADCAST_ACTION)
        );
    }
}
