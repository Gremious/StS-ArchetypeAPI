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

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.patches.ArchetypeCardTags.*;
import static archetypeAPI.util.CardpoolClearance.*;

public class RandomArchetypeEffect extends AbstractGameEffect {     // This is totally an effect. Yes.
    protected static final Logger logger = LogManager.getLogger(RandomArchetypeEffect.class.getName());
    // private static ArrayList<AbstractCard> randomArchetypes = new ArrayList<>();
    
    public RandomArchetypeEffect() {
        duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            cardsOfTheArchetypesInUse.clear(); // Makre sure the list is clean. Don't want archetypes from prior runs.
            
            addArchetypes();
            
            System.out.println("addArchetypes() is done.");

           /* for (AbstractCard c : randomArchetypes) {
                if (c instanceof ArchetypeSelectCard) {
                    //System.out.println("Activating the archetype effect of " + c);
                    ((ArchetypeSelectCard) c).archetypeEffect();
                }
            }*/
            
            makeSureWeMeetMinimum();
            
            if (!cardsOfTheArchetypesInUse.isEmpty()) {
                CardCrawlGame.dungeon.initializeCardPools();
            }
            
            tickDuration();
        }
        
        tickDuration();
    }
    
    private static void addArchetypes() {
        int currentCardListSize = 0;
        int maxNumber;
        CardGroup allArchetypeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup inUseArchetypes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup maxNumberCheckGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        
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
        int baseNum = ArchetypeAPI.characterCardNums.getOrDefault(AbstractDungeon.player.chosenClass, maxNumber);
        logger.info("The number of cards you've registered for \"" + AbstractDungeon.player.chosenClass + "\"  with Archetype API is: " + maxNumber);
        logger.info("The number of cards in the card library for  \"" + AbstractDungeon.player.chosenClass + "\"  is " + CardLibrary.getAllCards().stream().filter(c -> c.color == AbstractDungeon.player.getCardColor()).count());
        logger.info("Please keep in mind that special or starter cards should not be registered with Archetype API but will be found in the card library.");
        
        // Adds ALL the initial selection cards.
        for (AbstractCard c : AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass).group) {
            if (c.hasTag(BASIC) || c.hasTag(SINGLE)) {
                allArchetypeCards.addToTop(c);
            }
        }
        
        // Always add all BASIC tagged cards first.
        for (AbstractCard basicCheckCard : allArchetypeCards.group) {
            if (basicCheckCard.hasTag(BASIC)) {
                ((ArchetypeSelectCard) basicCheckCard).archetypeEffect();
                inUseArchetypes.addToTop(basicCheckCard);
                allArchetypeCards.removeCard(basicCheckCard);
                currentCardListSize = cardsOfTheArchetypesInUse.group.size();
            }
        }
        
        
        while (currentCardListSize < baseNum) {
            boolean canSupport = false;
            
            AbstractCard randomArchetype = allArchetypeCards.getRandomCard(true);
            
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
    
            ((ArchetypeSelectCard) randomArchetype).archetypeEffect();
            inUseArchetypes.addToTop(randomArchetype);
            allArchetypeCards.removeCard(randomArchetype);
            currentCardListSize = cardsOfTheArchetypesInUse.group.size();
        }
        
    }
    
    private static void supportCheck() {
        boolean canSupport = false;
        boolean isSupport = false;
        
        for (AbstractCard c : AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass).group){
            if (c.hasTag(SUPPORT)){
                isSupport = true;
            }
            if (c.hasTag(INCLUDE_SUPPORT)){
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