package com.example.spellviewer;



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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
    private List<CharacterImage> characters;
    private ListAdapter listAdapter;
    private final String characterFileName = "character_list";
    private ActivityResultLauncher<Intent> selectImageLauncher;

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
                characters = (List<CharacterImage>) is.readObject();
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
            intent.putExtra("characterName",characters.get(position).getName());
            MainActivity.this.startActivity(intent);
        });

//        Set up launcher for image selection
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        String tempFileName = result.getData().getStringExtra("result");
                        try {
                            FileInputStream fis = getApplicationContext().openFileInput(tempFileName);
                            ObjectInputStream is = new ObjectInputStream(fis);
                            characters.add((CharacterImage) is.readObject());
                            is.close();
                            fis.close();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        deleteFile(tempFileName);
                        updateCharacterListView();
                        writeCharactersToFile();
                    }
                });

    }



    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        return file != null && file.exists();
    }

    public void newCharacter(View view) {
        Intent intent = new Intent(this, ImageSelectionActivity.class);
        selectImageLauncher.launch(intent);
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
            String name = characters.get(id).getName();
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
