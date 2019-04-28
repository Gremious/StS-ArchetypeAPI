package archetypeAPI.effects;

import archetypeAPI.ArchetypeAPI;
import archetypeAPI.archetypes.AbstractArchetype;
import archetypeAPI.cards.ArchetypeSelectCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.patches.ArchetypeCardTags.*;
import static archetypeAPI.util.CardpoolMaintenance.*;

public class RandomArchetypeEffect extends AbstractGameEffect {     // This is totally an effect. Yes.
    protected static final Logger logger = LogManager.getLogger(RandomArchetypeEffect.class.getName());
    // private static ArrayList<AbstractCard> randomArchetypes = new ArrayList<>();
    
    public RandomArchetypeEffect() {
        duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            cardsOfTheArchetypesInUse.clear(); // Make sure the list is clean. Don't want archetypes from prior runs.
            
            addArchetypes();
            logger.info("Added all selected archetypes.");
            logger.info("Current card list:" + cardsOfTheArchetypesInUse.group.toString());
            
            logger.info("Adding non-API cards");
            addNonAPICards();
            logger.info("Added cards from mods you have that don't have Archetype API support.");
            logger.info("Current card list:" + cardsOfTheArchetypesInUse.group.toString());
            
            logger.info("Making sure we are meeting minimum card requirements.");
            makeSureWeMeetMinimum();
            logger.info("We now meet the minimum card requirements.");
            logger.info("Current and final card list:" + cardsOfTheArchetypesInUse.group.toString());
            
            logger.info("Re-initializing card pools.");
            if (!cardsOfTheArchetypesInUse.isEmpty()) {
                CardCrawlGame.dungeon.initializeCardPools();
            }
            
            tickDuration();
        }
        
