package com.example.spellviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ImageSelectionActivity extends AppCompatActivity {
    private List<CheckImageView> imageViews;
    private CheckImageView selectedImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.simpleTableLayout);
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
        EditText editText = (EditText) findViewById(R.id.editTextText);
        String name = String.valueOf(editText.getText());
        Bitmap image;
        Intent intent = new Intent();
        if (selectedImageView == null) {
            image = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.mysterion_nobg);
        } else {
            image = drawableToBitmap(selectedImageView.getDrawable());
        }
        SerialBitmap serialImage = new SerialBitmap(image);
        CharacterImage characterImage = new CharacterImage(name,serialImage);
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("tempFile", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(characterImage);
            os.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        intent.putExtra("result", "tempFile");
        setResult(Activity.RESULT_OK,intent);
        finish();
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

}
