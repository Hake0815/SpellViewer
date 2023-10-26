package com.example.spellviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectSpellListActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_spell_list);

        expandableListView =  findViewById(R.id.expandableListView);
        List<Spell> spells = ExpandableListData.getData();
        expandableListAdapter = new ExpandableListAdapter(this, spells,true);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }

        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                return false;
            }
        });



        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                long packedPosition = expandableListView.getExpandableListPosition(position);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
//                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
//                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);


                /*  if group item clicked */
                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    //  ...
                    onGroupLongClick(view);
//                    expandableListView.setItemChecked(groupPosition, true);

                }

                /*  if child item clicked */
//                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
//                    //  ...
//                    onChildLongClick(groupPosition, childPosition);
//                }

                return true;
            }

            public void onGroupLongClick(View view){
                ((SpellListItem) view).toggle();
            }

        });


    }

    public void confirmSpells(View view) {
        SpellListItem currentView;
        List<Spell> spells = new ArrayList<>();
        for (int i = 0; i<expandableListAdapter.getGroupCount(); i++){
            if (expandableListView.getChildAt(i) instanceof SpellListItem) {
                currentView = (SpellListItem) expandableListView.getChildAt(i);
//              currentView = (SpellListItem) expandableListAdapter.getGroupView(i,true, null, null);
                if (currentView.isChecked()) {
                    spells.add((Spell) expandableListAdapter.getGroup(i));
                }
            }
        }
        Bundle extra = new Bundle();
        extra.putSerializable("Spells", (Serializable) spells);
        Intent intent = new Intent();
        intent.putExtra("Spells", extra);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}