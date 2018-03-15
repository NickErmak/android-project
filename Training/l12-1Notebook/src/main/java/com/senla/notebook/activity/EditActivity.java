package com.senla.notebook.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.senla.notebook.R;
import com.senla.notebook.fragment.FragmentEditNote;

public class EditActivity extends AppCompatActivity implements FragmentEditNote.ISaveNoteEvent {

    public static final String KEY_NOTE_TEXT = "keyNoteText";
    public static final String KEY_PREVIOUS_TITLE = "keyPreviousTitle";
    private FragmentEditNote fragEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        fragEditText = new FragmentEditNote();
        FragmentTransaction fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frame_edit_note, fragEditText);
        fTrans.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String note = getIntent().getStringExtra(KEY_NOTE_TEXT);
        fragEditText.showNote(note);
    }

    @Override
    public void saveNote(String note, String previousTitle) {
        Intent intent = new Intent()
                .putExtra(KEY_NOTE_TEXT, note).putExtra(KEY_PREVIOUS_TITLE, previousTitle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
