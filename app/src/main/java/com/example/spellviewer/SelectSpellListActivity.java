package com.example.spellviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.List;

/**
 * Activity class for the spell selection
 */
public class SelectSpellListActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_spell_list);
//        Setup for drop down menus
//        Get String arrays from resources
        String[] ranks = getResources().getStringArray(R.array.ranksDropDown);
        String[] spellCats = getResources().getStringArray(R.array.spellCatDropDown);
//        Define Array adapter with String arrays
        ArrayAdapter arrayRanksAdapter = new ArrayAdapter(this,R.layout.dropdown_item,ranks);
        ArrayAdapter arraySpellCatsAdapter = new ArrayAdapter(this,R.layout.dropdown_item,spellCats);
//        Find AutoCompleteTextViews
        AutoCompleteTextView autoCompleteTextViewLeft = findViewById(R.id.autoCompleteTextViewLeft);
        AutoCompleteTextView autoCompleteTextViewRight = findViewById(R.id.autoCompleteTextViewRight);
//        Set adapters
        autoCompleteTextViewLeft.setAdapter(arrayRanksAdapter);
        autoCompleteTextViewRight.setAdapter(arraySpellCatsAdapter);
//        Find TextInput Layouts to obtain texts
        TextInputLayout textInputLayoutLeft = findViewById(R.id.textInputLayoutLeft);
        TextInputLayout textInputLayoutRight = findViewById(R.id.textInputLayoutRight);

//        Set toolbar title
        TextView toolbarTitle = findViewById(R.id.toolbarTextView);
        toolbarTitle.setText(getString(R.string.allSpellsList));


//        Setup of Expanded List View
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
//        Apply the filter when selected
        autoCompleteTextViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Get the selection of the spell category
                String catSelection = textInputLayoutRight.getEditText().getText().toString();
//                Get selected rank
                String rankSelection = arrayRanksAdapter.getItem(position).toString();
                expandableListAdapter.filterData(rankSelectionToInt(rankSelection), spellCatSelectionToSpellCat(catSelection));
            }
        });
        autoCompleteTextViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Get the selection of the spell category
                String rankSelection = textInputLayoutLeft.getEditText().getText().toString();
//                Get selected rank
                String catSelection = arraySpellCatsAdapter.getItem(position).toString();
                expandableListAdapter.filterData(rankSelectionToInt(rankSelection), spellCatSelectionToSpellCat(catSelection));
            }
        });

    }

    /**
     * Button method to finish the activity and pass the selected spells in intend as result
     * @param view
     */
    public void confirmSpells(View view) {
        List<Spell> spells = expandableListAdapter.getCheckedSpells();
        Bundle extra = new Bundle();
        extra.putSerializable("Spells", (Serializable) spells);
        Intent intent = new Intent();
        intent.putExtra("Spells", extra);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    /**
     * Method to convert the strings of the Dropdown menu for the filter to integer values
     * @param rankSelection String that should be one of the possible filter options
     * @return Integer value of filter option
     */
    private int rankSelectionToInt(String rankSelection){
        String[] ranks = getResources().getStringArray(R.array.ranksDropDown);

        if (rankSelection.equals(ranks[1])) {
            return 1;
        } else if (rankSelection.equals(ranks[2])) {
            return 2;
        } else if (rankSelection.equals(ranks[3])) {
            return 3;
        } else {
            return 0;
        }
    }

    /**
     * Method to convert the strings of the Dropdown menu for the filter to SpellCat enum
     * @param spellCatSelection String that should be one of the possible filter options
     * @return Enum value of filter option
     */
    private SpellCat spellCatSelectionToSpellCat(String spellCatSelection){
        String[] spellCats = getResources().getStringArray(R.array.spellCatDropDown);

        if (spellCatSelection.equals(spellCats[1])) {
            return SpellCat.Enchantment;
        } else if (spellCatSelection.equals(spellCats[2])) {
            return SpellCat.Summoning;
        } else if (spellCatSelection.equals(spellCats[3])) {
            return SpellCat.Destruction;
        } else if (spellCatSelection.equals(spellCats[4])) {
            return SpellCat.Alteration;
        } else {
            return null;
        }
    }
}