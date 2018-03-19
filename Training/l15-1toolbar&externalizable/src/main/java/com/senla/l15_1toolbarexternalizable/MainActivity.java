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

    private ElementAdapter mAdapter;
    private ArrayList<Element> mElements;
    private DataHandler mDataHandler;
    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDataHandler = new DataHandler(this);
        // mElements = mDataHandler.readFromFile();
        mElements = new ArrayList<>();
        mElements.add(new Element("tiitl1", 10));
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

                int count = mAdapter.getCount();

                Log.e("count = ", "" + count);

                mElements = new ArrayList<>();
                for (int i = 0; i < count; i++) {

                    ViewGroup view = (ViewGroup) mLv.getChildAt(i);
                    String title = ((TextView) view.findViewById(R.id.et_title)).getText().toString();
                    String value = ((TextView) view.findViewById(R.id.tv_value)).getText().toString();

                    Element element = new Element(title, Integer.valueOf(value));
                    mElements.add(element);
                    Log.e("TAG", "i = " + i + ", element = " + element.toString());
                }

                mDataHandler.writeToFile(mElements);
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
