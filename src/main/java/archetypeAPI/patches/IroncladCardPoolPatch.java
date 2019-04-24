package archetypeAPI.patches;

import archetypeAPI.util.CardpoolClearance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;

@SpirePatch(
        clz = Ironclad.class,
        method = "getCardPool"
)

public class IroncladCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(Ironclad __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            CardpoolClearance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
        }
        System.out.println("Archetype API Log: Ironclad card pool patch. You are playing with: " + tmpPool.size() + " cards.");
        System.out.println("These cards are: " + tmpPool.toString());
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



