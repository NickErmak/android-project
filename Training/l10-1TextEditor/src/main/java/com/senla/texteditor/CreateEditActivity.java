package com.senla.texteditor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CreateEditActivity extends AppCompatActivity {

    private Action action;

    private final String FILE_NAME = "data";
    private File file;
    private EditText mEtText;
    private TextView mTvTitle;

    private final String TITLE_EDITING = "Editing";
    private final String TITLE_VIEWING = "Viewing";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit);

        file = new File(getFilesDir() + FILE_NAME);
        mEtText = (EditText) findViewById(R.id.create_edit_pt);
        mTvTitle = (TextView) findViewById(R.id.create_edit_tv_title);

        action = (Action) getIntent().getSerializableExtra("action");
        switch (action) {
            case EDITING:
                mTvTitle.setText(TITLE_EDITING);
                mEtText.setText(convertLinesForEdit());
                break;

            case VIEWING:
                mTvTitle.setText(TITLE_VIEWING);
                mEtText.setFocusable(false);
                loadTextConfig();
                mEtText.setText(convertLinesForView());
                break;
        }
    }

    private void loadTextConfig() {
        int colorValue;
        float sizeValue;
        switch (DataHandler.loadColor()) {
            case BLUE:
                colorValue = getResources().getColor(R.color.colorBlue);
                break;
            case BLACK:
            default:
                colorValue = getResources().getColor(R.color.colorBlack);
                break;
        }
        switch (DataHandler.loadSize()) {
            case SIZE_12:
            default:
                sizeValue = getResources().getDimension(R.dimen.tv_size_12);
                break;
            case SIZE_20:
                sizeValue = getResources().getDimension(R.dimen.tv_size_20);
                break;
        }

        mEtText.setTextColor(colorValue);
        mEtText.setTextSize(sizeValue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (action == Action.EDITING) {
            writeToFile(mEtText.getText().toString());
        }
    }

    private void writeToFile(String data) {
        try (FileOutputStream fos = new FileOutputStream(file); OutputStreamWriter osw = new OutputStreamWriter(fos); BufferedWriter bw = new BufferedWriter(osw)) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertLinesForView() {
        StringBuilder sb = new StringBuilder();
        List<String> lines = readLinesFromFile();
        Iterator iterator = lines.iterator();
        int count = 1;
        while (iterator.hasNext()) {
            sb.append(count++).append(". ");
            sb.append(iterator.next()).append("\n");
        }
        return sb.toString();
    }

    private String convertLinesForEdit() {
        StringBuilder sb = new StringBuilder();
        List<String> lines = readLinesFromFile();
        Iterator iterator = lines.iterator();

        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n");
        }
        return sb.toString();
    }

    private List<String> readLinesFromFile() {
        List<String> lines = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file); InputStreamReader isr = new InputStreamReader(fis); BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
