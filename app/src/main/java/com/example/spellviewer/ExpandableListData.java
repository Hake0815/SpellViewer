package com.example.spellviewer;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 22-Mar-18.
 */

public class ExpandableListData {
    public static List<Spell> getData() {
        List<Spell> spells = new ArrayList<>();
        Spell panic = new Spell(MainActivity.resources.getString(R.string.panic_name), MainActivity.resources.getString(R.string.panic_description), 1, 2,
                MainActivity.resources.getString(R.string.panic_dc), Action.FULLACTION, MainActivity.resources.getString(R.string.panic_range), MainActivity.resources.getString(R.string.panic_duration));
        spells.add(panic);
        return spells;
    }
}