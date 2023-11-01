package com.example.spellviewer;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity Class to Select the image for a new character
 */
public class ImageSelectionActivity extends AppCompatActivity {
    private CheckImageView selectedImageView;
    private SerialBitmap imageFromGallery;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        TableLayout tableLayout = findViewById(R.id.simpleTableLayout);
        selectedImageView = null;
        View.OnClickListener onClickListener = v -> imageViewSelected((CheckImageView) v);
//        Set the OnClickListener for all ImageViews
        for (int i = 0, tableChildCount = tableLayout.getChildCount(); i < tableChildCount; i++) {
            View rowView = tableLayout.getChildAt(i);
            if (rowView instanceof TableRow) {
                for (int j = 0, columnCount = ((TableRow) rowView).getChildCount(); j < columnCount; j++) {
                    View imageView = ((TableRow) rowView).getChildAt(j);
                    if (imageView instanceof CheckImageView) {
                        imageView.setOnClickListener(onClickListener);
                    }
                }
            }
        }
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
                        int displayWidth = displayMetrics.widthPixels;
                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();
                        imageFromGallery = new SerialBitmap(getResizedBitmap(bitmap,displayWidth,imageHeight/imageWidth*displayWidth));
                    }
                });

    }

    /**
     * Method to toggle the selected status of an CheckedImageView, while unselect any previously
     * selected image view
     * @param imageView CheckImageView to be toggled
     */
    private void imageViewSelected(CheckImageView imageView){
        if (selectedImageView == imageView) {
//            The selected ImageView is clicked again, and hence it is set to unselected.
            imageView.toggle();
            selectedImageView = null;
            return;
        }
        if (selectedImageView != null) {
//            If a different image was selected previously it is unselected here
            selectedImageView.toggle();
        }
//        Select the image and save selection.
        imageView.toggle();
        selectedImageView = imageView;
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
        CharacterImage characterImage;
        if (imageFromGallery != null) {
//            If an image was selected from gallery use it as image result
            characterImage = new CharacterImage(name, imageFromGallery);
        } else {
//            Get the slected image
            Bitmap image;
            if (selectedImageView == null) {
                image = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.mysterion_nobg);
            } else {
                image = drawableToBitmap(selectedImageView.getDrawable());
            }
//            Resize the image to device size, note all images are squares
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            SerialBitmap serialImage = new SerialBitmap(getResizedBitmap(image,displayMetrics.widthPixels,displayMetrics.widthPixels));
            characterImage = new CharacterImage(name,serialImage);
        }
//        Write the result as character to file
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("tempFile", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(characterImage);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Pass the file name as result to previous activity.
        Intent intent = new Intent();
        intent.putExtra("result", "tempFile");
        setResult(Activity.RESULT_OK,intent);
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
