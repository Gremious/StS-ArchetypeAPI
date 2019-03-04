package archetypeAPI.patches;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.cards.archetypeSelectionCards.theIronclad.BasicIroncladArchetypeSelectCard;
import archetypeAPI.cards.archetypeSelectionCards.theSilent.BasicSilentArchetypeSelectCard;
import archetypeAPI.characters.customCharacterArchetype;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static archetypeAPI.patches.ArchetypeCardTags.BASIC;

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

                }
            case UNCOMMON:
                if (uncommonCheck() < numCheck) {

                }
                break;
            case RARE:
                if (rareCheck() < numCheck) {

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

    private void addCommonCards() {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        temp.group.addAll(UsedArchetypesCombined.group);

        if (AbstractDungeon.player instanceof customCharacterArchetype) {
            UsedArchetypesCombined.clear();
            CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();
            for (AbstractCard basicCheckCard : cardg.group) {
                if (basicCheckCard.hasTag(BASIC)) {
                    ((AbstractArchetypeCard) basicCheckCard).archetypeEffect();
                }
            }
            temp.addToRandomSpot(UsedArchetypesCombined.getRandomCard(true, AbstractCard.CardRarity.COMMON));
            UsedArchetypesCombined.clear();
            UsedArchetypesCombined.group.addAll(temp.group);
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    new BasicIroncladArchetypeSelectCard().makeCopy();
                    break;
                case THE_SILENT:
                    new BasicSilentArchetypeSelectCard().makeCopy();
                    break;
                case DEFECT:
                    new BasicSilentArchetypeSelectCard().makeCopy();
                    break;
                default:
                    break;
            }
        }
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



