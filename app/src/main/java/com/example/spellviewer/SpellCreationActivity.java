package com.example.spellviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Activity class for spell creator
 */
public class SpellCreationActivity extends AppCompatActivity {
    private String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_spell);
        mode = getIntent().getStringExtra("Mode");
        TextView toolbarTitle = findViewById(R.id.toolbarTextView);
        if (mode.equals("NewSpell")) {
//            A new Spell should be create
//            Set the toolbar title
            toolbarTitle.setText(getString(R.string.newSpell));
        } else if (mode.equals("ModifySpell")) {
//            Get the spell from intent
            Bundle extra = getIntent().getBundleExtra("ModSpell");
            Spell modSpell = (Spell) extra.getSerializable("ModSpell");
            toolbarTitle.setText(modSpell.getName() + getResources().getString(R.string.modSpell));
            TextInputLayout textInputLayoutRank = findViewById(R.id.textInputLayoutRank);
            TextInputLayout textInputLayoutSpellCat = findViewById(R.id.textInputLayoutSpellCat);
            TextInputLayout textInputLayoutAction = findViewById(R.id.textInputLayoutAction);
            EditText editTextName = findViewById(R.id.editTextName);
            EditText editTextDescription = findViewById(R.id.editTextDescription);
            EditText editTextDC = findViewById(R.id.editTextDC);
            EditText editTextDuration = findViewById(R.id.editTextDuration);
            EditText editTextMana = findViewById(R.id.editTextMana);
            EditText editTextRange = findViewById(R.id.editTextRange);
            editTextRange.setText(modSpell.getRange());
            editTextDC.setText(modSpell.getDc());
            editTextDuration.setText(modSpell.getDuration());
            editTextDescription.setText(modSpell.getDescription());
            editTextMana.setText(modSpell.getManaCost());
            editTextName.setText(modSpell.getName());
            textInputLayoutRank.getEditText().setText(String.valueOf(modSpell.getRank()));
            textInputLayoutAction.getEditText().setText(modSpell.getActionCost().toString());
            textInputLayoutSpellCat.getEditText().setText(modSpell.getSpellCat().toString());
        }


//        Setup for drop down menus
//        Get String arrays from resources
        String[] ranks = getResources().getStringArray(R.array.ranksDropDownNumber);
        String[] actions = getResources().getStringArray(R.array.actionDropDown);
        String[] spellCats = Arrays.copyOfRange(getResources().getStringArray(R.array.spellCatDropDown),1,5);
//        Define Array adapter with String arrays
        ArrayAdapter arrayRanksAdapter = new ArrayAdapter(this,R.layout.dropdown_item,ranks);
        ArrayAdapter arraySpellCatAdapter = new ArrayAdapter(this,R.layout.dropdown_item,spellCats);
        ArrayAdapter arrayActionAdapter = new ArrayAdapter(this,R.layout.dropdown_item,actions);
        //        Find AutoCompleteTextViews
        AutoCompleteTextView autoCompleteTextViewRank = findViewById(R.id.autoCompleteTextViewRank);
        AutoCompleteTextView autoCompleteTextViewSpellCat = findViewById(R.id.autoCompleteTextViewSpellCat);
        AutoCompleteTextView autoCompleteTextViewAction = findViewById(R.id.autoCompleteTextViewAction);
//        Set adapters
        autoCompleteTextViewRank.setAdapter(arrayRanksAdapter);
        autoCompleteTextViewSpellCat.setAdapter(arraySpellCatAdapter);
        autoCompleteTextViewAction.setAdapter(arrayActionAdapter);
//        Find TextInput Layouts to obtain texts
    }

    /**
     * Button method to finish Spell creation
     * @param view
     */
    public void confirmSpell(View view){
//        Find all the input texts
        TextInputLayout textInputLayoutRank = findViewById(R.id.textInputLayoutRank);
        TextInputLayout textInputLayoutSpellCat = findViewById(R.id.textInputLayoutSpellCat);
        TextInputLayout textInputLayoutAction = findViewById(R.id.textInputLayoutAction);
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        EditText editTextDC = findViewById(R.id.editTextDC);
        EditText editTextDuration = findViewById(R.id.editTextDuration);
        EditText editTextMana = findViewById(R.id.editTextMana);
        EditText editTextRange = findViewById(R.id.editTextRange);

        String spellName = editTextName.getText().toString();
        String spellDescription = editTextDescription.getText().toString();
        String spellDC = editTextDC.getText().toString();
        String spellDuration = editTextDuration.getText().toString();
        String spellMana = editTextMana.getText().toString();
        String spellRange = editTextRange.getText().toString();
        int spellRank = Integer.valueOf(textInputLayoutRank.getEditText().getText().toString());
        SpellCat spellCat = SelectSpellListActivity.spellCatSelectionToSpellCat(textInputLayoutSpellCat.getEditText().getText().toString());
        Action spellAction = actionSelectionToAction(textInputLayoutAction.getEditText().getText().toString());

        Spell newSpell = new Spell(spellName, spellDescription, spellRank, spellMana, spellDC,
                spellAction, spellRange, spellDuration, spellCat);
        if (mode.equals("NewSpell")) {
//            Check if Spell with the same name already exists
            if (MainActivity.fileExists(getApplicationContext(), "spell_list")) {
                List<Spell> spells;
                try {
                    FileInputStream fis = getApplicationContext().openFileInput("spell_list");
                    ObjectInputStream is = new ObjectInputStream(fis);
                    spells = (List<Spell>) is.readObject();
                    is.close();
                    fis.close();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (spells.contains(newSpell)) {
                    Toast.makeText(this, getResources().getString(R.string.spellExistsToast),
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        Bundle extra = new Bundle();
        extra.putSerializable("NewSpell", (Serializable) newSpell);
        Intent intent = new Intent();
        intent.putExtra("NewSpell", extra);
        setResult(Activity.RESULT_OK,intent);
        finish();

    }

    /**
     * Method to convert the Selection of the drop down menu to an Action enum
     * @param actionSelection String to be converted
     * @return Action according to selection
     */
    public static Action actionSelectionToAction(String actionSelection){
        String[] options = MainActivity.resources.getStringArray(R.array.actionDropDown);
        if (actionSelection.equals(options[0])) {
            return Action.STANDARDACTION;
        } else if (actionSelection.equals(options[1])) {
            return Action.BONUSACTION;
        } else if (actionSelection.equals(options[2])) {
            return Action.FREEACTION;
        } else {
            return Action.FULLACTION;
        }
    }

    /**
     * Method to convert the Selection of the drop down menu to an integer, number version
     * @param rankSelection String to be converted
     * @return Integer value of String
     */
    public static int rankSelectionToInt(String rankSelection) {
        String[] options = MainActivity.resources.getStringArray(R.array.ranksDropDownNumber);
        if (rankSelection.equals(options[0])) {
            return 1;
        } else if (rankSelection.equals(options[1])) {
            return 2;
        } else {
            return 3;
        }
    }
}
