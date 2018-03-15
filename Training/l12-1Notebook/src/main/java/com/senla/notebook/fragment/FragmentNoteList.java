package com.senla.notebook.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.senla.notebook.R;
import com.senla.notebook.adapter.NoteAdapter;
import com.senla.notebook.bean.NoteItem;

import java.util.Date;
import java.util.List;

public class FragmentNoteList extends Fragment {

    public interface INoteClickEvent {
        void showNote(String title);
        void createNote();
    }

    public final static String KEY_NOTES = "keyNotes";

    private INoteClickEvent noteClickEvent;
    private NoteAdapter adapter;
    private ListView mLvNotes;
    private List<NoteItem> notes;
    private NoteItem selectedNote;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        noteClickEvent = (INoteClickEvent) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_layout_note_list, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvNotes = (ListView) view.findViewById(R.id.main_lv_note);
        view.findViewById(R.id.main_btn_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteClickEvent.createNote();
                selectedNote = null;
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notes = getArguments().getParcelableArrayList(KEY_NOTES);
        adapter = new NoteAdapter(getActivity(), notes);
        mLvNotes.setAdapter(adapter);
        mLvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedNote = (NoteItem) adapter.getItem(position);
                String title = selectedNote.getTitle();
                noteClickEvent.showNote(title);
            }
        });
    }

    public void addNote(String note) {
        notes.add(new NoteItem(note.split("\n")[0], new Date().getTime()));
        adapter.notifyDataSetChanged();
    }

    public void refreshNote(String note) {
        if (selectedNote != null) {
            selectedNote.setTitle(note.split("\n")[0]);
            selectedNote.setDate(new Date().getTime());
            adapter.notifyDataSetChanged();
        } else {
            addNote(note);
        }

    }
}
