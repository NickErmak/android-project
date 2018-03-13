package com.senla.notebook.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.senla.notebook.R;
import com.senla.notebook.util.DataHandler;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private final String KEY_NOTE = "keyNote";

    private EditText mEt;
    private Button mBtnOk, mBtnCancel;
    private DataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dataHandler = new DataHandler(this);

        mEt = (EditText) findViewById(R.id.edit_et);
        mBtnOk = (Button) findViewById(R.id.edit_btn_ok);
        mBtnCancel = (Button) findViewById(R.id.edit_btn_cancel);

        mBtnCancel.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);

        mEt.setText(getIntent().getStringExtra(KEY_NOTE));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_btn_ok:
                setResult(RESULT_OK, new Intent().putExtra("note", mEt.getText().toString()));
                finish();
                break;
            case R.id.edit_btn_cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
