package com.senla.notebook.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.senla.notebook.R;
import com.senla.notebook.bean.NoteItem;
import com.senla.notebook.fragment.FragmentEditNote;
import com.senla.notebook.fragment.FragmentNoteList;
import com.senla.notebook.util.DataHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentNoteList.INoteClickEvent, FragmentEditNote.ISaveNoteEvent {

    private final int REQUEST_CODE_EDIT = 101;
    private final int REQUEST_CODE_CREATE = 102;
    private FragmentEditNote mFragEditNote;
    private FragmentNoteList mFragNoteList;
    private DataHandler dataHandler;
    private ArrayList<NoteItem> notes;
    private FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataHandler = new DataHandler(this);
        notes = dataHandler.getAll();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FragmentNoteList.KEY_NOTES, notes);
        mFragNoteList = (FragmentNoteList) getFragmentManager().findFragmentById(R.id.fragment_note_list);
        mFragNoteList.setArguments(bundle);

        mFragEditNote = new FragmentEditNote();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frame_edit_note, mFragEditNote);
            fTrans.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "result code" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case REQUEST_CODE_EDIT:
            case REQUEST_CODE_CREATE:
                String note = data.getStringExtra(EditActivity.KEY_NOTE_TEXT);
                String previousTitle = data.getStringExtra(EditActivity.KEY_PREVIOUS_TITLE);
                saveNote(note, previousTitle);
        }
    }

    @Override
    public void saveNote(String note, String previousTitle) {
        dataHandler.writeToFile(note, previousTitle);
        mFragNoteList.refreshNote(note);
    }

    @Override
    public void showNote(String title) {
        String note = dataHandler.getNote(title);

        if (mFragEditNote == null || findViewById(R.id.frame_edit_note) == null) {
            startActivityForResult(new Intent(this, EditActivity.class)
                    .putExtra(EditActivity.KEY_NOTE_TEXT, note), REQUEST_CODE_EDIT);
        } else {
            mFragEditNote.showNote(note);
        }
    }

    @Override
    public void createNote() {
        if (mFragEditNote == null || findViewById(R.id.frame_edit_note) == null) {
            startActivityForResult(new Intent(this, EditActivity.class)
                    .putExtra(EditActivity.KEY_NOTE_TEXT, ""), REQUEST_CODE_CREATE);
        } else {
            mFragEditNote.showNote("");
        }
    }
}
