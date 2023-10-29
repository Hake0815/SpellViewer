package com.example.spellviewer;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 22-Mar-18.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private List<Spell> expandableListSpells;
    private List<Spell> unfilteredSpells;
    private final boolean withCheckMarks;
    private GroupViewHolder groupViewHolder;
    private boolean[] mGroupCheckStates;
    public ExpandableListAdapter(Context context, List<Spell> spellList) {
        this.context = context;
        expandableListSpells = new ArrayList<>();
        expandableListSpells.addAll(spellList);
        unfilteredSpells = spellList;
        this.withCheckMarks = false;
    }

    public ExpandableListAdapter(Context context, List<Spell> spellList,boolean withCheckMarks) {
        this.context = context;
        expandableListSpells = new ArrayList<>();
        expandableListSpells.addAll(spellList);
        unfilteredSpells = spellList;
        this.withCheckMarks = withCheckMarks;
        mGroupCheckStates = new boolean[getGroupCount()];
    }

    public void updateSpellList(List<Spell> newSpells) {
        unfilteredSpells = newSpells;
        expandableListSpells.clear();
        expandableListSpells.addAll(newSpells);
        notifyDataSetChanged();
        mGroupCheckStates = new boolean[getGroupCount()];
    }

    public void updateData(){
        notifyDataSetChanged();
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
        TextView expandedListTextViewRank = convertView
                .findViewById(R.id.simpleTextRank);
        TextView expandedListTextViewMana = convertView
                .findViewById(R.id.simpleTextMana);
        TextView expandedListTextViewDC = convertView
                .findViewById(R.id.simpleTextDC);
        TextView expandedListTextViewAction = convertView
                .findViewById(R.id.simpleTextAction);
        TextView expandedListTextViewRange = convertView
                .findViewById(R.id.simpleTextRange);
        TextView expandedListTextViewDuration = convertView
                .findViewById(R.id.simpleTextDuration);
        TextView expandedListTextViewDescription = convertView
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
            groupViewHolder = new GroupViewHolder();
            if (withCheckMarks){
                convertView = layoutInflater.inflate(R.layout.parent_withcheckmarks, null);
                groupViewHolder.mImageView = (ImageView) convertView
                        .findViewById(R.id.imageView);
            } else {
                convertView = layoutInflater.inflate(R.layout.parent, null);
            }
            groupViewHolder.mGroupTextLeft = (TextView) convertView
                    .findViewById(R.id.textViewLeft);
            groupViewHolder.mGroupTextRight = (TextView) convertView
                    .findViewById(R.id.textViewRight);
            if (withCheckMarks){
                convertView.setTag(R.layout.parent_withcheckmarks,groupViewHolder);
            } else {
                convertView.setTag(R.layout.parent,groupViewHolder);
            }
        } else {
            if (withCheckMarks) {
                groupViewHolder = (GroupViewHolder) convertView.getTag(R.layout.parent_withcheckmarks);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag(R.layout.parent);
            }
        }
        groupViewHolder.mGroupTextLeft.setTypeface(null, Typeface.BOLD);
        groupViewHolder.mGroupTextLeft.setText(spellName);
        groupViewHolder.mGroupTextRight.setTypeface(null, Typeface.BOLD);
        groupViewHolder.mGroupTextRight.setText(spellCat.toString());
        int textColor;
        switch (spellCat) {
            case Enchantment:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.enchantment,null);
                break;
            case Summoning:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.summoning,null);
                break;
            case Destruction:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.destruction,null);
                break;
            case Alteration:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.alteration,null);
                break;
            default:
                textColor = ResourcesCompat.getColor(MainActivity.resources,R.color.text,null);
        }
        groupViewHolder.mGroupTextRight.setTextColor(textColor);
        groupViewHolder.mGroupTextLeft.setTextColor(textColor);

        if (withCheckMarks) {
            groupViewHolder.mImageView.setImageResource((mGroupCheckStates[listPosition]) ? R.drawable.btn_check_on : R.drawable.btn_check_off);
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mGroupCheckStates[listPosition] = !mGroupCheckStates[listPosition];
                    ((ImageView)v).setImageResource((mGroupCheckStates[listPosition]) ? R.drawable.btn_check_on : R.drawable.btn_check_off);
                }
            };
            groupViewHolder.mImageView.setOnClickListener(clickListener);
        }

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

    public final class GroupViewHolder {

        TextView mGroupTextLeft;
        TextView mGroupTextRight;
        ImageView mImageView;
    }

    public List<Spell> getCheckedSpells() {
        List<Spell> checkedSpells = new ArrayList<>();
        for (int i = 0; i < getGroupCount(); i++) {
            if (mGroupCheckStates[i]) {
                checkedSpells.add(expandableListSpells.get(i));
            }
        }
        return checkedSpells;
    }


    public void filterData(int rank, SpellCat spellCat){
        expandableListSpells.clear();
        if (rank == 0 && spellCat == null) {
            expandableListSpells.addAll(unfilteredSpells);
        } else if (rank == 0) {
            for (Spell spell : unfilteredSpells) {
                if (spell.getSpellCat() == spellCat) {
                    expandableListSpells.add(spell);
                }
            }
        } else if (spellCat == null) {
            for (Spell spell : unfilteredSpells) {
                if (spell.getRank() == rank) {
                    expandableListSpells.add(spell);
                }
            }
        } else {
            for (Spell spell : unfilteredSpells) {
                if (spell.getRank() == rank && spell.getSpellCat() == spellCat) {
                    expandableListSpells.add(spell);
                }
            }
        }
        notifyDataSetChanged();
    }
}