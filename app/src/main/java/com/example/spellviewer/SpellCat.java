package com.example.spellviewer;

public enum SpellCat {
    Enchantment,
    Summoning,
    Destruction,
    Alteration;

    @Override
    public String toString() {
        switch (this) {
            case Enchantment:
                return MainActivity.resources.getString(R.string.enchantment);
            case Summoning:
                return MainActivity.resources.getString(R.string.summoning);
            case Destruction:
                return MainActivity.resources.getString(R.string.destruction);
            case Alteration:
                return MainActivity.resources.getString(R.string.alteration);
            default:
                return null;
        }
    }
}
