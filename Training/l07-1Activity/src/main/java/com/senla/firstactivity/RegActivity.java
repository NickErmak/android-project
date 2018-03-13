package com.senla.firstactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    private final int MIN_LOGIN_LENGTH = 8;
    private final int MIN_PASSWORD_LENGTH = 4;
    private Button mBtnReturn, mBtnReg;
    private EditText mEtLogin, mEtPassword, mEtPasswordRepeat, mEtName, mEtSurname, mEtAddInfo;
    private RadioGroup mRgGender;
    private CheckBox mCbAgree;

    private String minLenthFormat;
    private String errorEmpty;
    private String errorPasswordsNotTheSame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        
        mBtnReturn = (Button) findViewById(R.id.reg_btn_return);
        mBtnReg = (Button) findViewById(R.id.reg_btn_register);
        mEtLogin = (EditText) findViewById(R.id.reg_et_login);
        mEtPassword = (EditText) findViewById(R.id.reg_et_password);
        mEtPasswordRepeat = (EditText) findViewById(R.id.reg_et_password2);
        mEtName = (EditText) findViewById(R.id.reg_et_name);
        mEtSurname = (EditText) findViewById(R.id.reg_et_surname);
        mRgGender = (RadioGroup) findViewById(R.id.reg_rg_gender);
        mEtAddInfo = (EditText) findViewById(R.id.reg_et_add_info);
        mCbAgree = (CheckBox) findViewById(R.id.reg_cb_agree);

        mBtnReg.setOnClickListener(this);
        mBtnReturn.setOnClickListener(this);
        mCbAgree.setOnClickListener(this);

        minLenthFormat = getString(R.string.min_length_format);
        errorEmpty = getString(R.string.msg_error_empty);
        errorPasswordsNotTheSame = getString(R.string.msg_error_passwords);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.reg_btn_return:
                this.finish();
                break;
            case R.id.reg_btn_register:
                User user = tryRegister();
                if (user != null) {
                    intent = new Intent(this, InfoActivity.class)
                            .putExtra("user", user);
                }
                break;
            case R.id.reg_cb_agree:
                if (mCbAgree.isChecked()) {
                    mBtnReg.setEnabled(true);
                } else {
                    mBtnReg.setEnabled(false);
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
            finish();
        }
    }

    private User tryRegister() {
        mEtLogin.setError(null);
        mEtPassword.setError(null);
        mEtPasswordRepeat.setError(null);
        mEtName.setError(null);
        mEtSurname.setError(null);

        String login = mEtLogin.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String passwordRepeat = mEtPasswordRepeat.getText().toString().trim();
        String name = mEtName.getText().toString().trim();
        String surname = mEtSurname.getText().toString().trim();
        RadioButton genderBtn = (RadioButton) findViewById(mRgGender.getCheckedRadioButtonId());
        String gender = genderBtn.getText().toString();
        String addInfo = mEtAddInfo.getText().toString().trim();

        boolean hasError = false;

        if (login.isEmpty()) {
            mEtLogin.setError(errorEmpty);
            hasError = true;
        } else if (login.length() < MIN_LOGIN_LENGTH) {
            mEtLogin.setError(String.format(minLenthFormat, MIN_LOGIN_LENGTH));
            hasError = true;
        }

        if (password.isEmpty()) {
            mEtPassword.setError(errorEmpty);
            hasError = true;
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            mEtPassword.setError(String.format(minLenthFormat, MIN_PASSWORD_LENGTH));
            hasError = true;
        }

        if (passwordRepeat.isEmpty()) {
            mEtPasswordRepeat.setError(errorEmpty);
            hasError = true;
        } else if (passwordRepeat.equals(password)) {
            mEtPasswordRepeat.setError(errorPasswordsNotTheSame);
            hasError = true;
        }

        if (name.isEmpty()) {
            mEtName.setError(errorEmpty);
            hasError = true;
        }

        if (surname.isEmpty()) {
            mEtSurname.setError(errorEmpty);
            hasError = true;
        }

        if (hasError) {
            return null;
        } else {
            return new User(login, password, name, surname, gender, addInfo);
        }
    }
}
