package com.senla.texteditor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnCreate, mBtnEdit, mBtnView, mBtnSettings;

    private final String FILE_NAME = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnCreate = (Button) findViewById(R.id.main_btn_create_file);
        mBtnEdit = (Button) findViewById(R.id.main_btn_edit_file);
        mBtnView = (Button) findViewById(R.id.main_btn_view_file);
        mBtnSettings = (Button) findViewById(R.id.main_btn_settings);

        mBtnCreate.setOnClickListener(this);
        mBtnEdit.setOnClickListener(this);
        mBtnView.setOnClickListener(this);
        mBtnSettings.setOnClickListener(this);

        if (hasFile()) {
            mBtnCreate.setVisibility(View.INVISIBLE);
            mBtnEdit.setVisibility(View.VISIBLE);
            mBtnView.setVisibility(View.VISIBLE);
        } else {
            mBtnCreate.setVisibility(View.VISIBLE);
            mBtnEdit.setVisibility(View.INVISIBLE);
            mBtnView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_create_file:
                createNewFile();
                recreate();
                break;
            case R.id.main_btn_edit_file:
                startActivity(new Intent(this, CreateEditActivity.class)
                        .putExtra("action", Action.EDITING));
                break;
            case R.id.main_btn_view_file:
                startActivity(new Intent(this, CreateEditActivity.class)
                        .putExtra("action", Action.VIEWING));
                break;
            case R.id.main_btn_settings:
                startActivity(new Intent(this, SettingsActivity.class));
        }
    }

    private boolean createNewFile() {
        File file = new File(getFilesDir() + FILE_NAME);
        boolean result = false;
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean hasFile() {
        File file = new File(getFilesDir() + FILE_NAME);
        return file.isFile();
    }
}
