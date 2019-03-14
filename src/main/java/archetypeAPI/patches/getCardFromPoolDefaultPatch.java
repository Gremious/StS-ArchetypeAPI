package archetypeAPI.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.apache.logging.log4j.Logger;

import static archetypeAPI.archetypes.abstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.cardpoolClearance.getSuperRandomCard;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getCardFromPool",
        paramtypez = {AbstractCard.CardRarity.class,
                AbstractCard.CardType.class,
                boolean.class}
)
public class getCardFromPoolDefaultPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static SpireReturn<AbstractCard> getCardFromPoolDefaultPatch(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng) {

        for (AbstractCard c : cardsOfTheArchetypesInUse.group) {
            if (c.type == type) {
                return SpireReturn.Return(c);
            } else {
                AbstractCard rc = getSuperRandomCard(rarity, type);
                return SpireReturn.Return(rc);
            }
        }

        return SpireReturn.Return(new Madness().makeCopy());
    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
            //  return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[4]};
        }
    }

}

