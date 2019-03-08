package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;

@SpirePatch(
        clz = Ironclad.class,
        method = "getCardPool"
)

public class IroncladCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(Ironclad __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        if (!UsedArchetypesCombined.isEmpty()) {
            cardpoolClearance.replaceCardpool(tmpPool, UsedArchetypesCombined);
        }
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



