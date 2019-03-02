package archetypeAPI.archetypes;

import archetypeAPI.jsonClasses.archetypeStringsClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class abstractArchetype {
    public static CardGroup archetypeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static ArrayList<AbstractCard> UsedArchetypesCombined = new ArrayList<>();
    private boolean useArchetype;
    public abstractArchetype(String archetypeFile, boolean useArchetype) {
        this.useArchetype = useArchetype;

        if (useArchetype) {
            addCardsFromArchetypes(archetypeFile);
        }
        removeDupes(UsedArchetypesCombined);
    }

    public static void addCardsFromArchetypes(String archetypeFile) {
        ArrayList<AbstractCard> currentArchetype = new ArrayList<>();

        InputStream in = abstractArchetype.class.getResourceAsStream("/" + archetypeFile);
        Type mapType = new TypeToken<Map<String, archetypeStringsClass>>() {
        }.getType();

        Map<String, archetypeStringsClass> allString = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), mapType);

        for (Map.Entry<String, archetypeStringsClass> entry : allString.entrySet()) {
            System.out.println("Adding " + entry.getKey() + " to archetypes; Name: " + entry.getValue().NAME);
            System.out.println("Adding Cards: " + Arrays.toString(entry.getValue().CARD_IDS));

            for (String ID : entry.getValue().CARD_IDS) {
                System.out.println("Adding Card: " + ID);
                currentArchetype.add(CardLibrary.getCopy(ID));
            }

            UsedArchetypesCombined.addAll(currentArchetype);
        }
    }


    public static ArrayList<AbstractCard> getArchetypes() {
        removeDupes(UsedArchetypesCombined);
        return UsedArchetypesCombined;
    }

    public static ArrayList<AbstractCard> removeDupes(ArrayList<AbstractCard> listToRemoveDupesFrom) {
        Set<AbstractCard> dupeRemoveSet = new LinkedHashSet<>();

        dupeRemoveSet.addAll(listToRemoveDupesFrom);
        listToRemoveDupesFrom.clear();
        listToRemoveDupesFrom.addAll(dupeRemoveSet);

        return listToRemoveDupesFrom;
    }
}
