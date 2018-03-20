package com.senla.l15_1toolbarexternalizable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.senla.l15_1toolbarexternalizable.adapter.ElementAdapter;
import com.senla.l15_1toolbarexternalizable.bean.Element;
import com.senla.l15_1toolbarexternalizable.dao.DataHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_ELEMENTS = "KEY_ELEMENTS";

    private ElementAdapter mAdapter;
    private ArrayList<Element> mElements;
    private DataHandler mDataHandler;
    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataHandler = new DataHandler(this);

        if (savedInstanceState != null) {
            mElements = savedInstanceState.getParcelableArrayList(KEY_ELEMENTS);
        } else {
            mElements = mDataHandler.readFromFile();
        }
        Log.e("TAG", "onCreate = " + mElements);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdapter = new ElementAdapter(this, mElements);

        mLv = (ListView) findViewById(R.id.list_elements);
        mLv.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Element newElement = new Element();
                mElements.add(newElement);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.action_save:
                Log.e("TAG", "save = " + mElements);
                mDataHandler.writeToFile(mElements);
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ELEMENTS, mElements);
        Log.e("TAG", "saving = " + mElements);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mElements = savedInstanceState.getParcelableArrayList(KEY_ELEMENTS);
        Log.e("TAG", "loading = " + mElements);
    }
}
