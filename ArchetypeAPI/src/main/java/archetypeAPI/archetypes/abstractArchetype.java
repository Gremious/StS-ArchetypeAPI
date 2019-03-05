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
    public static CardGroup silentArchetypeSelectCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static CardGroup ironcladArchetypeSelectCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static CardGroup defectArchetypeSelectCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static CardGroup UsedArchetypesCombined = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

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
                System.out.println("Adding " + entry.getKey() + " to archetypes; Name: " + entry.getValue().NAME);
                System.out.println("Adding Cards: " + Arrays.toString(entry.getValue().CARD_IDS));

                for (String ID : entry.getValue().CARD_IDS) {
                    System.out.println("Adding Card: " + ID);
                    currentArchetype.add(CardLibrary.getCopy(ID));
                }

                UsedArchetypesCombined.group.addAll(currentArchetype);
            }
        }
    }


    public static CardGroup getArchetypes() {
        removeDupes(UsedArchetypesCombined);
        return UsedArchetypesCombined;
    }

    public static CardGroup removeDupes(CardGroup listToRemoveDupesFrom) {
        Set<AbstractCard> dupeRemoveSet = new LinkedHashSet<>();

        dupeRemoveSet.addAll(listToRemoveDupesFrom.group);
        listToRemoveDupesFrom.clear();
        listToRemoveDupesFrom.group.addAll(dupeRemoveSet);

        return listToRemoveDupesFrom;
    }

}
