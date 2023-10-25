package com.example.spellviewer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resources = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToSpellList(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }
}
