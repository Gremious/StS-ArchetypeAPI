package archetypeAPI.patches;

import archetypeAPI.archetypes.AbstractArchetype;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import static archetypeAPI.util.CardpoolClearance.extendWithBasics;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getRewardCards"
)

public class ExpandPoolPatch {
    public static int numCheck;

    @SpireInsertPatch(
            locator = fixCardPoolsLocator.class,
            localvars = {"rarity", "numCards"}
    )
    public static void fixCardPools(AbstractCard.CardRarity rarity, int numCards) {
        numCheck = numCards;
        System.out.println("RARITY IS: " + rarity);
        System.out.println("numCards is " + numCards);
        System.out.println("numCheck is " + numCheck);
        System.out.println("There are this many commons: " + cardRarityCheck(AbstractCard.CardRarity.COMMON));
        System.out.println("There are this many uncommons: " + cardRarityCheck(AbstractCard.CardRarity.UNCOMMON));
        System.out.println("There are this many rares: " + cardRarityCheck(AbstractCard.CardRarity.RARE));

        switch (rarity) {
            case COMMON:
                if (cardRarityCheck(AbstractCard.CardRarity.COMMON) < numCheck) {
                    for (int i = cardRarityCheck(AbstractCard.CardRarity.COMMON); i < numCheck; i++) {
                        extendWithBasics(1, AbstractCard.CardRarity.COMMON);

                    }
                }
            case UNCOMMON:
                if (cardRarityCheck(AbstractCard.CardRarity.UNCOMMON) < numCheck) {
                    for (int i = cardRarityCheck(AbstractCard.CardRarity.UNCOMMON); i < numCheck; i++) {
                        extendWithBasics(1, AbstractCard.CardRarity.UNCOMMON);
                    }
                }
                break;
            case RARE:
                if (cardRarityCheck(AbstractCard.CardRarity.RARE) < numCheck) {
                    for (int i = cardRarityCheck(AbstractCard.CardRarity.RARE); i < numCheck; i++) {
                        extendWithBasics(1, AbstractCard.CardRarity.RARE);
                    }
                }
                break;
            default:
        }
    }

    private static int cardRarityCheck(AbstractCard.CardRarity rarity) {
        System.out.println("New Check Start");
        int count = 0;
        CardGroup UsedArchetypesCombinedTemp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        UsedArchetypesCombinedTemp.group.addAll(AbstractArchetype.cardsOfTheArchetypesInUse.group);

        System.out.println("Your  " + rarity.toString() + "'S are:");
        for (AbstractCard c : UsedArchetypesCombinedTemp.group) {
            //    System.out.println("cardRarityCheck for rarity " + rarity.toString() + " is " + c);
            if (c.rarity == rarity) {
                System.out.println(c);
                count++;
            }
        }
        System.out.println("Final Count:" + count);
        return count;
    }

    private static class fixCardPoolsLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "rollRarity");
            //  return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0] + 1};
        }
    }
}