package com.senla.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etLogin, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
    }

    public void btnLoginOnClick(View view) {
        String loginValue = etLogin.getText().toString();
        String passwordValue = etPassword.getText().toString();

        if (loginValue.equals(getString(R.string.login_correct))
                && passwordValue.equals(getString(R.string.password_correct))) {
            showToast(getText(R.string.toast_msg_success).toString());
            return;
        }

        if (loginValue.isEmpty()) {
            etLogin.setError(getText(R.string.toast_msg_error_empty));
        } else etLogin.setError(getText(R.string.toast_msg_error_incorrect));
        if (passwordValue.isEmpty()) {
            etPassword.setError(getText(R.string.toast_msg_error_empty));
        }
    }

    public void btnRegisterOnClick(View view) {
        showToast(getText(R.string.toast_msg_error).toString());
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
