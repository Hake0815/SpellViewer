package com.example.spellviewer;

import java.io.Serializable;
/**
 * Serializable class to store character name together with an image.
 */
public class WizardImage implements Serializable {
    private String name;
    private SerialBitmap image;

    public WizardImage(String name, SerialBitmap image) {
        this.name = name;
        this.image = image;
    }
    public WizardImage(String name) {
        this.name = name;
//        this.image = ResourcesCompat.getDrawable(MainActivity.resources,R.drawable.baseline_question_mark_24, null);
    }

    public SerialBitmap getImage() {
        return image;
    }

    public void setImage(SerialBitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
//        Two character are considered equal if they have the same name.
        if (o instanceof WizardImage) {
            if (((WizardImage)o).getName().equals(name)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
