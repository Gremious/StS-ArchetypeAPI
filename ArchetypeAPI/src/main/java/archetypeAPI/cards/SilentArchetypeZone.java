package archetypeAPI.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
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


    public static void checkSilentArchetypes() {
        Iterator<CardArchsSilentEnum> i = silentArchetypesEnums.iterator();

        while (i.hasNext()) {
            CardArchsSilentEnum en = i.next();

            if (en == CardArchsSilentEnum.BASIC) {
                createArchetypeCardArray(BasicSilentCards, en);
            } else if (en == CardArchsSilentEnum.POISON) {
                createArchetypeCardArray(PoisonSilentCards, en);
            } else if (en == CardArchsSilentEnum.DISCARD) {
                createArchetypeCardArray(DiscardSilentCards, en);
            } else if (en == CardArchsSilentEnum.SHIV) {
                createArchetypeCardArray(ShivSilentCards, en);
            } else if (en == CardArchsSilentEnum.BLOCK) {
                createArchetypeCardArray(BlockSilentCards, en);
            }

        }
    }

    public static void createArchetypeCardArray(ArrayList<AbstractCard> arrayListOfCards, CardArchsSilentEnum arch) {
        if (arch == CardArchsSilentEnum.BASIC) {
            arrayListOfCards.add(new Strike_Green());
            arrayListOfCards.add(new Defend_Green());
        }
    }

    enum CardArchsSilentEnum {
        BASIC,
        POISON,
        DISCARD,
        SHIV,
        BLOCK
    }

    enum CardArchsIronclad {
        BASIC,
        STRENGTH,
        EXHAUST,
        BLOCK,
        SELF_DAMAGE,
        STRIKE
    }

    enum CardArchsDefect {
        BASIC,
        LIGHTNING,
        FROST,
        DARK,
        ZERO_COST, // + Claw btw
        POWER,
        BIG_ENERGY
    }

}
