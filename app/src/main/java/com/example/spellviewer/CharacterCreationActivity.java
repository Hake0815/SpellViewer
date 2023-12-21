package com.example.spellviewer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Activity Class to Select the image for a new character
 */
public class CharacterCreationActivity extends AppCompatActivity {
    private SerialBitmap imageFromGallery;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private String characterFileName;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Get file name with character list
        characterFileName = MainActivity.characterFileName;
        setContentView(R.layout.activity_character_creation);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(this);
        viewPager2.setAdapter(imageViewPagerAdapter);
        viewPager2.setUserInputEnabled(false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ImageFragment imageFragment = (ImageFragment) getSupportFragmentManager().findFragmentByTag("f" + viewPager2.getCurrentItem());
                imageFragment.unselectImageView();
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
//        Launcher to open the Gallery
        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Bitmap bitmap;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
//                        The selected Image is scaled down to display size
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int displayWidth = Math.min(displayMetrics.widthPixels,500);
                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();
                        imageFromGallery = new SerialBitmap(getResizedBitmap(bitmap,displayWidth,Math.round((float) imageHeight/imageWidth*displayWidth)));
                    }
                });

    }

    /**
     * Method called after Button press. Method that finishes activity and returns a result.
     * The selected Image and name is saved and passed to previous activity.
     * @param view View that called the method.
     */
    public void confirmImage(View view) {
//        Get the character name
        EditText editText = findViewById(R.id.editTextText);
        String name = String.valueOf(editText.getText());
        if (name.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.noNameEntered),
                    Toast.LENGTH_LONG).show();
            return;
        }
        WizardImage wizardImage;
        if (imageFromGallery != null) {
//            If an image was selected from gallery use it as image result
            wizardImage = new WizardImage(name, imageFromGallery);
        } else {
//            Get the selected image
            ImageFragment imageFragment = (ImageFragment) getSupportFragmentManager().findFragmentByTag("f" + viewPager2.getCurrentItem());
            SerialBitmap imageSelection = imageFragment.getSelectedImage();
            if (imageSelection == null) {
                imageSelection = new SerialBitmap(BitmapFactory.decodeResource(MainActivity.resources, R.drawable.mysterion_nobg_small));
            }
////            Resize the image to device size, note all images are squares
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            SerialBitmap serialImage = new SerialBitmap(getResizedBitmap(image, displayMetrics.widthPixels, displayMetrics.widthPixels));
//            wizardImage = new WizardImage(name, serialImage);
            wizardImage = new WizardImage(name, imageSelection);
        }
//        Check if Character exists already
        List<WizardImage> characters;
        if (MainActivity.fileExists(getApplicationContext(), characterFileName)) {
            try {
                FileInputStream fis = getApplicationContext().openFileInput(characterFileName);
                ObjectInputStream is = new ObjectInputStream(fis);
                characters = (List<WizardImage>) is.readObject();
                is.close();
                fis.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (characters.contains(wizardImage)) {
                Toast.makeText(this, getResources().getString(R.string.characterExists),
                        Toast.LENGTH_LONG).show();
                return;
            }
        }
//        Pass image and name of new Wizard as result.
        Bundle extra = new Bundle();
        Intent intent = new Intent();
        extra.putSerializable("WizardImage", wizardImage.getImage());
        intent.putExtra("WizardImage", extra);
        intent.putExtra("WizardName", wizardImage.getName());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Button click method to open the gallery
     * @param view View that called the method
     */
    public void openGallery(View view) {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());

    }

    /**
     * Static method to convert a drawable to Bitmap
     * copypasta from https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
     * @param drawable Drawable to be converted
     * @return Bitmap of the drawable
     */
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Static method to resize a Bitmapo
     * copypast from https://stackoverflow.com/questions/4837715/how-to-resize-a-bitmap-in-android
     * @param bm Bitmap to be resized
     * @param newWidth New width of the new Bitmap
     * @param newHeight New height of the new Bitmap
     * @return Resized Bitmap
     */
    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}
