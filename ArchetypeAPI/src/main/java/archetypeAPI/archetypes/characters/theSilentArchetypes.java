package archetypeAPI.archetypes.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;

import java.util.ArrayList;

public class theSilentArchetypes {

    // Create array lists filled with cards of the archetypes.
    public static ArrayList<AbstractCard> BasicSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> PoisonSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> DiscardSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> ShivSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> BlockSilentCards = new ArrayList<>();


    // Create enums with the names of your archetypes and an array list you could add them
    public static ArrayList<CardArchsSilentEnum> silentArchetypesEnums = new ArrayList<>();

    public enum CardArchsSilentEnum {
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

        ArrayList<AbstractCard> UsedArchetypesCombined = new ArrayList<>();
        if (BasicSilentCards.isEmpty()) fillBasicArray();
        if (PoisonSilentCards.isEmpty()) fillPoisonArray();


        for (CardArchsSilentEnum e : silentArchetypesEnums) {
            System.out.println("Enum archetypes are: " + e);
            if (e == CardArchsSilentEnum.BASIC) {
                UsedArchetypesCombined.addAll(BasicSilentCards);
            } else if (e == CardArchsSilentEnum.POISON) {
                UsedArchetypesCombined.addAll(PoisonSilentCards);
            }
        }
        System.out.println("Final List of cards is " + UsedArchetypesCombined);
        return UsedArchetypesCombined;
    }

    public static void fillBasicArray() {
        System.out.println("filling basic cards");
        BasicSilentCards.add(new DaggerSpray());
        BasicSilentCards.add(new Outmaneuver());
        BasicSilentCards.add(new Acrobatics());

        BasicSilentCards.add(new AllOutAttack());
        BasicSilentCards.add(new LegSweep());
        BasicSilentCards.add(new Caltrops());

        BasicSilentCards.add(new DieDieDie());
        BasicSilentCards.add(new Adrenaline());
        BasicSilentCards.add(new AfterImage());

    }

    public static void fillPoisonArray() {
        System.out.println("filling poison cards");
        PoisonSilentCards.add(new DeadlyPoison());
        PoisonSilentCards.add(new BouncingFlask());
        PoisonSilentCards.add(new PoisonedStab());
    }

}
