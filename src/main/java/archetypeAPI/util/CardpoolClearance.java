package archetypeAPI.util;

import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.cards.archetypeSelectionCards.theDefect.BasicDefectArchetypeSelectCard;
import archetypeAPI.cards.archetypeSelectionCards.theIronclad.BasicIroncladArchetypeSelectCard;
import archetypeAPI.cards.archetypeSelectionCards.theSilent.BasicSilentArchetypeSelectCard;
import archetypeAPI.characters.customCharacterArchetype;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.patches.ArchetypeCardTags.BASIC;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.*;

public class CardpoolClearance {
    protected static final Logger logger = LogManager.getLogger(CardpoolClearance.class.getName());

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

    public static AbstractCard getSuperRandomCard(AbstractCard.CardType type) {
        if (cleanCards.isEmpty()) {
            populateTrulyFullClassCardList(AbstractDungeon.player.getCardColor());
        }
        return cleanCards.getRandomCard(type, true);
    }

    public static AbstractCard getSuperRandomCard(AbstractCard.CardRarity rarity, AbstractCard.CardType type) {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        if (cleanCards.isEmpty()) {
            populateTrulyFullClassCardList(AbstractDungeon.player.getCardColor());
        }

        for (AbstractCard c : cleanCards.group) {
            if (c.rarity == rarity && c.type == type) {
                temp.addToTop(c);
            }
        }

        return temp.getRandomCard(cardRandomRng);
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
                        extendWithBasicsInner(by, basicCheckCard);
                    }
                }
            } else {
                extendWithRandomCard(by);
            }

        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendWithBasicsInner(by, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendWithBasicsInner(by, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendWithBasicsInner(by, new BasicDefectArchetypeSelectCard().makeCopy());
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
                    extendWithBasicsInner(by, type, basicCheckCard);
                }
            }
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendWithBasicsInner(by, type, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendWithBasicsInner(by, type, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendWithBasicsInner(by, type, new BasicDefectArchetypeSelectCard().makeCopy());
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
                    extendWithBasicsInner(by, rarity, basicCheckCard);
                }
            }
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendWithBasicsInner(by, rarity, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendWithBasicsInner(by, rarity, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendWithBasicsInner(by, rarity, new BasicDefectArchetypeSelectCard().makeCopy());
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
                    extendWithBasicsInner(by, rarity, type, basicCheckCard);
                }
            }
        } else {
            switch (AbstractDungeon.player.chosenClass) {
                case IRONCLAD:
                    extendWithBasicsInner(by, rarity, type, new BasicIroncladArchetypeSelectCard().makeCopy());
                    break;
                case THE_SILENT:
                    extendWithBasicsInner(by, rarity, type, new BasicSilentArchetypeSelectCard().makeCopy());
                    break;
                case DEFECT:
                    extendWithBasicsInner(by, rarity, type, new BasicDefectArchetypeSelectCard().makeCopy());
                    break;
                default:
                    break;
            }
        }
    }

    public static void extendWithRandomCard(int by) {
        int i = 0;
        do {
            AbstractCard c = CardLibrary.getRandomColorSpecificCard(AbstractDungeon.player.getCardColor(), AbstractDungeon.cardRandomRng);
            if (!containsID(cardsOfTheArchetypesInUse.group, c)) {
                cardsOfTheArchetypesInUse.addToRandomSpot(c);
                i++;
            }

        } while (i < by);
    }

    public static void extendWithBasicsInner(int by, AbstractCard basicArchetypeCard) {
        extendWithBasicsInner(by, (AbstractCard.CardType) null, basicArchetypeCard);
    }

    public static void extendWithBasicsInner(int by, AbstractCard.CardType type, AbstractCard basicArchetypeCard) {
        AbstractCard.CardRarity rarity = rollRarity();
        extendWithBasicsInner(by, rarity, type, basicArchetypeCard);
    }

    public static void extendWithBasicsInner(int by, AbstractCard.CardRarity rarity, AbstractCard basicArchetypeCard) {
        extendWithBasicsInner(by, rarity, null, basicArchetypeCard);
    }

    public static void extendWithBasicsInner(int by, AbstractCard.CardRarity rarity, AbstractCard.CardType type, AbstractCard basicArchetypeCard) {

        CardGroup holder = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        holder.group.addAll(cardsOfTheArchetypesInUse.group);
        cardsOfTheArchetypesInUse.clear(); // Clear all cards
        CardGroup cardsThatFit = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        ((AbstractArchetypeCard) basicArchetypeCard).archetypeEffect(); // And only activate the basic archetype

        if (type == null) {
            if (rarity != AbstractCard.CardRarity.COMMON) {
                int roll = AbstractDungeon.miscRng.random(2);
                switch (roll) {
                    case 0:
                        type = AbstractCard.CardType.ATTACK;
                        break;
                    case 1:
                        type = AbstractCard.CardType.SKILL;
                        break;
                    case 2:
                        type = AbstractCard.CardType.POWER;
                        break;
                    default:
                        logger.info("nani? Let's just go for uhhhh skills then");
                        type = AbstractCard.CardType.SKILL;
                }
            } else {
                int roll = AbstractDungeon.miscRng.random(1);
                switch (roll) {
                    case 0:
                        type = AbstractCard.CardType.ATTACK;
                        break;
                    case 1:
                        type = AbstractCard.CardType.SKILL;
                        break;
                    default:
                        logger.info("nani? Let's just go for uhhhh skills then");
                        type = AbstractCard.CardType.SKILL;
                }
            }
        }

        for (AbstractCard c : cardsOfTheArchetypesInUse.group) {
            if (c.type == type && c.rarity == rarity) {
                cardsThatFit.addToTop(c);
            }
        }

        int i = 0;
        do {
            logger.info("You have requested a/an " + rarity + " " + type);
            logger.info("The entire list of basic cards is: " + holder);
            logger.info("We can add any of these: " + cardsThatFit + " unless you already have all of them.");

            if (!cardsThatFit.isEmpty() && !containsGroupByID(holder.group, cardsThatFit.group)) {
                AbstractCard c = cardsThatFit.getRandomCard(cardRandomRng);
                logger.info("We have cards that fit. Adding + " + c.name + " if you don't already have it.");
                if (!containsID(holder.group, c)) {
                    holder.addToRandomSpot(c);
                    i++;
                }
            } else {
                logger.info("The pool of fitting cards has been exhausted. Adding a totally random card.");
                AbstractCard c = getSuperRandomCard(rarity, type);
                if (!containsID(holder.group, c)) {
                    holder.addToRandomSpot(c);
                    i++;
                }
            }
        } while (i < by);

        cardsOfTheArchetypesInUse.clear();
        cardsOfTheArchetypesInUse.group.addAll(holder.group);
        // CardCrawlGame.dungeon.initializeCardPools();
        // Replace cardsOfTheArchetypesInUse with a temp group
    }

    // ===================

    public static void makeSureWeMeetMinimum() {
        int commons = 0;
        int uncommons = 0;
        int rares = 0;
        int attacks = cardsOfTheArchetypesInUse.getAttacks().size();
        int skills = cardsOfTheArchetypesInUse.getSkills().size();
        int powers = cardsOfTheArchetypesInUse.getPowers().size();


        for (AbstractCard c : cardsOfTheArchetypesInUse.group) {
            switch (c.rarity) {
                case COMMON:
                    commons++;
                    break;
                case UNCOMMON:
                    uncommons++;
                    break;
                case RARE:
                    rares++;
                    break;
            }
        }


        while (attacks < 3) {
            logger.info("You have too little attacks!: " + attacks);
            if (commons < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.COMMON, AbstractCard.CardType.ATTACK);
                commons++;
            } else if (uncommons < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.ATTACK);
                uncommons++;
            } else if (rares < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.RARE, AbstractCard.CardType.ATTACK);
                rares++;
            } else {
                AbstractCard.CardRarity rarity = rollRarity();
                extendWithBasics(1, rarity, AbstractCard.CardType.ATTACK);
                switch (rarity) {
                    case COMMON:
                        commons++;
                        break;
                    case UNCOMMON:
                        uncommons++;
                        break;
                    case RARE:
                        rares++;
                        break;
                }
            }
            attacks++;
        }

        while (skills < 3) {
            logger.info("You have too little skills!: " + skills);
            if (commons < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.COMMON, AbstractCard.CardType.SKILL);
                commons++;
            } else if (uncommons < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.SKILL);
                uncommons++;
            } else if (rares < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.RARE, AbstractCard.CardType.SKILL);
                rares++;
            } else {
                extendWithBasics(1, AbstractCard.CardType.SKILL);
            }
            skills++;
        }

        while (powers < 3) {
            logger.info("You have too little powers!: " + powers);
            if (uncommons < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.POWER);
            } else if (rares < 3) {
                extendWithBasics(1, AbstractCard.CardRarity.RARE, AbstractCard.CardType.POWER);
            } else {

                AbstractCard.CardRarity rarity = rollRareOrUncommon(0.25f);

                switch (rarity) {
                    case UNCOMMON:
                        uncommons++;
                        break;
                    case RARE:
                        rares++;
                        break;
                }

                extendWithBasics(1, rarity, AbstractCard.CardType.POWER);
            }
            powers++;
        }

        while (commons < 3) {
            logger.info("You have too little commons!: " + commons);
            extendWithBasics(1, AbstractCard.CardRarity.COMMON);
            commons++;
        }

        while (uncommons < 3) {
            logger.info("You have too little uncommons!: " + uncommons);
            extendWithBasics(1, AbstractCard.CardRarity.UNCOMMON);
            uncommons++;
        }

        while (rares < 3) {
            logger.info("You have too little rares!: " + rares);
            extendWithBasics(1, AbstractCard.CardRarity.RARE);
            rares++;
        }
    }

    // ===================

    public static void reinitializeCardPools() {
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            CardCrawlGame.dungeon.initializeCardPools();
        }
    }

}