package com.example.spellviewer;

import java.io.Serializable;

public class CharacterImage implements Serializable {
    private String name;
    private SerialBitmap image;

    public CharacterImage(String name, SerialBitmap image) {
        this.name = name;
        this.image = image;
    }
    public CharacterImage(String name) {
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
        if (o instanceof CharacterImage) {
            if (((CharacterImage)o).getName().equals(name)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
