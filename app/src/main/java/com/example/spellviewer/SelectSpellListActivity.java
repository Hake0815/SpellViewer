package com.example.spellviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Activity class for the spell selection
 */
public class SelectSpellListActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private ActivityResultLauncher<Intent> spellCreatorLauncher;
    private List<Spell> spells;
    public static final String spellListFile = "spell_list";
    private TextInputLayout textInputLayoutLeft;
    private TextInputLayout textInputLayoutRight;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_spell_list);
        mode = getIntent().getStringExtra("Mode");
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
        textInputLayoutLeft = findViewById(R.id.textInputLayoutLeft);
        textInputLayoutRight = findViewById(R.id.textInputLayoutRight);

//        Set toolbar
        TextView toolbarTitle = findViewById(R.id.toolbarTextView);
        toolbarTitle.setText(getString(R.string.allSpellsList));
        ImageView toolbarImage = findViewById(R.id.toolbarImagaView);
        toolbarImage.setImageDrawable(getResources().getDrawable(R.drawable.baseline_arrow_back_24,null));
        toolbarImage.getLayoutParams().width=100;

        if (mode.equals("DisplayOnly")) {
            toolbarImage.setOnClickListener(v -> finish());
        } else {
            toolbarImage.setOnClickListener(v -> {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            });
        }

//        Read Spells from file, if it does not exist create the default spells
        if (MainActivity.fileExists(getApplicationContext(), spellListFile)) {
            readInSpellListFile();
        } else {
            spells = ExpandableListData.getData();
            writeSpellsToFile();
        }
//        Setup of Expanded List View
        expandableListView =  findViewById(R.id.expandableListView);
        if (mode.equals("DisplayOnly")) {
            expandableListAdapter = new ExpandableListAdapter(this, spells);
            Button confirmButton = findViewById(R.id.confirm_button);
            confirmButton.setText(getString(R.string.resetSpell));
            confirmButton.setCompoundDrawablesWithIntrinsicBounds(null,getDrawable(R.drawable.baseline_restart_alt_24),null,null);
        } else {
            expandableListAdapter = new ExpandableListAdapter(this, spells,true);
        }
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

//        Long clicks on Items in the ELV open context Menu
        registerForContextMenu(expandableListView);

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
                        Collections.sort(spells);
//                        Update ELV with selected filters
                        if (mode.equals("SelectMode")) {
                            expandableListAdapter = new ExpandableListAdapter(getApplicationContext(), spells, true);
                            expandableListView.setAdapter(expandableListAdapter);
                        }
                        filterData();
                        writeSpellsToFile();
                    }
                });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, getResources().getString(R.string.delete));

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
//        If the delete option is selected the respective item is removed from the spell list
//        and also from the ELV
        if (item.getTitle() == getResources().getString(R.string.delete)) {
//            get the selected group position
            int id = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//            remove selected spell from spells and file
            spells.remove(id);
            writeSpellsToFile();
//            Notify adapter that data has changed
            filterData();
        }
        return true;
    }


    /**
     * Button method to finish the activity and pass the selected spells in intend as result if in
     * Select mode, or reset spell list if in display mode
     * @param view View that calls this method
     */
    public void confirmSpells(View view) {
        if (mode.equals("DisplayOnly")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.resetSpell));
            alert.setMessage(getString(R.string.resetSpellDialogText));
            alert.setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                spells.clear();
                spells.addAll(ExpandableListData.getData());
                writeSpellsToFile();
                filterData();
            });
            alert.setNegativeButton(getString(R.string.cancel), ((dialog, which) -> {}));
            alert.show();
        } else {
            List<Spell> spells = expandableListAdapter.getCheckedSpells();
            Bundle extra = new Bundle();
            extra.putSerializable("Spells", (Serializable) spells);
            Intent intent = new Intent();
            intent.putExtra("Spells", extra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    /**
     * Button method to go to the spell creation activity
     * @param view View that calls this method
     */
    public void createSpell(View view) {
        Intent intent = new Intent(this, SpellCreationActivity.class);
        intent.putExtra("Mode", "NewSpell");
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

    /**
     * Method to write the spells list to file.
     */
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

    /**
     * Method to filter the data according to the selected filters in the autoCompleteTextViews
     */
    private void filterData(){
//                        Get selected Spell category
        String catSelection = textInputLayoutRight.getEditText().getText().toString();
//                        Get selected rank
        String rankSelection = textInputLayoutLeft.getEditText().getText().toString();
        expandableListAdapter.filterData(rankSelectionToInt(rankSelection), spellCatSelectionToSpellCat(catSelection));
    }

    /**
     * Method to read in the file with the saved spell List.
     */
    private void readInSpellListFile() {
        try {
            FileInputStream fis = getApplicationContext().openFileInput(spellListFile);
            ObjectInputStream is = new ObjectInputStream(fis);
            spells = (List<Spell>) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}