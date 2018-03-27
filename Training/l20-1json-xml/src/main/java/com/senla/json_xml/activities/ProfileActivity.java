package com.senla.json_xml.activities;

import static com.senla.json_xml.utils.JsonParserUtil.getJsonValueByKey;
import static com.senla.json_xml.utils.StringUtil.getBirthDate;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.senla.json_xml.App;
import com.senla.json_xml.R;
import com.senla.json_xml.utils.JsonParserUtil;

public class ProfileActivity extends AppCompatActivity {

    public static final String INFO_EXTRA = "INFO_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String response = getIntent().getStringExtra(INFO_EXTRA);
        ((TextView) findViewById(R.id.profile_tv_email_value)).setText(App.self.getState().getLogin());
        ((TextView) findViewById(R.id.profile_tv_first_name_value)).setText(getJsonValueByKey(response, JsonParserUtil.Key.firstName));
        ((TextView) findViewById(R.id.profile_tv_last_name_value)).setText(getJsonValueByKey(response, JsonParserUtil.Key.lastName));
        String birthDate = getBirthDate(getJsonValueByKey(response, JsonParserUtil.Key.birthDate));
        ((TextView) findViewById(R.id.profile_tv_birth_date_value)).setText(birthDate);
        ((TextView) findViewById(R.id.profile_tv_notes_value)).setText(getJsonValueByKey(response, JsonParserUtil.Key.notes));
    }

    public void onClickLogOut(View view) {
        App.self.getState().reset();
        finish();
    }
}
