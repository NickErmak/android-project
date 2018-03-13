package com.senla.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Map<String, String> testMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testMap = new HashMap<>();
        testMap.put("1", "value1");

        Log.e("TAG", getFilesDir().getAbsolutePath());
    }
}
