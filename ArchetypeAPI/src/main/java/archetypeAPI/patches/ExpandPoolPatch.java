package archetypeAPI.patches;

import archetypeAPI.archetypes.abstractArchetype;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import static archetypeAPI.util.cardpoolClearance.extendSpecificRarityWithBasics;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getRewardCards"
)

public class ExpandPoolPatch {
    public static int numCheck;
    public static CardGroup UsedArchetypesCombinedTemp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    @SpireInsertPatch(
            locator = numCardsCheckLocator.class,
            localvars = {"numCards"}
    )
    public static void numCardsCheck(int numCards) {
        numCheck = numCards;
    }


    @SpireInsertPatch(
            locator = fixCardPoolsLocator.class,
            localvars = {"rarity"}
    )
    public static void fixCardPools(int numCards, AbstractCard.CardRarity rarity) {
        numCheck = numCards;
        switch (rarity) {
            case COMMON:
                if (commonCheck() < numCheck) {
                    for (int i = commonCheck(); i < numCheck; i++) {
                        extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.COMMON);
                    }
                }
            case UNCOMMON:
                if (uncommonCheck() < numCheck) {
                    for (int i = commonCheck(); i < numCheck; i++) {
                        extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.UNCOMMON);
                    }
                }
                break;
            case RARE:
                if (rareCheck() < numCheck) {
                    for (int i = commonCheck(); i < numCheck; i++) {
                        extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.RARE);
                    }
                }
                break;
            default:
        }


    }

    private static int commonCheck() {
        int commons = 0;
        UsedArchetypesCombinedTemp.group.addAll(abstractArchetype.UsedArchetypesCombined.group);
        for (AbstractCard c : UsedArchetypesCombinedTemp.group) {
            if (c.rarity == AbstractCard.CardRarity.COMMON) {
                commons++;
            }
        }
        return commons;
    }

    private static int uncommonCheck() {
        int uncommon = 0;
        UsedArchetypesCombinedTemp.group.addAll(abstractArchetype.UsedArchetypesCombined.group);
        for (AbstractCard c : UsedArchetypesCombinedTemp.group) {
            if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                uncommon++;
            }
        }
        return uncommon;
    }

    private static int rareCheck() {
        int rares = 0;
        UsedArchetypesCombinedTemp.group.addAll(abstractArchetype.UsedArchetypesCombined.group);
        for (AbstractCard c : UsedArchetypesCombinedTemp.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE) {
                rares++;
            }
        }
        return rares;
    }


    private static class numCardsCheckLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
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



