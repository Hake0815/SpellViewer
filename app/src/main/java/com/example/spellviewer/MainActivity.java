package com.example.spellviewer;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
        List<Spell> newSpells = gerald.getSpells();

        expandableListAdapter.addNewSpells(newSpells);
    }

    public void goToSpellList(View view) {
        Intent intent = new Intent(this, SelectSpellListActivity.class);
        selectSpellListLauncher.launch(intent);
    }
}
