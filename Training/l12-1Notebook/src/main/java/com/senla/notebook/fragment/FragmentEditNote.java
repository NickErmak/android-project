package com.senla.notebook.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.senla.notebook.R;

public class FragmentEditNote extends Fragment {

    public interface ISaveNoteEvent {
        void saveNote(String note, String previousTitle);
    }

    private EditText mEt;
    private ISaveNoteEvent saveNoteEvent;
    private String previousTitle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        saveNoteEvent = (ISaveNoteEvent) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_layout_edit_note, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEt = view.findViewById(R.id.edit_et);
        Button btnOk = view.findViewById(R.id.edit_btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = mEt.getText().toString();
                saveNoteEvent.saveNote(note, previousTitle);
            }
        });
    }

    public void showNote(String note) {
        mEt.setText(note);
        if (note.equals("")) {
            previousTitle = null;
        } else {
            previousTitle = note.split("\n")[0];
        }
    }
}
