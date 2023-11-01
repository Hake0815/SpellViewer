package com.example.spellviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * BaseAdapter class for a ListView displaying characters
 */
public class ListAdapter extends BaseAdapter {
    private List<CharacterImage> characters;
    private final Context context;

    public void updateData(){
        notifyDataSetChanged();
    }

    /**
     * Constructor for ListAdapter
     * @param context ActivityContext of ListView
     * @param characters List of CharacterImage to be displayed in ListView
     */
    public ListAdapter(Context context, List<CharacterImage> characters) {

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
        textView.setText(characters.get(position).getName());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewAvatar);
        imageView.setImageBitmap(characters.get(position).getImage().bitmap);
        return convertView;
    }
}
