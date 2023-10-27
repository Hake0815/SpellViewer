package com.example.spellviewer;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Resources resources;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private Character gerald;
    private ActivityResultLauncher<Intent> selectSpellListLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resources = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gerald = new Character("Gerald");
        expandableListView = findViewById(R.id.expandableListView);
        expandableListAdapter = new ExpandableListAdapter(this, gerald.getSpells());
        expandableListView.setAdapter(expandableListAdapter);
        registerForContextMenu(expandableListView);


        selectSpellListLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        List<Spell> spells;
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Bundle extra = result.getData().getBundleExtra("Spells");
                            spells = (ArrayList<Spell>) extra.getSerializable("Spells");
                            for (Spell spell : spells){
                                if (!gerald.getSpells().contains(spell)){
                                    gerald.addSpell(spell);
                                }
                            }
                            Collections.sort(gerald.getSpells());
                        }

                        updateSpellListView();
                    }
                });

    }

    private void updateSpellListView() {
        expandableListAdapter.updateData();
    }

    public void goToSpellList(View view) {
        Intent intent = new Intent(this, SelectSpellListActivity.class);
        selectSpellListLauncher.launch(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, resources.getString(R.string.delete));

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
        if (item.getTitle() == resources.getString(R.string.delete)) {
//            get the selected group position
            int id = ExpandableListView.getPackedPositionGroup(info.packedPosition);
//            remove selected spell from characters spells
            gerald.removeSpell(id);
//            Notify adapter that data has changed
            expandableListAdapter.updateData();
        }
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
        String fileName = gerald.getName();
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(gerald);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        String fileName = gerald.getName();
        try {
            FileInputStream fis = getApplicationContext().openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            gerald = (Character) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        expandableListAdapter.updateSpellList(gerald.getSpells());
    }
}
