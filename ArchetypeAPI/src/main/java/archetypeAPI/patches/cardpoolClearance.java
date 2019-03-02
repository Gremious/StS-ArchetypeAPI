package archetypeAPI.patches;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class cardpoolClearance {

    public static void replaceCardpool(ArrayList<AbstractCard> tmpPool, ArrayList<AbstractCard> replaceWith) {
        tmpPool.removeIf(card -> {
                    boolean idCheckBool = true;
                    for (AbstractCard c : replaceWith) {
                        System.out.println("Only keeping identical cards:");
                        System.out.println("Card ID 1: " + card.cardID + " and Card ID 2: " + c.cardID);
                        if (card.cardID.equals(c.cardID)) {
                            idCheckBool = false;
                        }
                    }
                    return idCheckBool;
                }
        );
    }
}
