package com.senla.parcing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.senla.parcing.R;
import com.senla.parcing.adapters.ScreenAdapter;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public enum Screen {
        MAIN,
        SETTINGS
    }

    private EditText mEtText;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtText = findViewById(R.id.main_et);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        //debug
        mEtText.setText("Это тестовый   текст. Такие    дела\n" +
                "Номер то 8 (029) 111-22-33 и ещё 8 (029) 333-22-11,\n" +
                "кроме того <one>один</one>   и  <one>раз</one>.\n" +
                "Сайтецы www.google.com и www.senla.ru хорошие.");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        ListView lv = findViewById(R.id.main_lv);
        final BaseAdapter adapter = new ScreenAdapter(this, Arrays.asList(Screen.values()));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch ((Screen) adapter.getItem(position)) {
                    case MAIN:

                        break;
                    case SETTINGS:
                        startActivity(new Intent(getApplicationContext(), PrefActivity.class));
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });
    }

    public void onClickParse(View view) {
        Intent intent = new Intent(this, ResultActivity.class)
                .putExtra(ResultActivity.RESULT_KEY, mEtText.getText().toString());
        startActivity(intent);
    }
}
