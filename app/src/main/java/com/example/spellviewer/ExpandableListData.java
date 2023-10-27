package com.example.spellviewer;


import java.util.ArrayList;
import java.util.Collections;
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
                MainActivity.resources.getString(R.string.calmAnimal_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.calmAnimal_range),
                MainActivity.resources.getString(R.string.calmAnimal_duration),
                SpellCat.Enchantment);
        spells.add(calmAnimal);
        Spell darkVision = new Spell(MainActivity.resources.getString(R.string.darkVision_name),
                MainActivity.resources.getString(R.string.darkVision_description), 1,
                MainActivity.resources.getString(R.string.darkVision_mana),
                MainActivity.resources.getString(R.string.darkVision_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.darkVision_range),
                MainActivity.resources.getString(R.string.darkVision_duration),
                SpellCat.Enchantment);
        spells.add(darkVision);

        spells.add(new Spell(MainActivity.resources.getString(R.string.slowFall_name),
                MainActivity.resources.getString(R.string.slowFall_description), 1,
                MainActivity.resources.getString(R.string.slowFall_mana),
                MainActivity.resources.getString(R.string.slowFall_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.slowFall_range),
                MainActivity.resources.getString(R.string.slowFall_duration),
                SpellCat.Enchantment));

        spells.add(new Spell(MainActivity.resources.getString(R.string.animalSpeech_name),
                MainActivity.resources.getString(R.string.animalSpeech_description), 1,
                MainActivity.resources.getString(R.string.animalSpeech_mana),
                MainActivity.resources.getString(R.string.animalSpeech_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.animalSpeech_range),
                MainActivity.resources.getString(R.string.animalSpeech_duration),
                SpellCat.Enchantment));

        spells.add(new Spell(MainActivity.resources.getString(R.string.speakWithDead_name),
                MainActivity.resources.getString(R.string.speakWithDead_description), 1,
                MainActivity.resources.getString(R.string.speakWithDead_mana),
                MainActivity.resources.getString(R.string.speakWithDead_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.speakWithDead_range),
                MainActivity.resources.getString(R.string.speakWithDead_duration),
                SpellCat.Enchantment));

        spells.add(new Spell(MainActivity.resources.getString(R.string.confusion_name),
                MainActivity.resources.getString(R.string.confusion_description), 2,
                MainActivity.resources.getString(R.string.confusion_mana),
                MainActivity.resources.getString(R.string.confusion_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.confusion_range),
                MainActivity.resources.getString(R.string.confusion_duration),
                SpellCat.Enchantment));

        spells.add(new Spell(MainActivity.resources.getString(R.string.boilBlood_name),
                MainActivity.resources.getString(R.string.boilBlood_description), 2,
                MainActivity.resources.getString(R.string.boilBlood_mana),
                MainActivity.resources.getString(R.string.boilBlood_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.boilBlood_range),
                MainActivity.resources.getString(R.string.boilBlood_duration),
                SpellCat.Enchantment));

        spells.add(new Spell(MainActivity.resources.getString(R.string.veiledView_name),
                MainActivity.resources.getString(R.string.veiledView_description), 2,
                MainActivity.resources.getString(R.string.veiledView_mana),
                MainActivity.resources.getString(R.string.veiledView_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.veiledView_range),
                MainActivity.resources.getString(R.string.veiledView_duration),
                SpellCat.Enchantment));

        spells.add(new Spell(MainActivity.resources.getString(R.string.sleep_name),
                MainActivity.resources.getString(R.string.sleep_description), 2,
                MainActivity.resources.getString(R.string.sleep_mana),
                MainActivity.resources.getString(R.string.sleep_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.sleep_range),
                MainActivity.resources.getString(R.string.sleep_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.waterBreathing_name),
                MainActivity.resources.getString(R.string.waterBreathing_description), 2,
                MainActivity.resources.getString(R.string.waterBreathing_mana),
                MainActivity.resources.getString(R.string.waterBreathing_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.waterBreathing_range),
                MainActivity.resources.getString(R.string.waterBreathing_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.farsight_name),
                MainActivity.resources.getString(R.string.farsight_description), 2,
                MainActivity.resources.getString(R.string.farsight_mana),
                MainActivity.resources.getString(R.string.farsight_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.farsight_range),
                MainActivity.resources.getString(R.string.farsight_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.befriend_name),
                MainActivity.resources.getString(R.string.befriend_description), 2,
                MainActivity.resources.getString(R.string.befriend_mana),
                MainActivity.resources.getString(R.string.befriend_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.befriend_range),
                MainActivity.resources.getString(R.string.befriend_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.invisibility_name),
                MainActivity.resources.getString(R.string.invisibility_description), 2,
                MainActivity.resources.getString(R.string.invisibility_mana),
                MainActivity.resources.getString(R.string.invisibility_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.invisibility_range),
                MainActivity.resources.getString(R.string.invisibility_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.disease_name),
                MainActivity.resources.getString(R.string.disease_description), 2,
                MainActivity.resources.getString(R.string.disease_mana),
                MainActivity.resources.getString(R.string.disease_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.disease_range),
                MainActivity.resources.getString(R.string.disease_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.bloodlust_name),
                MainActivity.resources.getString(R.string.bloodlust_description), 3,
                MainActivity.resources.getString(R.string.bloodlust_mana),
                MainActivity.resources.getString(R.string.bloodlust_dc), Action.BONUSACTION,
                MainActivity.resources.getString(R.string.bloodlust_range),
                MainActivity.resources.getString(R.string.bloodlust_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.curse_name),
                MainActivity.resources.getString(R.string.curse_description), 3,
                MainActivity.resources.getString(R.string.curse_mana),
                MainActivity.resources.getString(R.string.curse_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.curse_range),
                MainActivity.resources.getString(R.string.curse_duration),
                SpellCat.Enchantment));
        spells.add(new Spell(MainActivity.resources.getString(R.string.bloodRitual_name),
                MainActivity.resources.getString(R.string.bloodRitual_description), 3,
                MainActivity.resources.getString(R.string.bloodRitual_mana),
                MainActivity.resources.getString(R.string.bloodRitual_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.bloodRitual_range),
                MainActivity.resources.getString(R.string.bloodRitual_duration),
                SpellCat.Enchantment));

        Collections.sort(spells);
        return spells;
    }
}