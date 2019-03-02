package archetypeAPI.patches;

import archetypeAPI.ArchetypeAPI;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "initializeFirstRoom"
)

public class ResetCardPoolsPatch {

    public static void Postfix(AbstractDungeon __instance) {
        if (ArchetypeAPI.selectArchetypes) {
            __instance.initializeCardPools();
        }
    }
}