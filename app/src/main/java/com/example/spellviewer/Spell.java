package com.example.spellviewer;

public class Spell {
    private final String name;
    private final String description;
    private final int rank;
    private final int manaCost;
    private final String dc;
    private final Action actionCost;
    private final String range;
    private final String duration;

    public Spell(String name, String description, int rank, int manaCost, String dc, Action actionCost, String range, String duration) {
        this.name = name;
        this.description = description;
        this.rank = rank;
        this.manaCost = manaCost;
        this.dc = dc;
        this.actionCost = actionCost;
        this.range = range;
        this.duration = duration;
    }

    public String getDc() {
        return dc;
    }

    public int getManaCost() {
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

}
