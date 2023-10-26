package com.example.spellviewer;

import java.io.Serializable;

public class Spell implements Comparable, Serializable {
    private final String name;
    private final String description;
    private final int rank;
    private final String manaCost;
    private final String dc;
    private final Action actionCost;
    private final String range;
    private final String duration;
    private final SpellCat spellcat;

    public Spell(String name, String description, int rank, String manaCost, String dc, Action actionCost, String range, String duration, SpellCat spellcat){
        this.name = name;
        this.description = description;
        this.rank = rank;
        this.manaCost = manaCost;
        this.dc = dc;
        this.actionCost = actionCost;
        this.range = range;
        this.duration = duration;
        this.spellcat = spellcat;
    }

    public String getDc() {
        return dc;
    }

    public String getManaCost() {
        return manaCost;
    }

    public Action getActionCost() {
        return actionCost;
    }

    public int getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getRange() {
        return range;
    }
    public SpellCat getSpellCat() {
        return spellcat;
    }

    @Override
    public int compareTo(Object o) {
        int tempComp;
        if (o instanceof Spell){
            tempComp = this.spellcat.compareTo(((Spell) o).getSpellCat());
            if (tempComp == 0) {
                if (this.rank < ((Spell) o).getRank()) {
                    return -1;
                } else if (this.rank > ((Spell) o).getRank()) {
                    return 1;
                } else {
                    return this.name.compareTo(((Spell) o).getName());
                }
            } else {
                return tempComp;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Spell) {
            if (this.name.equals(((Spell) o).getName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
