package archetypeAPI.patches;

import archetypeAPI.util.CardsGet;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.util.cardpoolClearance.containsID;
import static archetypeAPI.util.CardsGet.cleanCards;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "returnTrulyRandomCardInCombat",
        paramtypez = {}
)

public class DiscoveryActionPatch {
    @SpireInsertPatch(
            locator = Locator.class
            , localvars = {"list"}
    )

    public static void Insert(ArrayList<AbstractCard> list) {
        int attackCheck = 0;
        int skillCheck = 0;
        int powerCheck = 0;
        ArrayList<AbstractCard> rewardForNearlyNoReason = AbstractDungeon.getRewardCards();

        for (AbstractCard c : list) {
            if (c.type == AbstractCard.CardType.ATTACK) attackCheck++;
            if (c.type == AbstractCard.CardType.SKILL) skillCheck++;
            if (c.type == AbstractCard.CardType.POWER) powerCheck++;
        }

        if (cleanCards.isEmpty()) {
            CardsGet.populateTrulyFullClassCardList(AbstractDungeon.player.getCardColor());
        }

        if (attackCheck < 3) {
            while (attackCheck < ExpandPoolPatch.numCheck) {
                AbstractCard c = cleanCards.getRandomCard(AbstractCard.CardType.ATTACK, true);
                if (!containsID(list, c)) {
                    list.add(c);
                    attackCheck++;
                }
            }
        }

        if (skillCheck < 3) {
            while (skillCheck < ExpandPoolPatch.numCheck) {
                AbstractCard c = cleanCards.getRandomCard(AbstractCard.CardType.SKILL, true);
                if (!containsID(list, c)) {
                    list.add(c);
                    skillCheck++;
                }
            }
        }

        if (powerCheck < 3) {
            while (powerCheck < ExpandPoolPatch.numCheck) {
                AbstractCard c = cleanCards.getRandomCard(AbstractCard.CardType.POWER, true);
                if (!containsID(list, c)) {
                    list.add(c);
                    powerCheck++;
                }
            }
        }

    }


    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "get");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}
