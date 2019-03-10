package archetypeAPI.effects;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.characters.customCharacterArchetype;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static archetypeAPI.patches.ArchetypeCardTags.*;
import static archetypeAPI.util.cardpoolClearance.*;

public class RandomArchetypeEffect extends AbstractGameEffect {
    // This is totally an effect. Yes.
    private boolean needReinst = false;
    private static ArrayList<AbstractCard> randomArchetypes = new ArrayList<>();
    public static int ironcladBase = 6;
    public static int silentBase = 5;
    public static int defectBase = 8;

    public RandomArchetypeEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            UsedArchetypesCombined.clear();

            CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            if (AbstractDungeon.player instanceof customCharacterArchetype) {
                CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();
                int defaultNum = ((customCharacterArchetype) AbstractDungeon.player).numberOfDefaultArchetypes();

                for (AbstractCard c : cardg.group) {
                    if (c.hasTag(SINGLE)) {
                        list.addToTop(c);
                    }
                }

                addArchetype(list, defaultNum);

            } else {
                switch (AbstractDungeon.player.chosenClass) {
                    case IRONCLAD:
                        //System.out.println("Here is the entire initial archetype card pool from which we will pick randoms: " + abstractArchetype.ironcladArchetypeSelectCards.group.toString());

                        for (AbstractCard basicCheckCard : abstractArchetype.ironcladArchetypeSelectCards.group) {
                            if (basicCheckCard.hasTag(BASIC)) {
                                list.addToTop(basicCheckCard);
                            }
                        }
                        for (AbstractCard c : abstractArchetype.ironcladArchetypeSelectCards.group) {
                            if (c.hasTag(SINGLE) && !c.hasTag(BASIC)) {
                                list.addToTop(c);
                            }
                        }
                        addArchetype(list, ironcladBase);
                        break;
                    case THE_SILENT:
                        for (AbstractCard basicCheckCard : abstractArchetype.silentArchetypeSelectCards.group) {
                            if (basicCheckCard.hasTag(BASIC)) {
                                list.addToTop(basicCheckCard);
                            }

                            for (AbstractCard c : abstractArchetype.silentArchetypeSelectCards.group) {
                                if (c.hasTag(SINGLE) && !c.hasTag(BASIC)) {
                                    list.addToTop(c);
                                }

                            }
                        }
                        addArchetype(list, silentBase);
                        break;
                    case DEFECT:
                        for (AbstractCard basicCheckCard : abstractArchetype.defectArchetypeSelectCards.group) {
                            if (basicCheckCard.hasTag(BASIC)) {
                                list.addToTop(basicCheckCard);
                            }

                            for (AbstractCard c : abstractArchetype.defectArchetypeSelectCards.group) {
                                if (c.hasTag(SINGLE) && !c.hasTag(BASIC)) {
                                    list.addToTop(c);
                                }

                            }
                        }
                        addArchetype(list, defectBase);
                        break;
                    default:
                        System.out.println("Archetype selection patch says: ???????????????");
                        System.out.println("Is (AbstractDungeon.player instanceof customCharacterArchetype)?: " + ((AbstractDungeon.player instanceof customCharacterArchetype)));
                        System.out.println("AbstractDungeon.player.chosenClass: " + (AbstractDungeon.player.chosenClass.toString()));
                        isDone= true;
                        break;
                }
                System.out.println("addArchetype() is done.");
                System.out.println("Randomly generated archetype select cards: " + randomArchetypes.toString());

                for (AbstractCard c : randomArchetypes) {
                    if (c instanceof AbstractArchetypeCard) {
                        //System.out.println("Activating the archetype effect of " + c);
                        ((AbstractArchetypeCard) c).archetypeEffect();
                    }
                }
                //System.out.println("All effects activated. Pool is: " + UsedArchetypesCombined.toString());

                if (!UsedArchetypesCombined.isEmpty()) {
                    CardCrawlGame.dungeon.initializeCardPools();
                }

                CheckPools();

                if (needReinst && !UsedArchetypesCombined.isEmpty()) {
                    System.out.println("Card Pool too small! Adding some basic cards.");
                    CardCrawlGame.dungeon.initializeCardPools();
                }

            }


