package archetypeAPI.patches;

import archetypeAPI.util.CardsGet;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import javassist.CtBehavior;

@SpirePatch(
        clz = TheLibrary.class,
        method = "buttonEffect"
)

public class TheLibraryIsTotallyRandomPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars={"card"}
    )

    public static void insert(TheLibrary __instance, int buttonPressed, @ByRef AbstractCard[] card) {
        card[0] = CardsGet.getSuperRandomCard(AbstractDungeon.rollRarity()).makeCopy();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "contains");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}



