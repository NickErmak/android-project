package com.senla.parcing.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.senla.parcing.activities.MainActivity;
import com.senla.parcing.R;

import java.util.List;

public class ScreenAdapter extends BaseAdapter{

    private static class ViewHolder {
        public TextView screenName;

        public ViewHolder(View view) {
            screenName = (TextView) view.findViewById(R.id.list_tv_screen_name);
        }
    }

    private LayoutInflater layoutInflater;
    private List<MainActivity.Screen> screens;

    public ScreenAdapter(Activity activity, List<MainActivity.Screen> screens) {
        this.layoutInflater = activity.getLayoutInflater();
        this.screens = screens;
    }

    @Override
    public int getCount() {
        return screens.size();
    }

    @Override
    public Object getItem(int position) {
        return screens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        ViewHolder viewHolder;

        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = layoutInflater.inflate(R.layout.list_screen, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        switch (screens.get(position)) {
            case MAIN:
                viewHolder.screenName.setText(R.string.screen_title_main);
                break;
            case SETTINGS:
                viewHolder.screenName.setText(R.string.screen_title_settings);
                break;
            default:
                break;
        }
        return view;
    }
}
