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

public class CharacterActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private Character character;
    private ActivityResultLauncher<Intent> selectSpellListLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Intent intent = getIntent();
        String characterName = intent.getStringExtra("characterName");
        if (MainActivity.fileExists(getApplicationContext(), characterName)) {
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
            character = new Character(characterName);
        }
        List<CharacterImage> characters;
        try {
            FileInputStream fis = getApplicationContext().openFileInput("character_list");
            ObjectInputStream is = new ObjectInputStream(fis);
            characters = ((List<CharacterImage>) is.readObject());
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        SerialBitmap avatar = characters.get(characters.indexOf(new CharacterImage(characterName))).getImage();
        ImageView imageView = findViewById(R.id.imageViewAvatar);
        imageView.setImageBitmap(avatar.bitmap);
        TextView title = findViewById(R.id.toolbarTextView);
        title.setText(character.getName());
        expandableListView = findViewById(R.id.expandableListView);
        expandableListAdapter = new ExpandableListAdapter(this, character.getSpells());
        expandableListView.setAdapter(expandableListAdapter);
        registerForContextMenu(expandableListView);


        selectSpellListLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    List<Spell> spells;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Bundle extra = result.getData().getBundleExtra("Spells");
                        spells = (ArrayList<Spell>) extra.getSerializable("Spells");
                        for (Spell spell : spells) {
                            if (!character.getSpells().contains(spell)) {
                                character.addSpell(spell);
                            }
                        }
                        Collections.sort(character.getSpells());
                    }
                    updateSpellListView();
                });

    }

    private void updateSpellListView() {
        expandableListAdapter.filterData(0,null);
    }

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
        writeCharacterToFile();
        super.onDestroy();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceStae){
        writeCharacterToFile();
        super.onSaveInstanceState(savedInstanceStae);
    }

    private void writeCharacterToFile() {
        String fileName = character.getName();
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(character);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
