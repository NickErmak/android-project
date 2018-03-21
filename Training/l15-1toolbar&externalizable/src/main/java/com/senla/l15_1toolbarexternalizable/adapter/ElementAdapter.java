package com.senla.l15_1toolbarexternalizable.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.senla.l15_1toolbarexternalizable.R;
import com.senla.l15_1toolbarexternalizable.bean.Element;

import java.util.ArrayList;

public class ElementAdapter extends BaseAdapter implements View.OnClickListener {

    private static class ViewHolder {

        public Element elementTag;
        public EditText title;
        public TextView value;

        public ViewHolder(View view) {
            title = (EditText) view.findViewById(R.id.et_title);
            value = (TextView) view.findViewById(R.id.tv_value);
        }
    }

    private ArrayList<Element> elements;
    private LayoutInflater layoutInflater;

    public ElementAdapter(Activity activity, ArrayList<Element> elements) {
        layoutInflater = activity.getLayoutInflater();
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
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
            view = layoutInflater.inflate(R.layout.list_element, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Element element = elements.get(position);

        viewHolder.title.setText(element.getTitle());
        viewHolder.value.setText(String.valueOf(element.getValue()));
        viewHolder.elementTag = element;

        view.findViewById(R.id.btn_plus).setOnClickListener(this);
        view.findViewById(R.id.btn_minus).setOnClickListener(this);
        view.findViewById(R.id.btn_delete).setOnClickListener(this);
        ((EditText)view.findViewById(R.id.et_title)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    ViewGroup parent = (ViewGroup) view.getParent();
                    Element elementTag = ((ViewHolder) parent.getTag()).elementTag;
                    elementTag.setTitle(((TextView) view).getText().toString());
                }
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        Element elementTag = ((ViewHolder) parent.getTag()).elementTag;
        switch (view.getId()) {
            case R.id.btn_minus: {
                elementTag.decreaseValue();
                TextView tvValue = (TextView) parent.findViewById(R.id.tv_value);
                tvValue.setText(String.valueOf(elementTag.getValue()));
                break;
            }
            case R.id.btn_plus: {
                elementTag.increaseValue();
                TextView tvValue = (TextView) parent.findViewById(R.id.tv_value);
                tvValue.setText(String.valueOf(elementTag.getValue()));
                break;
            }
            case R.id.btn_delete:
                elements.remove(elementTag);
                notifyDataSetChanged();
                break;
        }
    }

}
