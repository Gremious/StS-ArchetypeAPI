package archetypeAPI.util;

import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.cards.archetypeSelectionCards.theIronclad.BasicIroncladArchetypeSelectCard;
import archetypeAPI.cards.archetypeSelectionCards.theSilent.BasicSilentArchetypeSelectCard;
import archetypeAPI.characters.customCharacterArchetype;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static archetypeAPI.patches.ArchetypeCardTags.BASIC;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.rollRarity;

public class cardpoolClearance {
    public static CardGroup cleanCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

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

    public static boolean containsID(ArrayList<AbstractCard> poolToCheck, AbstractCard cardWithID) {
        for (AbstractCard c : poolToCheck) {
            if (c.cardID.equals(cardWithID.cardID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsGroupByID(ArrayList<AbstractCard> poolToCheck, ArrayList<AbstractCard> checkAgainst) {
        int count = checkAgainst.size();

        for (AbstractCard ca : poolToCheck) {
            if (containsID(checkAgainst, ca)) {
                count--;
            }
        }

        return (count <= 0);
    }

    public static void populateTrulyFullClassCardList(AbstractCard.CardColor CardColor) {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(CardLibrary.getAllCards());

        for (AbstractCard c : allCards) {
            if (c.color == CardColor
                    && c.rarity != AbstractCard.CardRarity.SPECIAL
                    && c.rarity != AbstractCard.CardRarity.BASIC) {
                cleanCards.addToTop(c);
            }
        }
    }

    public static AbstractCard getSuperRandomCard(AbstractCard.CardRarity rarity) {
        if (cleanCards.isEmpty()) {
            populateTrulyFullClassCardList(AbstractDungeon.player.getCardColor());
        }
        return cleanCards.getRandomCard(true, rarity);
    }

    // ===================

    public static void extendWithBasics(int by) {
        if (AbstractDungeon.player instanceof customCharacterArchetype) {
            CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();
            boolean hasBasic = false;

            for (AbstractCard basicCheckCard : cardg.group) {
                if (basicCheckCard.hasTag(BASIC)) {
                    hasBasic = true;
                }
            }

            if (hasBasic) {
                for (AbstractCard basicCheckCard : cardg.group) {
                    if (basicCheckCard.hasTag(BASIC)) {
                        extendSpecificTypeWithBasicsInner(by, basicCheckCard);
                    }
                }
            } else {
                extendSpecificTypeWithBasicsInner(by);
            }

        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendSpecificTypeWithBasicsInner(by, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendSpecificTypeWithBasicsInner(by, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendSpecificTypeWithBasicsInner(by, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                default:
                    break;
            }
        }
    }

    public static void extendWithBasics(int by, AbstractCard.CardType type) {
        if (AbstractDungeon.player instanceof customCharacterArchetype) {
            CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();

            for (AbstractCard basicCheckCard : cardg.group) {
                if (basicCheckCard.hasTag(BASIC)) {
                    extendSpecificTypeWithBasicsInner(by, type, basicCheckCard);
                }
            }
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendSpecificTypeWithBasicsInner(by, type, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendSpecificTypeWithBasicsInner(by, type, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendSpecificTypeWithBasicsInner(by, type, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                default:
                    break;
            }
        }
    }

    public static void extendWithBasics(int by, AbstractCard.CardRarity rarity) {
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

    public static void extendWithBasics(int by, AbstractCard.CardRarity rarity, AbstractCard.CardType type) {
        if (AbstractDungeon.player instanceof customCharacterArchetype) {
            CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();

            for (AbstractCard basicCheckCard : cardg.group) {
                if (basicCheckCard.hasTag(BASIC)) {
                    extendSpecificRarirtyInner(by, rarity, type, basicCheckCard);
                }
            }
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendSpecificRarirtyInner(by, rarity, type, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendSpecificRarirtyInner(by, rarity, type, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendSpecificRarirtyInner(by, rarity, type, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                default:
                    break;
            }
        }
    }


    public static void extendWithBasicsInner(int by, AbstractCard basicArchetypeCard) {
        AbstractCard.CardRarity rarity = rollRarity();
        extendSpecificTypeWithBasicsInner(by, rarity, type, basicArchetypeCard);
    }

    public static void extendWithBasicsInner(int by, AbstractCard.CardType type, AbstractCard basicArchetypeCard) {
        AbstractCard.CardRarity rarity = rollRarity();
        extendSpecificTypeWithBasicsInner(by, rarity, type, basicArchetypeCard);
    }

    public static void extendWithBasicsInner(int by, AbstractCard.CardRarity rarity, AbstractCard basicArchetypeCard) {
        extendSpecificTypeWithBasicsInner(by, rarity, type, basicArchetypeCard);
    }

    public static void extendWithBasicsInner(int by, AbstractCard.CardRarity rarity, AbstractCard.CardType type, AbstractCard basicArchetypeCard) {
        extendSpecificTypeWithBasicsInner(by, rarity, type, basicArchetypeCard);
    }

    public static void extendSpecificTypeWithBasicsInner(int by, AbstractCard.CardRarity rarity, AbstractCard.CardType type, AbstractCard basicArchetypeCard) {
        CardGroup holder = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        holder.group.addAll(UsedArchetypesCombined.group);

        UsedArchetypesCombined.clear(); // Clear all cards
        ((AbstractArchetypeCard) basicArchetypeCard).archetypeEffect(); // And only activate the basic archetype

        CardGroup cardsOfTheType = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : UsedArchetypesCombined.group) {   // Fill this card group with basics
            if (c.type == type) {                               // of the right type
                cardsOfTheType.addToTop(c);
            }
        }

        if (!cardsOfTheType.isEmpty()) {
            cardsOfTheType.group.removeIf(card -> card.rarity != rarity);
        }

        // Keep only those of the specific Rarirty
        int i = 0;
        do {

            if (!cardsOfTheType.isEmpty() && !containsGroupByID(holder.group, UsedArchetypesCombined.group)) {
                AbstractCard c = UsedArchetypesCombined.getRandomCard(true);
                if (!containsID(holder.group, c)) {
                    holder.addToRandomSpot(c);
                    i++;
                }

            } else {
                AbstractCard c = CardLibrary.getRandomColorSpecificCard(AbstractDungeon.player.getCardColor(), AbstractDungeon.cardRandomRng);
                if (!containsID(holder.group, c)) {
                    holder.addToRandomSpot(c);
                    i++;
                }
            }
        } while (i < by);

        UsedArchetypesCombined.clear();
        UsedArchetypesCombined.group.addAll(holder.group);
        CardCrawlGame.dungeon.initializeCardPools();
        // Replace UsedArchetypesCombined with a temp group
    }


    public static void extendSpecificRarirtyInner(int by, AbstractCard.CardRarity rarity, AbstractCard basicArchetypeCard) {
        CardGroup holder = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        holder.group.addAll(UsedArchetypesCombined.group);

        UsedArchetypesCombined.clear(); // Clear all cards
        ((AbstractArchetypeCard) basicArchetypeCard).archetypeEffect(); // And only activate the basic archetype

        CardGroup cardsOfTheRarity = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : UsedArchetypesCombined.group) {   // Fill this card group with basics
            if (c.rarity == rarity) {                           // of the right rarity
                cardsOfTheRarity.addToTop(c);
            }
        }

        // Keep only those of the specific Rarirty
        int i = 0;
        do {
            if (!containsGroupByID(holder.group, UsedArchetypesCombined.group)) {
                AbstractCard c = UsedArchetypesCombined.getRandomCard(true);
                if (!containsID(holder.group, c)) {
                    holder.addToRandomSpot(c);
                    i++;
                }
            } else {
                AbstractCard c = CardLibrary.getRandomColorSpecificCard(AbstractDungeon.player.getCardColor(), AbstractDungeon.cardRandomRng);
                if (!containsID(holder.group, c)) {
                    holder.addToRandomSpot(c);
                    i++;
                }
            }
        } while (i < by);

        // Add a random basic card, unless we exhausted the pool of commons, then add a totally random card.

        UsedArchetypesCombined.clear();
        UsedArchetypesCombined.group.addAll(holder.group);
        CardCrawlGame.dungeon.initializeCardPools();
        // Replace UsedArchetypesCombined with a temp group
    }
}
