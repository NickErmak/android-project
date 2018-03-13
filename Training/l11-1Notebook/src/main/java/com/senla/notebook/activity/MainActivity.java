package com.senla.notebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.senla.notebook.R;
import com.senla.notebook.adapter.NoteAdapter;
import com.senla.notebook.bean.NoteItem;
import com.senla.notebook.util.DataHandler;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_EDITING = 228;
    private final String KEY_NOTE = "keyNote";

    private DataHandler dataHandler;
    private ListView mLvNote;
    private NoteAdapter adapter;
    List<NoteItem> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // PreferenceManager.setDefaultValues(this, R.xml.pref, false);
        dataHandler = new DataHandler(this);
        notes = dataHandler.getAll();
        adapter = new NoteAdapter(this, notes);

        mLvNote = (ListView) findViewById(R.id.main_lv_note);
        mLvNote.setAdapter(adapter);
        mLvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                String title = ((NoteItem) adapter.getItem(position)).getTitle();
                intent.putExtra(KEY_NOTE ,dataHandler.getNote(title));
                startActivityForResult(intent, REQUEST_CODE_EDITING);
            }
        });
    }

    public void btnAddOnClick(View view) {
        startActivityForResult(new Intent(this, EditActivity.class), REQUEST_CODE_EDITING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_EDITING:
                String note = data.getStringExtra("note");
                dataHandler.writeToFile(note);
                notes.add(new NoteItem(note.split("\n")[0], new Date().getTime()));
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
