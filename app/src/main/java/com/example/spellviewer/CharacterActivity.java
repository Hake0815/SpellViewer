package com.example.spellviewer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Activity Class that will show the name, image and spell list of a character. The name of the
 * character has to be passed in the intent it is launched with.
 */
public class CharacterActivity extends AppCompatActivity {
    private ExpandableListAdapter expandableListAdapter;
    private Character character;
    private ActivityResultLauncher<Intent> selectSpellListLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
//        get character name from intent
        Intent intent = getIntent();
        String characterName = intent.getStringExtra("characterName");
//        Look for existing character data
        if (MainActivity.fileExists(getApplicationContext(), characterName)) {
//            Read character data from file
            try {
                FileInputStream fis = getApplicationContext().openFileInput(characterName);
                ObjectInputStream is = new ObjectInputStream(fis);
                character = (Character) is.readObject();
                is.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
//            If no character data exists, it is a new character
            character = new Character(characterName);
        }
//        Read character list file to obtain the image
        List<CharacterImage> characters;
        try {
            FileInputStream fis = getApplicationContext().openFileInput("character_list");
            ObjectInputStream is = new ObjectInputStream(fis);
            characters = (List<CharacterImage>) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        Read image and display it in ImageView
        SerialBitmap avatar = characters.get(characters.indexOf(new CharacterImage(characterName))).getImage();
        ImageView imageView = findViewById(R.id.imageViewAvatar);
        imageView.setImageBitmap(avatar.bitmap);
//        The toolbar title is set to the character name
        TextView title = findViewById(R.id.toolbarTextView);
        title.setText(character.getName());
//        ExpandableListView is fed with the spell list of the character
        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        expandableListAdapter = new ExpandableListAdapter(this, character.getSpells());
        expandableListView.setAdapter(expandableListAdapter);
//        Long clicks on Items in the ELV open context Menu
        registerForContextMenu(expandableListView);
//        Setup of the Launcher for the Activity that displays all spells to select from it
        selectSpellListLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    List<Spell> spells;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // The selected spells are obtained here
                        assert result.getData() != null;
                        Bundle extra = result.getData().getBundleExtra("Spells");
                        assert extra != null;
                        spells = (ArrayList<Spell>) extra.getSerializable("Spells");
//                        Add passed spells to the character but only if the character does not know them already
                        for (Spell spell : spells) {
                            if (!character.getSpells().contains(spell)) {
                                character.addSpell(spell);
                            }
                        }
//                        Sort the list of character spells
                        Collections.sort(character.getSpells());
                    }
//                    Notify the ELVAdapter that the data has changed.
                    updateSpellListView();
                });

    }
    /**
     * Method to notify ELVAdpapter that data has changed by calling the filter to display everything.
     * If filters are in place, this has to be changed.
     */
    private void updateSpellListView() {
        expandableListAdapter.filterData(0,null);
    }

    /**
     * Method is called after button click and launches the activity with all spells to select from.
     * @param view View from with the method was called
     */
    public void goToSpellList(View view) {
        Intent intent = new Intent(this, SelectSpellListActivity.class);
        selectSpellListLauncher.launch(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, getResources().getString(R.string.delete));

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
//        If the delete option is selected the respective item is removed from the characters spell list
//        and also from the ELV
        if (item.getTitle() == getResources().getString(R.string.delete)) {
//            get the selected group position
            int id = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//            remove selected spell from characters spells
            character.removeSpell(id);
//            Notify adapter that data has changed
            updateSpellListView();
        }
        return true;
    }

    @Override
    public void onDestroy(){
//        The character is saved to file if activity is destroyed
        writeCharacterToFile();
        super.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceStae){
//        The character is saved to file if activity is killed by OS.
        writeCharacterToFile();
        super.onSaveInstanceState(savedInstanceStae);
    }
    /**
     * Method to write the character data to a file with the character name as file name
     */
    private void writeCharacterToFile() {
        String fileName = character.getName();
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(character);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
