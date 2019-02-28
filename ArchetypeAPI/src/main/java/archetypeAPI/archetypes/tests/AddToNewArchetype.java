package archetypeAPI.archetypes.tests;

import archetypeAPI.archetypes.characters.Archetypes;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;

import java.util.ArrayList;

public class AddToNewArchetype extends Archetypes {
    // I want to add a whole new archetype that totally DOESN'T exist in the silent: Poison!
    private static ArrayList<AbstractCard> brandNewSeperatePoisonArchetype = new ArrayList<>();

    // I'll need to declare an array list of Enums that will contain the Enum of my archetypes
    public static ArrayList<ClassEnums> mySilentEnums = new ArrayList<>();

    // I'll need to create the actual enum.
    public enum ClassEnums {
        POISON
    }

    public void addCardsFromArchetypes() {
        if (brandNewSeperatePoisonArchetype.isEmpty()) fillPoisonArray(); // Fill Out all of the card pools.

        for (ClassEnums e : mySilentEnums) {
            if (e == ClassEnums.POISON) {
                addToWholeFinalList(brandNewSeperatePoisonArchetype);
            }
        }
    }

    // Card pool of upgraded stolen archetypes.
    private static CardGroup poisonCards;

    static {
        poisonCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        poisonCards.addToTop(new DeadlyPoison());
        poisonCards.addToTop(new PoisonedStab());
        poisonCards.addToTop(new CripplingPoison());
        poisonCards.addToTop(new Catalyst());

    }


    private static void fillPoisonArray() {
        System.out.println("Filling poison cards");
        brandNewSeperatePoisonArchetype.add(new DeadlyPoison());
        brandNewSeperatePoisonArchetype.add(new PoisonedStab());
        brandNewSeperatePoisonArchetype.add(new CripplingPoison());

        brandNewSeperatePoisonArchetype.add(new Catalyst());

    }
}
