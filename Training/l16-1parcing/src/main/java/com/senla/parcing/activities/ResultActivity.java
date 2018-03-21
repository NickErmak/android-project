package com.senla.parcing.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.senla.parcing.R;

import static com.senla.parcing.util.Parser.parse;


public class ResultActivity extends AppCompatActivity {

    public static final String RESULT_KEY = "RESULT_KEY";
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView tvParsed = (TextView) findViewById(R.id.result_tv_parsed);
        tvParsed.setText(parseText(getIntent().getStringExtra(RESULT_KEY)));
    }

    private String parseText(String src) {
        boolean spaceParse, tagParse, uriParse, mobileParse, uppercaseParse;
        spaceParse = sPref.getBoolean(getString(R.string.preference_space_parser_key), false);
        tagParse = sPref.getBoolean(getString(R.string.preference_tags_parser_key), false);
        uriParse = sPref.getBoolean(getString(R.string.preference_uri_parser_key), false);
        mobileParse = sPref.getBoolean(getString(R.string.preference_mobile_parser_key), false);
        uppercaseParse = sPref.getBoolean(getString(R.string.preference_uppercase_parser_key), false);

        return parse(src, spaceParse, mobileParse, uppercaseParse, tagParse, uriParse);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
