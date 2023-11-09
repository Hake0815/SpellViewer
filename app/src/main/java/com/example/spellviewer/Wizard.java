package com.example.spellviewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializable class to store the name of a character together with a List of spells
 */
public class Wizard implements Serializable {
    private final String name;
    private List<Spell> spells;

    /**
     * Constructor sets name of character and initializes the spell list to an empty ArrayList
     * @param name Name of the new Character
     */
    public Wizard(String name) {
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

    /**
     * Remove a spell from spell list
     * @param i index of spell to be removed
     */
    public void removeSpell(int i){
        spells.remove(i);
    }
}
