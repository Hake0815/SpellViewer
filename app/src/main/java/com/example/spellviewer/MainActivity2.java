package com.example.spellviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    List<Spell> spells;

    ExpandableListView expandableListView;
    android.widget.ExpandableListAdapter expandableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        spells = ExpandableListData.getData();
        expandableListAdapter = new com.example.spellviewer.ExpandableListAdapter(this, spells);
        expandableListView.setAdapter(expandableListAdapter);

    }
}