package com.senla.texteditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup mRgColor, mRgSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mRgColor = (RadioGroup) findViewById(R.id.settings_rg_color);
        mRgSize = (RadioGroup) findViewById(R.id.settings_rg_size);
    }

    @Override
    protected void onPause() {
        super.onPause();

        switch (mRgColor.getCheckedRadioButtonId()) {
            case R.id.settings_rb_black:
                DataHandler.putColor(DataHandler.Color.BLACK);
                break;
            case R.id.settings_rb_blue:
                DataHandler.putColor(DataHandler.Color.BLUE);
                break;
        }

        switch (mRgSize.getCheckedRadioButtonId()) {
            case R.id.settings_rb_size12:
                DataHandler.putSize(DataHandler.Size.SIZE_12);
                break;
            case R.id.settings_rb_size20:
                DataHandler.putSize(DataHandler.Size.SIZE_20);
                break;
        }
    }
}
