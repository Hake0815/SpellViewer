package com.example.spellviewer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Resources resources;
    List<Spell> spells;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resources = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        spells = ExpandableListData.getData();
        expandableListAdapter = new com.example.spellviewer.ExpandableListAdapter(this, spells);
        expandableListView.setAdapter(expandableListAdapter);

    }

}