            tickDuration();
        }

        tickDuration();

    }

    private static void addArchetype(CardGroup list, int baseNum) {
        boolean canSupport = false;
        boolean isSupport = false;

        //System.out.println("The list of only Basic and Single archetypes for this class is: " + list.group.toString());
        int i = 0;

        for (AbstractCard basicCheckCard : list.group) {
            if (basicCheckCard.hasTag(BASIC)) {
                randomArchetypes.add(basicCheckCard);
                i++;
            }
        }


        while (i < baseNum) {
            //System.out.println("i = " + i);
            //System.out.println("baseNum = " + baseNum);

            for (AbstractCard c : randomArchetypes) {
                if (c.hasTag(INCLUDE_SUPPORT)) {
                    canSupport = true;      // Check whether we can support every at every iteration
                }

                if (c.hasTag(SUPPORT)) {
                    isSupport = true;
                }
            }

            //System.out.println("canSupport: " + canSupport);

            AbstractCard c = list.getRandomCard(true);

            //System.out.println("Random Card: " + c);
            //System.out.println("Does randomArchetypes: " + randomArchetypes.toString());
            //System.out.println("Contains card? (if false, adding card): " + (containsID(randomArchetypes, c)));


            if (!containsID(randomArchetypes, c)) {
                //System.out.println("If !Cointains triggered.");
                //System.out.println("The card: " + c + " with ID: " + c.cardID + " has the following tags: ");

                //System.out.println("c has tag basic?" + (c.hasTag(BASIC)));
                //System.out.println("c has tag single?: " + (c.hasTag(SINGLE)));
                //System.out.println("c has tag support?: " + (c.hasTag(SUPPORT)));

                if (c.hasTag(SUPPORT)) {
                    //System.out.println(c.cardID + " c is a Support card");
                    if (canSupport) {
                        //System.out.println("And we can add those, so adding c");
                        //System.out.println("Pre add: " + randomArchetypes.toString());
                        randomArchetypes.add(c);
                        //System.out.println("Post add: " + randomArchetypes.toString());
                        i++;
                    }
                } else {
                    //System.out.println("Pre add: " + randomArchetypes.toString());
                    randomArchetypes.add(c);
                    //System.out.println("Post add: " + randomArchetypes.toString());

                    //System.out.println("Pre-increment: " + i);
                    i++;
                    //System.out.println("Post-increment: " + i);
                }
            }

            //System.out.println("Does randomArch: " + randomArchetypes.toString());
            //System.out.println("Contain the whole list" + list.group.toString());
            //System.out.println("by ID?: " + (containsGroupByID(randomArchetypes, list.group)));

            if (containsGroupByID(randomArchetypes, list.group)) {
                //System.out.println("Every single archetype was added to the pool.");
                break;
            }

            if (!canSupport && isSupport) {
                boolean uhOh = true;

                for (AbstractCard ca : list.group) {
                    if (ca.hasTag(INCLUDE_SUPPORT))
                        uhOh = false;
                }

                if (uhOh) {
                    System.out.println("You have archetypes tagged SUPPORT, and yet, you don't have any tagged CAN_SUPPORT");
                    System.out.println("Please fix this.");
                    break;
                }

            }
        }
    }


    private void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }


    private void CheckPools() {
        ArrayList<AbstractCard> commonCheck = new ArrayList<>();
        ArrayList<AbstractCard> uncommonCheck = new ArrayList<>();
        ArrayList<AbstractCard> rareCheck = new ArrayList<>();

        for (AbstractCard ca : UsedArchetypesCombined.group) {
            if (ca.rarity == AbstractArchetypeCard.CardRarity.COMMON) commonCheck.add(ca);
            if (ca.rarity == AbstractArchetypeCard.CardRarity.UNCOMMON) uncommonCheck.add(ca);
            if (ca.rarity == AbstractArchetypeCard.CardRarity.RARE) rareCheck.add(ca);
        }

        if (commonCheck.size() < 3) {
            needReinst = true;

            for (int i = commonCheck.size(); i < 3; i++) {
                extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.COMMON);
            }

        }
        if (uncommonCheck.size() < 3) {
            needReinst = true;

            for (int i = commonCheck.size(); i < 3; i++) {
                extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.UNCOMMON);
            }

        }
        if (rareCheck.size() < 3) {
            needReinst = true;
            for (int i = commonCheck.size(); i < 3; i++) {
                extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.RARE);
            }
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }
}