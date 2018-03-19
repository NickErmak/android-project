package com.senla.calculatortoolbar.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.senla.calculatortoolbar.R;
import com.senla.calculatortoolbar.fragment.FragCalculated;
import com.senla.calculatortoolbar.fragment.FragMainScreen;
import com.senla.calculatortoolbar.fragment.FragTextEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        SimpleAdapter simpleAdapter = createSimpleAdapter();

        ListView lv = (ListView) findViewById(R.id.main_lv);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        SimpleAdapter adapter = createSimpleAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String optionTag = ((TextView) view.findViewById(R.id.lv_tv_tag)).getText().toString();
                switch (optionTag) {
                    case FragMainScreen.FRAGMENT_TAG:
                        showFragment(FragMainScreen.newInstance(), FragMainScreen.FRAGMENT_TAG, true);
                        break;
                    case FragCalculated.FRAGMENT_TAG:
                        getSupportFragmentManager().popBackStack(FragMainScreen.FRAGMENT_TAG, 0);
                        showFragment(FragCalculated.newInstance(), FragCalculated.FRAGMENT_TAG, false);
                        break;
                    case FragTextEditor.FRAGMENT_TAG:
                        getSupportFragmentManager().popBackStack(FragMainScreen.FRAGMENT_TAG, 0);
                        showFragment(FragTextEditor.newInstance(), FragTextEditor.FRAGMENT_TAG, false);
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            showFragment(FragMainScreen.newInstance(), FragMainScreen.FRAGMENT_TAG, true);
        }

    }

    private SimpleAdapter createSimpleAdapter() {
        final String ATTRIBUTE_NAME_FRAGMENT = "fragment";
        final String ATTRIBUTE_NAME_TAG = "tag";

        String[] from = {ATTRIBUTE_NAME_FRAGMENT, ATTRIBUTE_NAME_TAG};
        int[] to = {R.id.lv_tv_fragment, R.id.lv_tv_tag};

        String[] fragments = {FragMainScreen.FRAGMENT_TITLE, FragCalculated.FRAGMENT_TITLE, FragTextEditor.FRAGMENT_TITLE};
        String[] tags = {FragMainScreen.FRAGMENT_TAG, FragCalculated.FRAGMENT_TAG, FragTextEditor.FRAGMENT_TAG};

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                fragments.length);
        Map<String, Object> m;

        for (int i = 0; i < fragments.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_FRAGMENT, fragments[i]);
            m.put(ATTRIBUTE_NAME_TAG, tags[i]);
            data.add(m);
        }
        return new SimpleAdapter(this, data, R.layout.listview_note, from, to);
    }

    private void showFragment(
            Fragment fragment,
            String tag,
            boolean clearBackStack
    ) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (clearBackStack) {
            fragmentManager.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            );
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

        /* TEST TOOLBAR and DRAWER

        getSupportActionBar().setTitle("newTItle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    public void onFragmentStart(String title, String tag) {
        mToolbar.setTitle(title);
    }
}
