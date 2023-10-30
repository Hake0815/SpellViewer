package com.example.spellviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private List<String> characters;
    private final Context context;

    public void updateData(){
        notifyDataSetChanged();
    }

    public ListAdapter(Context context, List<String> characters) {

        this.characters = characters;
        this.context = context;
    }
    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Object getItem(int position) {
        return characters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_view_item, null);
        }
        TextView textView = (TextView) convertView
                .findViewById(R.id.textViewName);
        textView.setText(characters.get(position));
        return convertView;
    }
}