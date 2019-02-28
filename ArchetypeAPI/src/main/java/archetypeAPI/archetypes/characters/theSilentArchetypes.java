package archetypeAPI.archetypes.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.DaggerSpray;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class theSilentArchetypes  {

    // Create array lists filled with cards of the archetypes.
    public static ArrayList<AbstractCard> BasicSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> PoisonSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> DiscardSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> ShivSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> BlockSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> UsedArchetypesCombined = new ArrayList<>();


    // Create enums with the names of your archetypes and an array list you could add them
    public static ArrayList<ClassEnums> silentArchetypesEnums = new ArrayList<>();


    public enum ClassEnums {
        BASIC,
        POISON,
        DISCARD,
        SHIV,
        BLOCK
    }

    // Iterate through the array list of enums, and for each one, add the appropriate cards to a combined list.
    public static ArrayList<AbstractCard> addCardsFromArchetypes() {
        System.out.println("Triggered add cards from archetypes.");
        System.out.println("Basic Silent cards empty: " + BasicSilentCards.isEmpty());
        System.out.println("Poison Silent cards empty: " + PoisonSilentCards.isEmpty());

        if (BasicSilentCards.isEmpty()) fillBasicArray();
        if (PoisonSilentCards.isEmpty()) fillPoisonArray();


        for (ClassEnums e : silentArchetypesEnums) {
            System.out.println("Enum archetypes are: " + e);
            if (e == ClassEnums.BASIC) {
                UsedArchetypesCombined.addAll(BasicSilentCards);
            } else if (e == ClassEnums.POISON) {
                UsedArchetypesCombined.addAll(PoisonSilentCards);
            }
        }

        Set<AbstractCard> dupeRemoveSet = new LinkedHashSet<>();
        dupeRemoveSet.addAll(UsedArchetypesCombined);
        UsedArchetypesCombined.clear();
        UsedArchetypesCombined.addAll(dupeRemoveSet);
        dupeRemoveSet.clear();

        System.out.println("Final List of cards is " + UsedArchetypesCombined);
        return UsedArchetypesCombined;
    }


    public static void fillBasicArray() {
        System.out.println("Filling non-Archetype cards");
        BasicSilentCards.add(new DaggerSpray());

    }

    public static void fillPoisonArray() {
        System.out.println("Filling poison cards");
        PoisonSilentCards.add(new DeadlyPoison());
    }

    public static void fillDiscardArray() {
        System.out.println("Filling discard cards");

    }

    public static void fillShivArray() {
        System.out.println("Filling shiv cards");

    }

    public static void fillBlockArray() {
        System.out.println("Filling block cards");

    }

}
