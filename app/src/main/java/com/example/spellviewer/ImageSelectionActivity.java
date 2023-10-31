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


public class ImageSelectionActivity extends AppCompatActivity {
    private List<CheckImageView> imageViews;
    private CheckImageView selectedImageView;
    private SerialBitmap imageFromGallery;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        TableLayout tableLayout = findViewById(R.id.simpleTableLayout);
        imageViews = new ArrayList<>();
        selectedImageView = null;
        View.OnClickListener onClickListener = v -> imageViewSelected((CheckImageView) v);
//        Save all imageViews in a List
        for (int i = 0, tableChildCount = tableLayout.getChildCount(); i < tableChildCount; i++) {
            View rowView = tableLayout.getChildAt(i);
            if (rowView instanceof TableRow) {
                for (int j = 0, columnCount = ((TableRow) rowView).getChildCount(); j < columnCount; j++) {
                    View imageView = ((TableRow) rowView).getChildAt(j);
                    if (imageView instanceof CheckImageView) {
                        imageViews.add((CheckImageView) imageView);
                        imageView.setOnClickListener(onClickListener);
                    }
                }
            }
        }
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
//                        Matrix matrix = new Matrix();
//                        matrix.postRotate(90);
//                        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int displayWidth = displayMetrics.widthPixels;
                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();
                        imageFromGallery = new SerialBitmap(getResizedBitmap(bitmap,displayWidth*imageWidth/imageHeight,displayWidth));
                    }
                });

    }

    private void imageViewSelected(CheckImageView imageView){
        if (selectedImageView == imageView) {
            imageView.toggle();
            selectedImageView = null;
            return;
        }
        if (selectedImageView != null) {
            selectedImageView.toggle();
        }
        imageView.toggle();
        selectedImageView = imageView;
    }
    public void confirmImage(View view) {
        EditText editText = findViewById(R.id.editTextText);
        String name = String.valueOf(editText.getText());
        CharacterImage characterImage;
        if (imageFromGallery != null) {
            characterImage = new CharacterImage(name, imageFromGallery);
        } else {
            Bitmap image;
            if (selectedImageView == null) {
                image = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.mysterion_nobg);
            } else {
                image = drawableToBitmap(selectedImageView.getDrawable());
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            SerialBitmap serialImage = new SerialBitmap(getResizedBitmap(image,displayMetrics.widthPixels,displayMetrics.widthPixels));
            characterImage = new CharacterImage(name,serialImage);
        }
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("tempFile", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(characterImage);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Intent intent = new Intent();
        intent.putExtra("result", "tempFile");
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
    public void openGallery(View view) {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());

    }

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

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
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
