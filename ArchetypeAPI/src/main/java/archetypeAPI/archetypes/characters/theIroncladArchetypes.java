package archetypeAPI.archetypes.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;

import java.util.ArrayList;
import java.util.Iterator;

public class theIroncladArchetypes {

    // Create array lists filled with cards of the archetypes.
    public static ArrayList<AbstractCard> BasicSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> PoisonSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> DiscardSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> ShivSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> BlockSilentCards = new ArrayList<>();

    public static ArrayList<CardArchsIronclad> silentArchetypesEnums = new ArrayList<>();


    public ArrayList<AbstractCard> addCardsFromArchetypes(ArrayList<AbstractCard> addCardsFromArchetypes) {
        ArrayList<AbstractCard> UsedArchetypesCombined = new ArrayList<>();
        fillArrayWithArchetypeCards();
        Iterator<CardArchsIronclad> i = silentArchetypesEnums.iterator();

        while (i.hasNext()) {
            CardArchsIronclad en = i.next();
            if (en == CardArchsIronclad.BASIC) {
                UsedArchetypesCombined.addAll(BasicSilentCards);
            } else if (en == CardArchsIronclad.STRENGTH) {
                UsedArchetypesCombined.addAll(PoisonSilentCards);
            }
        }

        return UsedArchetypesCombined;
    }

    public static void fillArrayWithArchetypeCards() {

        BasicSilentCards.add(new DaggerSpray());
        BasicSilentCards.add(new Outmaneuver());
        BasicSilentCards.add(new AThousandCuts());

        BasicSilentCards.add(new AllOutAttack());
        BasicSilentCards.add(new LegSweep());
        BasicSilentCards.add(new Caltrops());

        BasicSilentCards.add(new DieDieDie());
        BasicSilentCards.add(new Adrenaline());
        BasicSilentCards.add(new AfterImage());

        PoisonSilentCards.add(new DeadlyPoison());
        PoisonSilentCards.add(new BouncingFlask());
        PoisonSilentCards.add(new PoisonedStab());
    }

    public enum CardArchsIronclad {
        BASIC,
        STRENGTH,
        EXHAUST,
        BLOCK,
        SELF_DAMAGE,
        STRIKE
    }
}
