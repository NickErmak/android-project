package com.senla.notebook.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.senla.notebook.R;
import com.senla.notebook.bean.NoteItem;
import com.senla.notebook.fragment.FragmentEditNote;
import com.senla.notebook.fragment.FragmentNoteList;
import com.senla.notebook.util.DataHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FragmentNoteList.INoteClickEvent {

    private final int REQUEST_CODE_EDIT = 101;
    private FragmentEditNote mFragEditNote;
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
        Fragment mFragNoteList = getFragmentManager().findFragmentById(R.id.fragment_note_list);
        mFragNoteList.setArguments(bundle);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mFragEditNote = new FragmentEditNote();
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.frame_edit_note, mFragEditNote);
            fTrans.commit();
        }
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


    @Override
    public void showNote(String title) {
        String note = dataHandler.getNote(title);

        if (mFragEditNote == null || findViewById(R.id.frame_edit_note) == null) {
            startActivityForResult(new Intent(this, EditActivity.class)
                    .putExtra(EditActivity.KEY_NOTE_TEXT, title), REQUEST_CODE_EDIT);
        } else {
            mFragEditNote.showNote(note);
        }
    }
}
