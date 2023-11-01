package com.example.spellviewer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
/**
 * Extension to ImageView. CheckImageView can be selected with public void setSelected(boolean checked).
 * If a CheckImageView is selected the background color is changed. This is only visible if padding
 * is set greater than 0.
 */
@SuppressLint("AppCompatCustomView")
public class CheckImageView extends ImageView {

    private boolean isChecked;
    public CheckImageView(Context context) {
        super(context);
    }

    public CheckImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSelected(boolean checked) {
        isChecked = checked;
        if (isChecked) {
            this.setBackgroundColor(MainActivity.resources.getColor(R.color.light_purple,null));
        } else {
            this.setBackgroundColor(MainActivity.resources.getColor(R.color.background,null));
        }
    }
    public void toggle() {
        setSelected(!isChecked);
    }

    public boolean getSelected(){
        return isChecked;
    }

}
