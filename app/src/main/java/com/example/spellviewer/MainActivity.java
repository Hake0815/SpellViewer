package com.example.spellviewer;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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
    private List<WizardImage> wizards;
    private ListAdapter listAdapter;
    public static final String characterFileName = "character_list";
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resources = this.getResources();
        setContentView(R.layout.activity_main);
//        Set toolbar title to app name
        TextView title = findViewById(R.id.toolbarTextView);
        title.setText(R.string.app_name);

//        Read in character list from file if it exists or create new list
        if (MainActivity.fileExists(getApplicationContext(),characterFileName)) {
            List list;
            try {
                FileInputStream fis = getApplicationContext().openFileInput(characterFileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                list = (List) is.readObject();
                is.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (list.isEmpty()) {
                deleteFile(characterFileName);
                wizards = new ArrayList<>();
            } else if (list.get(0) instanceof WizardImage) {
                wizards = (ArrayList<WizardImage>) list;
            } else {
                deleteFile(characterFileName);
                wizards = new ArrayList<>();
            }
        } else {
            wizards = new ArrayList<>();
        }

//        Set up navigation window
        setUpNavView();
//        Feed ListView with character list
        ListView characterListView = findViewById(R.id.listView);
        listAdapter = new ListAdapter(this, wizards);
        characterListView.setAdapter(listAdapter);

        registerForContextMenu(characterListView);
//        If Listview Item is clicked the Character Activity is launched for the clicked character
        characterListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, CharacterActivity.class);
            intent.putExtra("characterName", wizards.get(position).getName());
            MainActivity.this.startActivity(intent);
        });

//        Set up launcher for image selection
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Get the Image and name of new Wizard from the result data
                        assert result.getData() != null;
                        Bundle extra = result.getData().getBundleExtra("WizardImage");
                        assert extra != null;
                        SerialBitmap newWizardImage = (SerialBitmap) extra.getSerializable("WizardImage");
                        String newWizardName = result.getData().getStringExtra("WizardName");
                        wizards.add(new WizardImage(newWizardName, newWizardImage));
                        updateCharacterListView();
//                        Save character list including new character to file
                        writeCharactersToFile();
                    }
                });

    }

    /**
     * Setup method for the Navigation view in the MainActivity
     */
    private void setUpNavView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.toolbarImagaView).setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(0).setChecked(true);
    }
    /**
     * Method called from Navigation view item
     * @param item Clicked menu item
     */
    public void openSpellList(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, SelectSpellListActivity.class);
        intent.putExtra("Mode","DisplayOnly");
        MainActivity.this.startActivity(intent);
    }
    /**
     * Open Link to rule pdf
     * @param item Item that triggered method call
     */
    public void openRules(MenuItem item) {
        Uri uri = Uri.parse("https://github.com/Hake0815/Heldenanleitung/blob/master/Heldenanleitung2.pdf"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    /**
     * Open Link to APK
     * @param item Item that triggered method call
     */
    public void linkAPK(MenuItem item) {
        Uri uri = Uri.parse("https://github.com/Hake0815/SpellViewer/blob/master/app/release/SpellViewer.apk"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void goToMain(MenuItem item) {
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
//        Intent intent = new Intent(this, ImageSelectionActivity.class);
        Intent intent = new Intent(this, CharacterCreationActivity.class);
        selectImageLauncher.launch(intent);
    }


    /**
     * Write the characters in the local characters List variable to file
     */
    private void writeCharactersToFile() {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(characterFileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(wizards);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, getResources().getString(R.string.delete));
    }


    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == getResources().getString(R.string.delete)) {
//            get the selected position
            int id = info.position;
//            Get character name to remove its data if it exists
            String name = wizards.get(id).getName();
            deleteFile(name);
//            remove selected character
            wizards.remove(id);
//            Notify adapter that data has changed
            updateCharacterListView();
            writeCharactersToFile();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
//        On back press app should close navigation menu instead of activity
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Method to notify the ListViewAdapter when the character List changes
     */
    private void updateCharacterListView(){
        listAdapter.updateData();
    }

}
