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

        spells.add(new Spell(MainActivity.resources.getString(R.string.lightball_name),
                MainActivity.resources.getString(R.string.lightball_description), 1,
                MainActivity.resources.getString(R.string.lightball_mana),
                MainActivity.resources.getString(R.string.lightball_dc), Action.BONUSACTION,
                MainActivity.resources.getString(R.string.lightball_range),
                MainActivity.resources.getString(R.string.lightball_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.flash_name),
                MainActivity.resources.getString(R.string.flash_description), 1,
                MainActivity.resources.getString(R.string.flash_mana),
                MainActivity.resources.getString(R.string.flash_dc), Action.BONUSACTION,
                MainActivity.resources.getString(R.string.flash_range),
                MainActivity.resources.getString(R.string.flash_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.shield_name),
                MainActivity.resources.getString(R.string.shield_description), 1,
                MainActivity.resources.getString(R.string.shield_mana),
                MainActivity.resources.getString(R.string.shield_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.shield_range),
                MainActivity.resources.getString(R.string.shield_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.vineAttack_name),
                MainActivity.resources.getString(R.string.vineAttack_description), 1,
                MainActivity.resources.getString(R.string.vineAttack_mana),
                MainActivity.resources.getString(R.string.vineAttack_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.vineAttack_range),
                MainActivity.resources.getString(R.string.vineAttack_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.wall_name),
                MainActivity.resources.getString(R.string.wall_description), 1,
                MainActivity.resources.getString(R.string.wall_mana),
                MainActivity.resources.getString(R.string.wall_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.wall_range),
                MainActivity.resources.getString(R.string.wall_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.swamp_name),
                MainActivity.resources.getString(R.string.swamp_description), 1,
                MainActivity.resources.getString(R.string.swamp_mana),
                MainActivity.resources.getString(R.string.swamp_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.swamp_range),
                MainActivity.resources.getString(R.string.swamp_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.mist_name),
                MainActivity.resources.getString(R.string.mist_description), 2,
                MainActivity.resources.getString(R.string.mist_mana),
                MainActivity.resources.getString(R.string.mist_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.mist_range),
                MainActivity.resources.getString(R.string.mist_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.familiar_name),
                MainActivity.resources.getString(R.string.familiar_description), 2,
                MainActivity.resources.getString(R.string.familiar_mana),
                MainActivity.resources.getString(R.string.familiar_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.familiar_range),
                MainActivity.resources.getString(R.string.familiar_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.vineShackle_name),
                MainActivity.resources.getString(R.string.vineShackle_description), 2,
                MainActivity.resources.getString(R.string.vineShackle_mana),
                MainActivity.resources.getString(R.string.vineShackle_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.vineShackle_range),
                MainActivity.resources.getString(R.string.vineShackle_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.protectiveField_name),
                MainActivity.resources.getString(R.string.protectiveField_description), 2,
                MainActivity.resources.getString(R.string.protectiveField_mana),
                MainActivity.resources.getString(R.string.protectiveField_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.protectiveField_range),
                MainActivity.resources.getString(R.string.protectiveField_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.mirrorImage_name),
                MainActivity.resources.getString(R.string.mirrorImage_description), 2,
                MainActivity.resources.getString(R.string.mirrorImage_mana),
                MainActivity.resources.getString(R.string.mirrorImage_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.mirrorImage_range),
                MainActivity.resources.getString(R.string.mirrorImage_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.disguise_name),
                MainActivity.resources.getString(R.string.disguise_description), 2,
                MainActivity.resources.getString(R.string.disguise_mana),
                MainActivity.resources.getString(R.string.disguise_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.disguise_range),
                MainActivity.resources.getString(R.string.disguise_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.arcaneHands_name),
                MainActivity.resources.getString(R.string.arcaneHands_description), 2,
                MainActivity.resources.getString(R.string.arcaneHands_mana),
                MainActivity.resources.getString(R.string.arcaneHands_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.arcaneHands_range),
                MainActivity.resources.getString(R.string.arcaneHands_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.resurrect_name),
                MainActivity.resources.getString(R.string.resurrect_description), 2,
                MainActivity.resources.getString(R.string.resurrect_mana),
                MainActivity.resources.getString(R.string.resurrect_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.resurrect_range),
                MainActivity.resources.getString(R.string.resurrect_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.poisonCloud_name),
                MainActivity.resources.getString(R.string.poisonCloud_description), 2,
                MainActivity.resources.getString(R.string.poisonCloud_mana),
                MainActivity.resources.getString(R.string.poisonCloud_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.poisonCloud_range),
                MainActivity.resources.getString(R.string.poisonCloud_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.deathServant_name),
                MainActivity.resources.getString(R.string.deathServant_description), 3,
                MainActivity.resources.getString(R.string.deathServant_mana),
                MainActivity.resources.getString(R.string.deathServant_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.deathServant_range),
                MainActivity.resources.getString(R.string.deathServant_duration),
                SpellCat.Summoning));
        spells.add(new Spell(MainActivity.resources.getString(R.string.summonElemental_name),
                MainActivity.resources.getString(R.string.summonElemental_description), 3,
                MainActivity.resources.getString(R.string.summonElemental_mana),
                MainActivity.resources.getString(R.string.summonElemental_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.summonElemental_range),
                MainActivity.resources.getString(R.string.summonElemental_duration),
                SpellCat.Summoning));

        spells.add(new Spell(MainActivity.resources.getString(R.string.energyBullet_name),
                MainActivity.resources.getString(R.string.energyBullet_description), 1,
                MainActivity.resources.getString(R.string.energyBullet_mana),
                MainActivity.resources.getString(R.string.energyBullet_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.energyBullet_range),
                MainActivity.resources.getString(R.string.energyBullet_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.fireHands_name),
                MainActivity.resources.getString(R.string.fireHands_description), 1,
                MainActivity.resources.getString(R.string.fireHands_mana),
                MainActivity.resources.getString(R.string.fireHands_dc), Action.BONUSACTION,
                MainActivity.resources.getString(R.string.fireHands_range),
                MainActivity.resources.getString(R.string.fireHands_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.tornado_name),
                MainActivity.resources.getString(R.string.tornado_description), 1,
                MainActivity.resources.getString(R.string.tornado_mana),
                MainActivity.resources.getString(R.string.tornado_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.tornado_range),
                MainActivity.resources.getString(R.string.tornado_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.fireball_name),
                MainActivity.resources.getString(R.string.fireball_description), 2,
                MainActivity.resources.getString(R.string.fireball_mana),
                MainActivity.resources.getString(R.string.fireball_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.fireball_range),
                MainActivity.resources.getString(R.string.fireball_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.energySword_name),
                MainActivity.resources.getString(R.string.energySword_description), 2,
                MainActivity.resources.getString(R.string.energySword_mana),
                MainActivity.resources.getString(R.string.energySword_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.energySword_range),
                MainActivity.resources.getString(R.string.energySword_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.chainLightning_name),
                MainActivity.resources.getString(R.string.chainLightning_description), 2,
                MainActivity.resources.getString(R.string.chainLightning_mana),
                MainActivity.resources.getString(R.string.chainLightning_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.chainLightning_range),
                MainActivity.resources.getString(R.string.chainLightning_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.iceBolt_name),
                MainActivity.resources.getString(R.string.iceBolt_description), 2,
                MainActivity.resources.getString(R.string.iceBolt_mana),
                MainActivity.resources.getString(R.string.iceBolt_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.iceBolt_range),
                MainActivity.resources.getString(R.string.iceBolt_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.desintegrate_name),
                MainActivity.resources.getString(R.string.desintegrate_description), 2,
                MainActivity.resources.getString(R.string.desintegrate_mana),
                MainActivity.resources.getString(R.string.desintegrate_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.desintegrate_range),
                MainActivity.resources.getString(R.string.desintegrate_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.earthquake_name),
                MainActivity.resources.getString(R.string.earthquake_description), 2,
                MainActivity.resources.getString(R.string.earthquake_mana),
                MainActivity.resources.getString(R.string.earthquake_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.earthquake_range),
                MainActivity.resources.getString(R.string.earthquake_duration),
                SpellCat.Destruction));
        spells.add(new Spell(MainActivity.resources.getString(R.string.seaOfFlames_name),
                MainActivity.resources.getString(R.string.seaOfFlames_description), 3,
                MainActivity.resources.getString(R.string.seaOfFlames_mana),
                MainActivity.resources.getString(R.string.seaOfFlames_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.seaOfFlames_range),
                MainActivity.resources.getString(R.string.seaOfFlames_duration),
                SpellCat.Destruction));

        spells.add(new Spell(MainActivity.resources.getString(R.string.plantGrowth_name),
                MainActivity.resources.getString(R.string.plantGrowth_description), 1,
                MainActivity.resources.getString(R.string.plantGrowth_mana),
                MainActivity.resources.getString(R.string.plantGrowth_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.plantGrowth_range),
                MainActivity.resources.getString(R.string.plantGrowth_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.deepPuddle_name),
                MainActivity.resources.getString(R.string.deepPuddle_description), 1,
                MainActivity.resources.getString(R.string.deepPuddle_mana),
                MainActivity.resources.getString(R.string.deepPuddle_dc), Action.BONUSACTION,
                MainActivity.resources.getString(R.string.deepPuddle_range),
                MainActivity.resources.getString(R.string.deepPuddle_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.freeze_name),
                MainActivity.resources.getString(R.string.freeze_description), 1,
                MainActivity.resources.getString(R.string.freeze_mana),
                MainActivity.resources.getString(R.string.freeze_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.freeze_range),
                MainActivity.resources.getString(R.string.freeze_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.elementalManipulation_name),
                MainActivity.resources.getString(R.string.elementalManipulation_description), 1,
                MainActivity.resources.getString(R.string.elementalManipulation_mana),
                MainActivity.resources.getString(R.string.elementalManipulation_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.elementalManipulation_range),
                MainActivity.resources.getString(R.string.elementalManipulation_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.alarm_name),
                MainActivity.resources.getString(R.string.alarm_description), 1,
                MainActivity.resources.getString(R.string.alarm_mana),
                MainActivity.resources.getString(R.string.alarm_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.alarm_range),
                MainActivity.resources.getString(R.string.alarm_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.soundManipulation_name),
                MainActivity.resources.getString(R.string.soundManipulation_description), 1,
                MainActivity.resources.getString(R.string.soundManipulation_mana),
                MainActivity.resources.getString(R.string.soundManipulation_dc), Action.BONUSACTION,
                MainActivity.resources.getString(R.string.soundManipulation_range),
                MainActivity.resources.getString(R.string.soundManipulation_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.telekinesis_name),
                MainActivity.resources.getString(R.string.telekinesis_description), 2,
                MainActivity.resources.getString(R.string.telekinesis_mana),
                MainActivity.resources.getString(R.string.telekinesis_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.telekinesis_range),
                MainActivity.resources.getString(R.string.telekinesis_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.darkness_name),
                MainActivity.resources.getString(R.string.darkness_description), 2,
                MainActivity.resources.getString(R.string.darkness_mana),
                MainActivity.resources.getString(R.string.darkness_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.darkness_range),
                MainActivity.resources.getString(R.string.darkness_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.iceForming_name),
                MainActivity.resources.getString(R.string.iceForming_description), 2,
                MainActivity.resources.getString(R.string.iceForming_mana),
                MainActivity.resources.getString(R.string.iceForming_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.iceForming_range),
                MainActivity.resources.getString(R.string.iceForming_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.extremeTemperature_name),
                MainActivity.resources.getString(R.string.extremeTemperature_description), 2,
                MainActivity.resources.getString(R.string.extremeTemperature_mana),
                MainActivity.resources.getString(R.string.extremeTemperature_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.extremeTemperature_range),
                MainActivity.resources.getString(R.string.extremeTemperature_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.switchContent_name),
                MainActivity.resources.getString(R.string.switchContent_description), 2,
                MainActivity.resources.getString(R.string.switchContent_mana),
                MainActivity.resources.getString(R.string.switchContent_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.switchContent_range),
                MainActivity.resources.getString(R.string.switchContent_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.burrow_name),
                MainActivity.resources.getString(R.string.burrow_description), 2,
                MainActivity.resources.getString(R.string.burrow_mana),
                MainActivity.resources.getString(R.string.burrow_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.burrow_range),
                MainActivity.resources.getString(R.string.burrow_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.regeneration_name),
                MainActivity.resources.getString(R.string.regeneration_description), 2,
                MainActivity.resources.getString(R.string.regeneration_mana),
                MainActivity.resources.getString(R.string.regeneration_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.regeneration_range),
                MainActivity.resources.getString(R.string.regeneration_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.banMagic_name),
                MainActivity.resources.getString(R.string.banMagic_description), 2,
                MainActivity.resources.getString(R.string.banMagic_mana),
                MainActivity.resources.getString(R.string.banMagic_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.banMagic_range),
                MainActivity.resources.getString(R.string.banMagic_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.cleansing_name),
                MainActivity.resources.getString(R.string.cleansing_description), 2,
                MainActivity.resources.getString(R.string.cleansing_mana),
                MainActivity.resources.getString(R.string.cleansing_dc), Action.FULLACTION,
                MainActivity.resources.getString(R.string.cleansing_range),
                MainActivity.resources.getString(R.string.cleansing_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.silence_name),
                MainActivity.resources.getString(R.string.silence_description), 2,
                MainActivity.resources.getString(R.string.silence_mana),
                MainActivity.resources.getString(R.string.silence_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.silence_range),
                MainActivity.resources.getString(R.string.silence_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.antiMagicField_name),
                MainActivity.resources.getString(R.string.antiMagicField_description), 3,
                MainActivity.resources.getString(R.string.antiMagicField_mana),
                MainActivity.resources.getString(R.string.antiMagicField_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.antiMagicField_range),
                MainActivity.resources.getString(R.string.antiMagicField_duration),
                SpellCat.Alteration));
        spells.add(new Spell(MainActivity.resources.getString(R.string.bearForm_name),
                MainActivity.resources.getString(R.string.bearForm_description), 2,
                MainActivity.resources.getString(R.string.bearForm_mana),
                MainActivity.resources.getString(R.string.bearForm_dc), Action.STANDARDACTION,
                MainActivity.resources.getString(R.string.bearForm_range),
                MainActivity.resources.getString(R.string.bearForm_duration),
                SpellCat.Alteration));

        Collections.sort(spells);
        return spells;
    }
}