package com.example.spellviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class SpellListItem extends LinearLayout implements Checkable {

    private boolean checked = false;
//    private ImageView icon;
    public SpellListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SpellListItem(Context context) {
        super(context);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        icon = findViewById(R.id.checkboxView); // optimisation - you don't need to search for image view every time you want to reference it
//        OnClickListener clickListener = new OnClickListener() {
//            public void onClick(View v) {
////                if (v.equals(icon)) {
//                    toggle();
////                }
//            }
//        };
//        icon.setOnClickListener(clickListener);
    }
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
//        if (icon.getVisibility() == View.VISIBLE) {
//            icon.setImageResource((checked) ? R.drawable.btn_check_on : R.drawable.btn_check_off);
//        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
