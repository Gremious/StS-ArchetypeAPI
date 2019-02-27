package archetypeAPI.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;
import com.megacrit.cardcrawl.cards.green.Strike_Green;

import java.util.ArrayList;
import java.util.Iterator;

public class SilentArchetypeZone {
    public static ArrayList<CardArchsSilentEnum> silentArchetypesEnums = new ArrayList<>();
    /*
        public static ArrayList<Boolean> silentArchetypes = new ArrayList<>();
        public static Boolean UseBasicSilent = true;
        public static Boolean UsePoisonSilent = true;
        public static Boolean UseDiscardSilent = true;
        public static Boolean UseShivSilent = true;
        public static Boolean UseBlockSilent = true;
    */
    public static ArrayList<AbstractCard> BasicSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> PoisonSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> DiscardSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> ShivSilentCards = new ArrayList<>();
    public static ArrayList<AbstractCard> BlockSilentCards = new ArrayList<>();


    public static ArrayList<AbstractCard> checkSilentArchetypes() {
        ArrayList<AbstractCard> UsedArchetypesCombined = new ArrayList<>();
        createArchetypeCardArray();

        Iterator<CardArchsSilentEnum> i = silentArchetypesEnums.iterator();
        while (i.hasNext()) {
            CardArchsSilentEnum en = i.next();

            if (en == CardArchsSilentEnum.BASIC) {
                UsedArchetypesCombined.addAll(BasicSilentCards);
            } else if (en == CardArchsSilentEnum.POISON) {
                UsedArchetypesCombined.addAll(PoisonSilentCards);
            }
        }

        return UsedArchetypesCombined;
    }

    public static void createArchetypeCardArray() {
        Iterator<CardArchsSilentEnum> i = silentArchetypesEnums.iterator();

        while (i.hasNext()) {
            CardArchsSilentEnum en = i.next();
            if (en == CardArchsSilentEnum.BASIC) {
                BasicSilentCards.add(new Strike_Green());
                BasicSilentCards.add(new Defend_Green());
            } else if (en == CardArchsSilentEnum.POISON) {
                PoisonSilentCards.add(new DeadlyPoison());
                PoisonSilentCards.add(new DeadlyPoison());
                PoisonSilentCards.add(new PoisonedStab());
            }
        }
    }

    public enum CardArchsSilentEnum {
        BASIC,
        POISON,
        DISCARD,
        SHIV,
        BLOCK
    }

    public enum CardArchsIronclad {
        BASIC,
        STRENGTH,
        EXHAUST,
        BLOCK,
        SELF_DAMAGE,
        STRIKE
    }

    public enum CardArchsDefect {
        BASIC,
        LIGHTNING,
        FROST,
        DARK,
        ZERO_COST, // + Claw btw
        POWER,
        BIG_ENERGY
    }

}
