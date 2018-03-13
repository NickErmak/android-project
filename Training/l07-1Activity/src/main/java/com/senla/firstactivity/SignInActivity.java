package com.senla.firstactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtLogin, mEtPassword;
    private Button mBtnReg, mBtnReturn;

    private String errorEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEtLogin = (EditText) findViewById(R.id.etLogin);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        mBtnReg = (Button) findViewById(R.id.btnRegRegister);
        mBtnReturn = (Button) findViewById(R.id.btnRegReturn);
        mBtnReg.setOnClickListener(this);
        mBtnReturn.setOnClickListener(this);

        errorEmpty = getString(R.string.msg_error_empty);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegRegister:
                tryLogin();
                break;
            case R.id.btnRegReturn:
                finish();
                break;
        }
    }

    private void tryLogin() {
        mEtLogin.setError(null);
        mEtPassword.setError(null);

        String loginValue = mEtLogin.getText().toString().trim();
        String passwordValue = mEtPassword.getText().toString().trim();

        boolean hasError = false;

        if (loginValue.isEmpty()) {
            mEtLogin.setError(errorEmpty);
            hasError = true;
        }
        if (passwordValue.isEmpty()) {
            mEtPassword.setError(errorEmpty);
            hasError = true;
        }

        if (!hasError) {
            User user = new User(loginValue, passwordValue);
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }
}
