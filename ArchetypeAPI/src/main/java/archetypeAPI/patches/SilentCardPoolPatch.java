package archetypeAPI.patches;

import archetypeAPI.archetypes.characters.theSilentArchetypes;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.archetypes.characters.theSilentArchetypes.silentArchetypesEnums;

@SpirePatch(
        clz = TheSilent.class,
        method = "getCardPool"
)

public class SilentCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(TheSilent __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        System.out.println("START ARCHETYPE PATCH");

        System.out.println("Initial tmpPool at start of patch is: " + tmpPool);
        System.out.println("Adding enumerators");
        silentArchetypesEnums.add(theSilentArchetypes.CardArchsSilentEnum.BASIC);

        System.out.println("tmpPool: " + tmpPool + " will retain only contained inside: " + theSilentArchetypes.addCardsFromArchetypes());
        tmpPool.retainAll(theSilentArchetypes.addCardsFromArchetypes());

        System.out.println("END ARCHETYPE PATCH");
        System.out.println(tmpPool);
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



