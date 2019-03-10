package archetypeAPI.patches;

import archetypeAPI.characters.customCharacterArchetype;
import archetypeAPI.effects.RandomArchetypeEffect;
import archetypeAPI.effects.SelectArchetypeEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;

import static archetypeAPI.ArchetypeAPI.selectArchetypes;

@SpirePatch(
        clz = NeowEvent.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {boolean.class}
)

public class SelectArchetypesNeowPatch {

    public static void Postfix(NeowEvent __instance, boolean isDone) {
        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (Settings.isStandardRun() && (!Settings.isEndless || AbstractDungeon.floorNum > 1)) { // Only the first room ever ever

                if ((AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.IRONCLAD)
                        && (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.THE_SILENT)
                        && (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT)
                        && !(AbstractDungeon.player instanceof customCharacterArchetype)) {

                } else {
                    if (selectArchetypes) {
                        AbstractDungeon.effectList.add(new SelectArchetypeEffect());
                    } else {
                        AbstractDungeon.effectList.add(new RandomArchetypeEffect());
                    }
                }


            }
        }
    }
}