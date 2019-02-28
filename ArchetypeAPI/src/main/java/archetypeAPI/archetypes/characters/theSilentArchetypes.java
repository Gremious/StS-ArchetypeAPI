package archetypeAPI.archetypes.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;

import java.util.ArrayList;

public class theSilentArchetypes extends Archetypes {

    // Create array lists filled with cards of the archetypes.
    public static ArrayList<AbstractCard> BasicSilentCards = new ArrayList<>();

    public static ArrayList<AbstractCard> DiscardSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> ShivSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> BlockSilentCards = new ArrayList<>();


    // Create enums with the names of your archetypes
    // Create an array list to add them to if it's for a custom character.
    public static ArrayList<ClassEnums> silentArchetypesEnums = new ArrayList<>();

    public enum ClassEnums {
        BASIC,
        DISCARD,
        SHIV,
        BLOCK
    }

    // Iterate through the array list of enums, and for each one, add the appropriate cards to a combined list.
    public void addCardsFromArchetypes() {
        if (BasicSilentCards.isEmpty()) fillBasicArray();
        if (DiscardSilentCards.isEmpty()) fillDiscardArray();
        if (ShivSilentCards.isEmpty()) fillShivArray();
        if (BlockSilentCards.isEmpty()) fillBlockArray();

        for (ClassEnums e : silentArchetypesEnums) {
            System.out.println("Enum archetypes are: " + e);
            if (e == ClassEnums.BASIC) {
                addToWholeFinalList(BasicSilentCards);
            }
        }

        removeDupes();
        System.out.println("Final List of cards is " + UsedArchetypesCombined);

    }


    public static void fillBasicArray() {
        System.out.println("Filling non-Archetype cards");
        BasicSilentCards.add(new DaggerSpray());
        BasicSilentCards.add(new Outmaneuver());
        BasicSilentCards.add(new Acrobatics());

        BasicSilentCards.add(new AllOutAttack());
        BasicSilentCards.add(new LegSweep());
        BasicSilentCards.add(new Caltrops());

        BasicSilentCards.add(new DieDieDie());
        BasicSilentCards.add(new Adrenaline());
        BasicSilentCards.add(new AfterImage());
        BasicSilentCards.add(new AfterImage());

    }


    public static void fillDiscardArray() {
        System.out.println("Filling discard cards");
        BasicSilentCards.add(new DaggerSpray());
        BasicSilentCards.add(new Outmaneuver());
        BasicSilentCards.add(new Acrobatics());

        BasicSilentCards.add(new AllOutAttack());
        BasicSilentCards.add(new LegSweep());
        BasicSilentCards.add(new Caltrops());

        BasicSilentCards.add(new DieDieDie());
        BasicSilentCards.add(new Adrenaline());
        BasicSilentCards.add(new AfterImage());
        BasicSilentCards.add(new AfterImage());

    }

    public static void fillShivArray() {
        System.out.println("Filling shiv cards");
        BasicSilentCards.add(new DaggerSpray());
        BasicSilentCards.add(new Outmaneuver());
        BasicSilentCards.add(new Acrobatics());

        BasicSilentCards.add(new AllOutAttack());
        BasicSilentCards.add(new LegSweep());
        BasicSilentCards.add(new Caltrops());

        BasicSilentCards.add(new DieDieDie());
        BasicSilentCards.add(new Adrenaline());
        BasicSilentCards.add(new AfterImage());
        BasicSilentCards.add(new AfterImage());

    }

    public static void fillBlockArray() {
        System.out.println("Filling block cards");
        BasicSilentCards.add(new DaggerSpray());
        BasicSilentCards.add(new Outmaneuver());
        BasicSilentCards.add(new Acrobatics());

        BasicSilentCards.add(new AllOutAttack());
        BasicSilentCards.add(new LegSweep());
        BasicSilentCards.add(new Caltrops());

        BasicSilentCards.add(new DieDieDie());
        BasicSilentCards.add(new Adrenaline());
        BasicSilentCards.add(new AfterImage());
        BasicSilentCards.add(new AfterImage());

    }

}
