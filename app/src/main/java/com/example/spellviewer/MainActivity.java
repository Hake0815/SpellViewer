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

/**
 * Activity Class for the main activity
 */
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
//        Set toolbar title to app name
        TextView title = findViewById(R.id.toolbarTextView);
        title.setText(R.string.app_name);
//        Read in character list from file if it exists or create new list
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
//        Feed ListView with character list
        ListView characterListView = findViewById(R.id.listView);
        listAdapter = new ListAdapter(this, characters);
        characterListView.setAdapter(listAdapter);

        registerForContextMenu(characterListView);
//        If Listview Item is clicked the Character Activity is launched for the clicked character
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
//                        read character info from file
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
//                        delete the temporary file holding character info
                        deleteFile(tempFileName);
                        updateCharacterListView();
//                        Save character list including new character to file
                        writeCharactersToFile();
                    }
                });

    }


    /**
     * Static Method to check if a file exists in the files directory of the app
     * @param context Application context
     * @param filename Name of the file to be checked
     * @return True if file exists, false if it does not
     */
    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        return file != null && file.exists();
    }

    /**
     * Button method to launch the activity to create a new character
     * @param view View that calls the method
     */
    public void newCharacter(View view) {
        Intent intent = new Intent(this, ImageSelectionActivity.class);
        selectImageLauncher.launch(intent);
    }

    /**
     * Write the characters in the local characters List variable to file
     */
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

    /**
     * Method to notify the ListViewAdapter when the character List changes
     */
    private void updateCharacterListView(){
        listAdapter.updateData();
    }

}
