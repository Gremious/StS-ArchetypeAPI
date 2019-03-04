package archetypeAPI.util;

import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.cards.archetypeSelectionCards.theIronclad.BasicIroncladArchetypeSelectCard;
import archetypeAPI.cards.archetypeSelectionCards.theSilent.BasicSilentArchetypeSelectCard;
import archetypeAPI.characters.customCharacterArchetype;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static archetypeAPI.patches.ArchetypeCardTags.BASIC;

public class cardpoolClearance {

    public static void replaceCardpool(ArrayList<AbstractCard> tmpPool, CardGroup replaceWith) {
        tmpPool.removeIf(card -> {
                    boolean idCheckBool = true;
                    for (AbstractCard c : replaceWith.group) {
                        //    System.out.println("Only keeping identical cards:");
                        //    System.out.println("Card ID 1: " + card.cardID + " and Card ID 2: " + c.cardID);
                        if (card.cardID.equals(c.cardID)) {
                            idCheckBool = false;
                        }
                    }
                    return idCheckBool;
                }
        );
    }


    public static void extendSpecificRarityWithBasics(int by, AbstractCard.CardRarity rarity) {
        if (AbstractDungeon.player instanceof customCharacterArchetype) {
            CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();

            for (AbstractCard basicCheckCard : cardg.group) {
                if (basicCheckCard.hasTag(BASIC)) {
                    extendSpecificRarirtyInner(by, rarity, basicCheckCard);
                }
            }
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendSpecificRarirtyInner(by, rarity, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendSpecificRarirtyInner(by, rarity, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendSpecificRarirtyInner(by, rarity, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                default:
                    break;
            }
        }
    }

    public static void extendSpecificRarirtyInner(int by, AbstractCard.CardRarity rarity, AbstractCard basicArchetypeCard) {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        temp.group.addAll(UsedArchetypesCombined.group);
        UsedArchetypesCombined.clear();

        ((AbstractArchetypeCard) basicArchetypeCard).archetypeEffect();

        ArrayList<AbstractCard> mini = new ArrayList<>();
        for (AbstractCard c : UsedArchetypesCombined.group) {
            if (c.rarity == rarity) {
                mini.add(c);
            }
        }
        UsedArchetypesCombined.group.clear();
        UsedArchetypesCombined.group.addAll(mini);

        // Keep only those of the specific Rarirty
        int i = 0;
        do {
            if (!temp.group.containsAll(UsedArchetypesCombined.group)) {
                AbstractCard c = UsedArchetypesCombined.getRandomCard(true);

                if (!temp.contains(c)) {
                    temp.addToRandomSpot(c);
                    i++;
                }

            } else {
                AbstractCard c = CardLibrary.getRandomColorSpecificCard(AbstractDungeon.player.getCardColor(), AbstractDungeon.cardRandomRng);
                if (!temp.contains(c)) {
                    temp.addToRandomSpot(c);
                    i++;
                }
            }
        } while (i < by);

        // Add a random basic card, unless we exhausted the pool of commons, then add a totally random card.

        UsedArchetypesCombined.clear();
        UsedArchetypesCombined.group.addAll(temp.group);
        // Replace UsedArchetypesCombined with a temp group
    }
}
