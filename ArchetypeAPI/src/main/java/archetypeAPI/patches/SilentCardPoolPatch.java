package archetypeAPI.patches;

import archetypeAPI.archetypes.abstractArchetype;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

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
        //   System.out.println("Initial tmpPool at start of patch is: " + tmpPool);


        //    System.out.println("The entire list of cards (tmpPool) is: " + tmpPool);
        //  System.out.println("It will retain only retain these cards: " + abstractArchetype.getArchetypes());


        tmpPool.removeIf(card -> {
                    boolean idCheckBool = true;
                    System.out.println("oo this is inside a lambda fansy");
                    System.out.println("We are going to remove " + card);
                    for (AbstractCard c : abstractArchetype.UsedArchetypesCombined) {
                        System.out.println("If it's ID is not equal to the ID of " + c);
                        if (card.cardID.equals(c.cardID)) {
                            idCheckBool = false;
                        }
                    }
                    return idCheckBool;
                }
        );


        // tmpPool.clear();
        // tmpPool.addAll(abstractArchetype.addCardsFromArchetypes());

        System.out.println("END ARCHETYPE PATCH");
        System.out.println("The entire list of cards (tmpPool) is: " + tmpPool);
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



