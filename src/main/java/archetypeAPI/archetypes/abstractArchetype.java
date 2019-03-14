package archetypeAPI.archetypes;

import archetypeAPI.jsonClasses.archetypeStringsClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public abstract class abstractArchetype {
    protected static final Logger logger = LogManager.getLogger(abstractArchetype.class.getName());

    public static CardGroup silentArchetypeSelectCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static CardGroup ironcladArchetypeSelectCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static CardGroup defectArchetypeSelectCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static CardGroup cardsOfTheArchetypesInUse = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public abstractArchetype(ArrayList<String> archetypeFiles) {
        addCardsFromArchetypes(archetypeFiles);
    }

    public static void addCardsFromArchetypes(ArrayList<String> archetypeFiles) {
        ArrayList<AbstractCard> currentArchetype = new ArrayList<>();

        for (String archetypeFile : archetypeFiles) {
            InputStream in = abstractArchetype.class.getResourceAsStream("/" + archetypeFile);
            Type mapType = new TypeToken<Map<String, archetypeStringsClass>>() {
            }.getType();

            Map<String, archetypeStringsClass> allString = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), mapType);

            for (Map.Entry<String, archetypeStringsClass> entry : allString.entrySet()) {

                for (String ID : entry.getValue().CARD_IDS) {
                    //logger.info("Adding " + ID);
                    currentArchetype.add(CardLibrary.getCopy(ID));
                }

                cardsOfTheArchetypesInUse.group.addAll(currentArchetype);
            }
        }
    }

}