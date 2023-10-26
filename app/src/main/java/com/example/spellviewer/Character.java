package com.example.spellviewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Character implements Serializable {
    private final String name;
    private List<Spell> spells;

    public Character(String name) {
        this.name = name;
        this.spells = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public void addSpell (Spell spell) {
        spells.add(spell);
    }
}
