package archetypeAPI.archetypes;

import archetypeAPI.cards.ArchetypeSelectCard;
import archetypeAPI.jsonClasses.ArchetypeStringsClass;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static archetypeAPI.util.CardpoolMaintenance.removeDuplicatesFromCardGroup;

public abstract class AbstractArchetype {
    protected static final Logger logger = LogManager.getLogger(AbstractArchetype.class.getName());
    
    private static Map<AbstractPlayer.PlayerClass, CardGroup> archetypeSelectCards = new HashMap<>();
    
    public static CardGroup cardsOfTheArchetypesInUse = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    
    public static void readArchetypeJsonFile(FileHandle file) {
        ArchetypeStringsClass archetype = new Gson().fromJson(new InputStreamReader(file.read(), StandardCharsets.UTF_8), ArchetypeStringsClass.class);
        if (archetype.CHARACTER != null) {
            // If the player class (declared in the json) doesn't yet have a card group associated with it, map one to it
            archetypeSelectCards.putIfAbsent(archetype.CHARACTER, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
            
            // Then, if the the card groups exists, add a new archetype selection card,
            // passing the current json (pre-converted as an object) to it for it to infer all the info
            archetypeSelectCards.computeIfPresent(archetype.CHARACTER, (k, v) -> {
                v.addToTop(new ArchetypeSelectCard(archetype));
                return v;
            });
        }
    }
    
    public static CardGroup getArchetypeSelectCards(AbstractPlayer.PlayerClass playerClass) {
        return archetypeSelectCards.get(playerClass);
    }
    
    public static void addCardsFromArchetypes(String[] cardIDs, CardGroup groupToAdd) {
        ArrayList<AbstractCard> currentArchetype = new ArrayList<>();
        
        for (String ID : cardIDs) {
            currentArchetype.add(CardLibrary.getCopy(ID));
        }
        
        try {
            groupToAdd.group.addAll(currentArchetype);}
        catch (NullPointerException e){
            logger.info("YOU TRIED CALLING addCardsFromArchetypes() WITH A NULL GROUP");
            logger.info("This will usually only happen if you used the archetypeEffect of a card and passed a specific (null) cardGroup as a parameter");
            e.printStackTrace();
        }
    
        removeDuplicatesFromCardGroup(groupToAdd);
    }
    
    public static void addCardsFromArchetypes(String[] cardIDs) {
        ArrayList<AbstractCard> currentArchetype = new ArrayList<>();
        
        for (String ID : cardIDs) {
            currentArchetype.add(CardLibrary.getCopy(ID));
        }
        
        cardsOfTheArchetypesInUse.group.addAll(currentArchetype);
        removeDuplicatesFromCardGroup(cardsOfTheArchetypesInUse);
    }
}

   /* Cya!
   public AbstractArchetype(ArrayList<String> archetypeFiles) {
        addCardsFromArchetypes(archetypeFiles);
    }

    public static void addCardsFromArchetypes(ArrayList<String> archetypeFiles) {
        ArrayList<AbstractCard> currentArchetype = new ArrayList<>();

        for (String archetypeFile : archetypeFiles) {
            InputStream in = AbstractArchetype.class.getResourceAsStream("/" + archetypeFile);
            Type mapType = new TypeToken<Map<String, ArchetypeStringsClass>>(){}.getType();

            Map<String, ArchetypeStringsClass> allString = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), mapType);

            for (Map.Entry<String, ArchetypeStringsClass> entry : allString.entrySet()) {

                for (String ID : entry.getValue().CARD_IDS) {
                    //logger.info("Adding " + ID);
                    currentArchetype.add(CardLibrary.getCopy(ID));
                }

                cardsOfTheArchetypesInUse.group.addAll(currentArchetype);
            }
        }
    }
*/
