package archetypeAPI.archetypes.tests;

import archetypeAPI.archetypes.characters.theSilentArchetypes;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.green.Catalyst;
import com.megacrit.cardcrawl.cards.green.CripplingPoison;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;

import java.util.ArrayList;

public class AddToExistingArchetype extends theSilentArchetypes {
    // Let's say we want to add a whole new archetype that totally DOESN'T exist in the silent: Poison!
    // We'll need to declare an array list of Enums that will contain the Enums of any archetypes we add
    // (in this case, just the 1 : Poison)
    public static ArrayList<ClassEnums> mySilentEnums = new ArrayList<>();
    // I would make an overarching super-ArrayList of enums, but you can't really add different types of Enums to 1 array
    // And I don't know of a better way to go about this.

    // Create the actual enum.
    public enum ClassEnums {
        POISON
    }
    // The selection card we create will add this to the array list to enable the archetype.
    // Iterate through the whole list of enums, and only add cards to the pool that the enums are present for.

    @Override
    public void addCardsFromArchetypes() {
        for (ClassEnums e : mySilentEnums) {
            if (e == ClassEnums.POISON) {
                addToWholeFinalList(poisonCards.group);
            }
        }

        super.addCardsFromArchetypes();
    }

    // Card pool of the Archetype cards. You can just fill out an array too, if you want. This is just cleaner.
    private static CardGroup poisonCards;
    static {
        poisonCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        poisonCards.addToTop(new DeadlyPoison());
        poisonCards.addToTop(new PoisonedStab());
        poisonCards.addToTop(new CripplingPoison());
        poisonCards.addToTop(new Catalyst());

    }


}
