package com.example.spellviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Activity class for the spell selection
 */
public class SelectSpellListActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private ActivityResultLauncher<Intent> spellCreatorLauncher;
    private List<Spell> spells;
    private final String spellListFile = "spell_list";

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


//        Read Spells from file, if it does not exist create the default spells
        if (MainActivity.fileExists(getApplicationContext(), spellListFile)) {
            try {
                FileInputStream fis = getApplicationContext().openFileInput(spellListFile);
                ObjectInputStream is = new ObjectInputStream(fis);
                spells = (List<Spell>) is.readObject();
                is.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            spells = ExpandableListData.getData();
            writeSpellsToFile();
        }
//        Setup of Expanded List View
        expandableListView =  findViewById(R.id.expandableListView);
        expandableListAdapter = new ExpandableListAdapter(this, spells,true);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
        });
        expandableListView.setOnGroupCollapseListener(groupPosition -> {

        });

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> false);
//        Apply the filter when selected
        autoCompleteTextViewLeft.setOnItemClickListener((parent, view, position, id) -> {
//                Get the selection of the spell category
            String catSelection = textInputLayoutRight.getEditText().getText().toString();
//                Get selected rank
            String rankSelection = arrayRanksAdapter.getItem(position).toString();
            expandableListAdapter.filterData(rankSelectionToInt(rankSelection), spellCatSelectionToSpellCat(catSelection));
        });
        autoCompleteTextViewRight.setOnItemClickListener((parent, view, position, id) -> {
//                Get the selection of the spell category
            String rankSelection = textInputLayoutLeft.getEditText().getText().toString();
//                Get selected rank
            String catSelection = arraySpellCatsAdapter.getItem(position).toString();
            expandableListAdapter.filterData(rankSelectionToInt(rankSelection), spellCatSelectionToSpellCat(catSelection));
        });

        spellCreatorLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Spell newSpell;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // The created spell is obtained here
                        assert result.getData() != null;
                        Bundle extra = result.getData().getBundleExtra("NewSpell");
                        assert extra != null;
                        newSpell = (Spell) extra.getSerializable("NewSpell");

                        spells.add(newSpell);
//                        Update ELV with selected filters
                        expandableListAdapter = new ExpandableListAdapter(getApplicationContext(), spells,true);
                        expandableListView.setAdapter(expandableListAdapter);
//                        Get selected Spell category
                        String catSelection = textInputLayoutRight.getEditText().getText().toString();
//                        Get selected rank
                        String rankSelection = textInputLayoutLeft.getEditText().getText().toString();
                        expandableListAdapter.filterData(rankSelectionToInt(rankSelection), spellCatSelectionToSpellCat(catSelection));
                        writeSpellsToFile();
                    }
                });

    }

    /**
     * Button method to finish the activity and pass the selected spells in intend as result
     * @param view View that calls this method
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
     * Button method to go to the spell creation activity
     * @param view View that calls this method
     */
    public void createSpell(View view) {
        Intent intent = new Intent(this, SpellCreationActivity.class);
        spellCreatorLauncher.launch(intent);
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
    public static SpellCat spellCatSelectionToSpellCat(String spellCatSelection){
        String[] spellCats = MainActivity.resources.getStringArray(R.array.spellCatDropDown);

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

    private void writeSpellsToFile() {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(spellListFile, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(spells);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}