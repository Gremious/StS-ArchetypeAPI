package archetypeAPI.archetypes;

import archetypeAPI.cards.ArchetypeSelectCard;
import archetypeAPI.jsonClasses.ArchetypeStringsClass;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractArchetype {
    protected static final Logger logger = LogManager.getLogger(AbstractArchetype.class.getName());

    private static Map<AbstractPlayer.PlayerClass, CardGroup> archetypeSelectCards = new HashMap<>();
    private static Map<String, ArrayList<String>> archetypeFiles = new HashMap<>();

    public static CardGroup cardsOfTheArchetypesInUse = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static void readArchetypeJsonFile(FileHandle file) {
        ArchetypeStringsClass archetype = new Gson().fromJson(new InputStreamReader(file.read(), StandardCharsets.UTF_8), ArchetypeStringsClass.class);

        if (archetype.CHARACTER != null) {
            archetypeSelectCards.putIfAbsent(archetype.CHARACTER, new CardGroup(CardGroup.CardGroupType.UNSPECIFIED));
            archetypeSelectCards.computeIfPresent(archetype.CHARACTER, (k,v) -> { v.addToTop(new ArchetypeSelectCard(archetype)); return v; });
        }
    }

    public static CardGroup getArchetypeSelectCards(AbstractPlayer.PlayerClass playerClass) {
        return archetypeSelectCards.get(playerClass);
    }

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

    public static void addCardsFromArchetypes(String[] cardIDs) {
        ArrayList<AbstractCard> currentArchetype = new ArrayList<>();

        for (String ID : cardIDs) {
            currentArchetype.add(CardLibrary.getCopy(ID));
        }

        cardsOfTheArchetypesInUse.group.addAll(currentArchetype);
    }
}