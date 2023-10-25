package com.example.spellviewer;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import androidx.core.content.res.ResourcesCompat;

import java.util.List;

/**
 * Created by abc on 22-Mar-18.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Spell> expandableListSpells;
    //private HashMap<String, List<String>> expandableListDetail;

    public ExpandableListAdapter(Context context, List<Spell> expandableListSpells) {
        this.context = context;
        this.expandableListSpells = expandableListSpells;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListSpells.get(listPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Spell spell = (Spell) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child, null);
        }
        TextView expandedListTextViewRank = (TextView) convertView
                .findViewById(R.id.simpleTextRank);
        TextView expandedListTextViewMana = (TextView) convertView
                .findViewById(R.id.simpleTextMana);
        TextView expandedListTextViewDC = (TextView) convertView
                .findViewById(R.id.simpleTextDC);
        TextView expandedListTextViewAction = (TextView) convertView
                .findViewById(R.id.simpleTextAction);
        TextView expandedListTextViewRange = (TextView) convertView
                .findViewById(R.id.simpleTextRange);
        TextView expandedListTextViewDuration = (TextView) convertView
                .findViewById(R.id.simpleTextDuration);
        TextView expandedListTextViewDescription = (TextView) convertView
                .findViewById(R.id.simpleTextDescription);
        expandedListTextViewAction.setText(spell.getActionCost().toString());
        expandedListTextViewDescription.setText(spell.getDescription());
        expandedListTextViewDC.setText(MainActivity.resources.getString(R.string.dc) + ": "+ spell.getDc());
        expandedListTextViewDuration.setText(spell.getDuration());
        expandedListTextViewMana.setText(MainActivity.resources.getString(R.string.mana) + ": " + spell.getManaCost());
        expandedListTextViewRange.setText(spell.getRange());
        expandedListTextViewRank.setText(MainActivity.resources.getString(R.string.rank) + ": " + spell.getRank());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListSpells.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListSpells.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String spellName = ((Spell) getGroup(listPosition)).getName();
        SpellCat spellCat = ((Spell) getGroup(listPosition)).getSpellCat();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.parent, null);
        }
        TextView nameTextView = (TextView) convertView
                .findViewById(R.id.textViewLeft);
        nameTextView.setTypeface(null, Typeface.BOLD);
        nameTextView.setText(spellName);
        TextView catTextView = (TextView) convertView
                .findViewById(R.id.textViewRight);
        catTextView.setTypeface(null, Typeface.BOLD);
        catTextView.setText(spellCat.toString());
        int textColor;
        switch (spellCat) {
            case Enchantment:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.enchantment,null);
            case Summoning:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.enchantment,null);
            case Destruction:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.enchantment,null);
            case Alteration:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.enchantment,null);
            default:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.enchantment,null);;
        }
        catTextView.setTextColor(textColor);
        nameTextView.setTextColor(textColor);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}