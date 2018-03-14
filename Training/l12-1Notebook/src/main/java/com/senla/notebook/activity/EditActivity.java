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

public class EditActivity extends AppCompatActivity {

    public static final String KEY_NOTE_TEXT = "keyNoteText";

    private EditText mEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEt = (EditText) findViewById(R.id.edit_et);
        mEt.setText(getIntent().getStringExtra(KEY_NOTE_TEXT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        String note = mEt.getText().toString();
        setResult(RESULT_OK, new Intent().putExtra(KEY_NOTE_TEXT, note));
    }
}
