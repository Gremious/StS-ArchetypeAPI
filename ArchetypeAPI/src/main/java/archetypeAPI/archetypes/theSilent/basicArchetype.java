package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.Archetypes;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.nio.charset.StandardCharsets;

public class basicArchetype extends Archetypes {

    // Iterate through the array list of enums, and for each one, add the appropriate cards to a combined list.
    public static void addCardsFromArchetypes() {
        String jsonString = Gdx.files.internal("archetypeAPIResources/localization/eng/archetypes/archetype-Silent-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Gson gson = new Gson();

        gson.fromJson(jsonString, archetypeStrings.class);

        for (String s : cool) {
            System.out.println("Hello " + s);
        }

        //   for () {
        //  addToWholeFinalList(BasicSilentCards.group);
        //    }

        removeDupes();
        //   System.out.println("Final List of cards is " + UsedArchetypesCombined);

    }

    private static CardGroup BasicSilentCards;

    static {
        BasicSilentCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

     //   BasicSilentCards.addToTop(CardLibrary.getCopy("Outmaneuver"));
    }
}
