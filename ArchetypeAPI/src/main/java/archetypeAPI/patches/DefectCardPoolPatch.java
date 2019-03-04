package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;

@SpirePatch(
        clz = Defect.class,
        method = "getCardPool"
)

public class DefectCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(Defect __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        System.out.println("DEFECT CARD POOL PATCH STARTED");
        System.out.println("Replacing: " + tmpPool);
        System.out.println("With: " + UsedArchetypesCombined);
        System.out.println("(Unless it's empty)");

        if (!UsedArchetypesCombined.isEmpty()) {
            cardpoolClearance.replaceCardpool(tmpPool, UsedArchetypesCombined);
        }

        System.out.println("Final tmpPool: " + tmpPool);

        System.out.println("DONE, DEFECT CARD POOL PATCH ENDING");
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}



