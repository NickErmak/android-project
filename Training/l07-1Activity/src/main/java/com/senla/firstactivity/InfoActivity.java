package com.senla.firstactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtnInfoReturn;
    private TextView mTvLogin, mTvPassword, mTvName, mTvSurname, mTvSex, mTvAddInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        User mUser = (User) getIntent().getSerializableExtra("user");
        mBtnInfoReturn = (Button) findViewById(R.id.btnInfoReturn);
        mBtnInfoReturn.setOnClickListener(this);
        mTvLogin = (TextView) findViewById(R.id.tvInfoLogin);
        mTvPassword = (TextView) findViewById(R.id.tvInfoPassword);
        mTvName = (TextView) findViewById(R.id.tvInfoName);
        mTvSurname = (TextView) findViewById(R.id.tvInfoSurname);
        mTvSex = (TextView) findViewById(R.id.tvInfoSex);
        mTvAddInfo = (TextView) findViewById(R.id.tvInfoInfo);
        setUserInfo(mUser);
    }

    private void setUserInfo(User user) {
        mTvLogin.append(user.getLogin());
        mTvPassword.append(user.getPassword());
        mTvName.append(user.getName());
        mTvSurname.append(user.getSurname());
        mTvSex.append(String.valueOf(user.getGender()));
        mTvAddInfo.append(user.getAddInfo());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInfoReturn:
                finish();
        }
    }
}
