package archetypeAPI.patches;

import archetypeAPI.archetypes.AbstractArchetype;
import archetypeAPI.effects.RandomArchetypeEffect;
import archetypeAPI.effects.SelectArchetypeEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;

import static archetypeAPI.ArchetypeAPI.selectArchetypes;

@SpirePatch(
        clz = NeowEvent.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {boolean.class}
)
public class MainNeowPatch {
    public static void Postfix(NeowEvent __instance, boolean isDone) {
        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (Settings.isStandardRun() && (!Settings.isEndless || AbstractDungeon.floorNum > 1)) { // Only the first room ever ever
                if (AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass) != null) {
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