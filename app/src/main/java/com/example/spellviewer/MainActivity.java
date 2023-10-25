package com.example.spellviewer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Resources resources;
//    private Button goToSpellsButton = (Button) findViewById(R.id.goToSpellsButton);
    List<Spell> spells;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resources = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        goToSpellsButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                startActivity(new Intent(MainActivity.this, MainActivity2.class));
//            }
//        });
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        spells = ExpandableListData.getData();
        expandableListAdapter = new com.example.spellviewer.ExpandableListAdapter(this, spells);
        expandableListView.setAdapter(expandableListAdapter);

    }

//    public void goToSpellList(View view) {
//        startActivity(new Intent(this, MainActivity2.class));
//    }
}
