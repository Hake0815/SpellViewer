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
 * Adapter for the ExpandableListView to display spell names and their category as the groups and
 * their detailed description as child.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context context;
//    List of spells that are displayed after filtering
    private List<Spell> expandableListSpells;
//    List of all spells that are available
    private List<Spell> unfilteredSpells;
//    If set to true, the ELV will have checkboxes in each group element
    private final boolean withCheckMarks;
//    Variable to store the state of an group element to be restored later (safety for scrolling)
    private GroupViewHolder groupViewHolder;
//    Array to keep track of all selected spells
    private boolean[] mGroupCheckStates;

    /**
     * Constructor for ELVAdapter if checkmarks are not needed.
     * @param context Activity context of the ExpandableListView
     * @param spellList List of spells to be displayed
     */
    public ExpandableListAdapter(Context context, List<Spell> spellList) {
        this.context = context;
        expandableListSpells = new ArrayList<>();
        expandableListSpells.addAll(spellList);
        unfilteredSpells = spellList;
        withCheckMarks = false;
    }

    /**
     * Constructor for ELVAdapter
     * @param context Activity context of the ExpandableListView
     * @param spellList List of spells to be displayed
     * @param withCheckMarks if true, checkboxes are created for each group element
     */
    public ExpandableListAdapter(Context context, List<Spell> spellList,boolean withCheckMarks) {
        this.context = context;
        expandableListSpells = new ArrayList<>();
        expandableListSpells.addAll(spellList);
        unfilteredSpells = spellList;
        this.withCheckMarks = withCheckMarks;
        mGroupCheckStates = new boolean[getGroupCount()];
    }

    /**
     * Method to overide data and display it unfiltered
     * @param newSpells new List of Spells
     */
    public void updateSpellList(List<Spell> newSpells) {
        unfilteredSpells = newSpells;
        expandableListSpells.clear();
        expandableListSpells.addAll(newSpells);
        notifyDataSetChanged();
        expandableListSpells.size();
        mGroupCheckStates = new boolean[getGroupCount()];
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
//            If ChildView has not been created yet, it is created here
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child, null);
        }
//        TextView texts are set to their respective values
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
//        Get the values that should be displayed in the GroupView
        int spellRank = ((Spell) getGroup(listPosition)).getRank();
        String spellName = ((Spell) getGroup(listPosition)).getName();
        SpellCat spellCat = ((Spell) getGroup(listPosition)).getSpellCat();
//        Convert the listPosition to the position in the unfiltered data, they are identical
//        if filters are turned off
        int unfilteredposition = unfilteredSpells.indexOf(getGroup(listPosition));
        if (convertView == null) {
//            If the view was not yet created it is created here
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
//            The the child views of the group view are stored in the groupViewHolder
            groupViewHolder.mGroupTextLeft = convertView
                    .findViewById(R.id.textViewLeft);
            groupViewHolder.mGroupTextRight = convertView
                    .findViewById(R.id.textViewRight);
            groupViewHolder.mImageViewRank = convertView
                    .findViewById(R.id.imageViewRank);
            if (withCheckMarks){
                convertView.setTag(R.layout.parent_withcheckmarks,groupViewHolder);
            } else {
                convertView.setTag(R.layout.parent,groupViewHolder);
            }
        } else {
//            retrieve the previous state of the view if it existed before
            if (withCheckMarks) {
                groupViewHolder = (GroupViewHolder) convertView.getTag(R.layout.parent_withcheckmarks);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag(R.layout.parent);
            }
        }
//        Text of group view is set
        if (spellRank == 1) {
            groupViewHolder.mImageViewRank.setImageDrawable(MainActivity.resources.getDrawable(R.drawable.romenone,null));
        } else if (spellRank == 2) {
            groupViewHolder.mImageViewRank.setImageDrawable(MainActivity.resources.getDrawable(R.drawable.romentwo,null));
        } else {
            groupViewHolder.mImageViewRank.setImageDrawable(MainActivity.resources.getDrawable(R.drawable.romenthree,null));
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
//            Set the checkbox to the state as it is saved in mGroupCheckStates
            groupViewHolder.mImageView.setImageResource((mGroupCheckStates[unfilteredposition]) ? R.drawable.btn_check_on : R.drawable.btn_check_off);
//            Define the onClickListener to toggle the checked state
            View.OnClickListener clickListener = v -> {
                mGroupCheckStates[unfilteredposition] = !mGroupCheckStates[unfilteredposition];
                ((ImageView)v).setImageResource((mGroupCheckStates[unfilteredposition]) ? R.drawable.btn_check_on : R.drawable.btn_check_off);
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

    /**
     * Class toi store the state of a group view
     */
    public final class GroupViewHolder {

        TextView mGroupTextLeft;
        TextView mGroupTextRight;
        ImageView mImageView;
        ImageView mImageViewRank;
    }

    /**
     * method to obtain the selected spells
     * @return List that contains all spells that have been selected by clicking the checkbox
     */
    public List<Spell> getCheckedSpells() {
        List<Spell> checkedSpells = new ArrayList<>();
        for (int i = 0; i < unfilteredSpells.size(); i++) {
            if (mGroupCheckStates[i]) {
                checkedSpells.add(unfilteredSpells.get(i));
            }
        }
        return checkedSpells;
    }

    /**
     * Method to filter the displayed date
     * @param rank Rank of spells to be displayed, if set to 0 all ranks are displayed
     * @param spellCat Spell category to be displayed, if set to null all categories are displayed
     */
    public void filterData(int rank, SpellCat spellCat){
//        List of displayed spells is cleared and filled afterwards with spells that fit the filter
//        criteria
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