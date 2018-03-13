package com.senla.notebook.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senla.notebook.R;
import com.senla.notebook.bean.NoteItem;

import java.util.List;

import static com.senla.notebook.util.DateConverter.parseDateToString;

public class NoteAdapter extends BaseAdapter {

    private static class ViewHolder {
        public TextView title;
        public TextView date;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.lv_title);
            date = (TextView) view.findViewById(R.id.lv_date);
        }
    }

    private LayoutInflater layoutInflater;
    private List<NoteItem> notes;

    public NoteAdapter(Activity activity, List<NoteItem> notes) {
        layoutInflater = activity.getLayoutInflater();
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;

        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.listview_note, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        NoteItem note = notes.get(position);
        viewHolder.title.setText(parseDateToString(note.getDate()));
        viewHolder.date.setText(note.getTitle());

        return view;
    }
}
