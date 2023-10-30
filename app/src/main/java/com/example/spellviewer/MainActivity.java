package com.example.spellviewer;



import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Resources resources;
    private List<String> characters;
    private ListAdapter listAdapter;
    private final String characterFileName = "character_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        resources = getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = findViewById(R.id.toolbarTextView);
        title.setText(R.string.app_name);
        if (MainActivity.fileExists(getApplicationContext(),characterFileName)) {
            try {
                FileInputStream fis = getApplicationContext().openFileInput(characterFileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                characters = (List<String>) is.readObject();
                is.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            characters = new ArrayList<>();
        }

        ListView characterListView = findViewById(R.id.listView);
        listAdapter = new ListAdapter(this, characters);
        characterListView.setAdapter(listAdapter);

        registerForContextMenu(characterListView);

        characterListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, CharacterActivity.class);
            intent.putExtra("characterName",characters.get(position));
            MainActivity.this.startActivity(intent);
        });
    }



    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public void newCharacter(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.characterName));

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createCharacter(input.getText().toString());
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void createCharacter(String name) {
//        if (MainActivity.fileExists(getApplicationContext(),name)) {
        if (characters.contains(name)) {
            Toast.makeText(this, getString(R.string.characterExists),
                    Toast.LENGTH_LONG).show();
        } else {
            characters.add(name);
            listAdapter.updateData();
            writeCharactersToFile();
        }
    }
    private void writeCharactersToFile() {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(characterFileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(characters);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, resources.getString(R.string.delete));
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == resources.getString(R.string.delete)) {
//            get the selected position
            int id = info.position;
//            Get character name to remove its data if it exists
            String name = characters.get(id);
            deleteFile(name);
//            remove selected character
            characters.remove(id);
//            Notify adapter that data has changed
            updateCharacterListView();
            writeCharactersToFile();
        }
        return true;
    }

    private void updateCharacterListView(){
        listAdapter.updateData();
    }

}