        tickDuration();
    }
    
    private static void addArchetypes() {
        int currentCardListSize;
        int maxNumber;
        int baseNum;
        CardGroup singleArchetypeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup basicArchetypeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup inUseArchetypes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup maxNumberCheckGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        HashMap<ArchetypeSelectCard, String> archNames = new HashMap<>();
        
        supportCheck(); // Prints out a log if you have archetypes that have the support tag but none that allow support ones to be included.
        
        // Gets the maximum amount of cards this character has registered with the API.
        // We're not using the card library since you don't have to add every card in your character to the API.
        for (AbstractCard c : AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass).group) {
            if (c.hasTag(BASIC) || c.hasTag(SINGLE)) {
                ((ArchetypeSelectCard) c).archetypeEffect(maxNumberCheckGroup);
            }
        }
        
        // Sets the default maximum number of cards we can add to the player if not previously specified.
        maxNumber = maxNumberCheckGroup.size();
        if (maxNumber < ArchetypeAPI.characterCardNums.get(AbstractDungeon.player.chosenClass)) {
            logger.info("YOU SET THE AMOUNT OF CARDS IN ARCHETYPE API TO LOAD FOR " + AbstractDungeon.player.chosenClass + " TO BE " + ArchetypeAPI.characterCardNums.get(AbstractDungeon.player.chosenClass) + " BUT THE AMOUNT OF CARDS YOU REGISTERED WITH IT IS" + maxNumber);
            logger.info("FIX THAT AND BE CAREFUL NEXT TIME");
            baseNum = maxNumber;
        } else {
            baseNum = ArchetypeAPI.characterCardNums.getOrDefault(AbstractDungeon.player.chosenClass, maxNumber);
        }
        
        
        logger.info("The number of cards registered for \"" + AbstractDungeon.player.chosenClass + "\"  with Archetype API is: " + maxNumber);
        logger.info("The number of cards in the card library for  \"" + AbstractDungeon.player.chosenClass + "\"  is " + CardLibrary.getAllCards().stream().filter(c -> c.color == AbstractDungeon.player.getCardColor()).count());
        logger.info("Please keep in mind that special or starter cards should not be registered with Archetype API but will be found in the card library.");
        logger.info("Modded cards that are not registered with Archetype API will be included and can bypass the archetype card limit.");
        
        // Adds ALL the initial selection cards.
        for (AbstractCard c : AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass).group) {
            if (c.hasTag(BASIC)) {
                basicArchetypeCards.addToTop(c);
            }
            if (c.hasTag(SINGLE)) {
                singleArchetypeCards.addToTop(c);
                archNames.putIfAbsent(((ArchetypeSelectCard) c), ((ArchetypeSelectCard) c).getArchetypeName());
            }
        }
        
        while (!basicArchetypeCards.isEmpty()) {
            AbstractCard basicCard = basicArchetypeCards.getTopCard();
            ((ArchetypeSelectCard) basicCard).archetypeEffect();
            inUseArchetypes.addToTop(basicCard);
            basicArchetypeCards.removeCard(basicCard);
        }
        
        currentCardListSize = cardsOfTheArchetypesInUse.group.size();
        
        while (currentCardListSize < baseNum) {
            boolean canSupport = false;
            
            AbstractCard randomArchetype = singleArchetypeCards.getRandomCard(true);
            
            for (AbstractCard c : inUseArchetypes.group) {
                if (c.hasTag(INCLUDE_SUPPORT)) {
                    canSupport = true;
                }
            }
            
            if (randomArchetype.hasTag(SUPPORT)) {
                if (!canSupport) {
                    continue;
                }
            }
            
            ((ArchetypeSelectCard) randomArchetype).archetypeEffect(); // Add the current archetype.
            
            for (ArchetypeSelectCard arch : archNames.keySet()) { // Add all archetypes sharing the same archetype name
                if (arch.getArchetypeName().equals(((ArchetypeSelectCard) randomArchetype).getArchetypeName())) {
                    arch.archetypeEffect();
                    inUseArchetypes.addToTop(randomArchetype);
                    singleArchetypeCards.removeCard(randomArchetype);
                }
            }
            
            inUseArchetypes.addToTop(randomArchetype);
            singleArchetypeCards.removeCard(randomArchetype);
            currentCardListSize = cardsOfTheArchetypesInUse.group.size();
        }
        
        logger.info("There are " + inUseArchetypes.size() + " archetypes selected for this run. They are: " + inUseArchetypes.group.toString());
        logger.info("There are  " + singleArchetypeCards.size() + " archetypes NOT used in this run. They are: " + singleArchetypeCards.group.toString());
    }
    
    protected static void addNonAPICards() {
        CardGroup groupOfAllAPICards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass).group) {
            ((ArchetypeSelectCard) c).archetypeEffect(groupOfAllAPICards);
        }
        
        CardGroup groupOfAllCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        groupOfAllCards.group.addAll(getAllEffectiveClassCards(AbstractDungeon.player.getCardColor()).group);
        
        removeGroupFromAnotherGroup(groupOfAllCards, groupOfAllAPICards);
        logger.info("This character has cards not registered with archetype API. Those cards are: " + groupOfAllCards.group.toString());
        logger.info("Adding them to the pool.");
        cardsOfTheArchetypesInUse.group.addAll(groupOfAllCards.group);
        // removeDuplicatesFromCardGroup(cardsOfTheArchetypesInUse);
    }
    
    private static void supportCheck() {
        boolean canSupport = false;
        boolean isSupport = false;
        
        for (AbstractCard c : AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass).group) {
            if (c.hasTag(SUPPORT)) {
                isSupport = true;
            }
            
            if (c.hasTag(INCLUDE_SUPPORT)) {
                canSupport = true;
            }
        }
        
        if (!canSupport && isSupport) {
            System.out.println("You have archetypes tagged SUPPORT, and yet, you don't have any tagged CAN_SUPPORT");
            System.out.println("This means that you have archetypes that can NEVER be selected randomly.");
            System.out.println("if this is not intentional, please fix it by tagging the appropriate archetypes correctly.");
        }
    }
    
    private void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }
    
    @Override
    public void dispose() {
    }
    
    @Override
    public void render(SpriteBatch spriteBatch) {
    }
}