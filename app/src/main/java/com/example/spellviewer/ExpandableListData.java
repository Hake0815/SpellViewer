package com.example.spellviewer;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 22-Mar-18.
 */

public class ExpandableListData {
    public static List<Spell> getData() {
        List<Spell> spells = new ArrayList<>();
        Spell panic = new Spell(MainActivity.resources.getString(R.string.panic_name),
                MainActivity.resources.getString(R.string.panic_description), 1,
                MainActivity.resources.getString(R.string.panic_mana),
                MainActivity.resources.getString(R.string.panic_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.panic_range),
                MainActivity.resources.getString(R.string.panic_duration), SpellCat.Enchantment);
        spells.add(panic);
        Spell calmAnimal = new Spell(MainActivity.resources.getString(R.string.calmAnimal_name),
                MainActivity.resources.getString(R.string.calmAnimal_description), 1,
                MainActivity.resources.getString(R.string.calmAnimal_mana),
                MainActivity.resources.getString(R.string.calmAnimal_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.calmAnimal_range),
                MainActivity.resources.getString(R.string.calmAnimal_duration),
                SpellCat.Enchantment);
        spells.add(calmAnimal);
        Spell darkVision = new Spell(MainActivity.resources.getString(R.string.darkVision_name),
                MainActivity.resources.getString(R.string.darkVision_description), 1,
                MainActivity.resources.getString(R.string.darkVision_mana),
                MainActivity.resources.getString(R.string.darkVision_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.darkVision_range),
                MainActivity.resources.getString(R.string.darkVision_duration),
                SpellCat.Enchantment);
        spells.add(darkVision);
        return spells;
    }
}