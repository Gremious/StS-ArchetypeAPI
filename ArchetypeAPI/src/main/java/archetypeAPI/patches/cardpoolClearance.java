package archetypeAPI.patches;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class cardpoolClearance {

    public static void replaceCardpool(ArrayList<AbstractCard> tmpPool, ArrayList<AbstractCard> replaceWith) {

        tmpPool.removeIf(card -> {
                    boolean idCheckBool = true;
                    System.out.println("We will check if this card fits our card pool requirements: " + card);
                    for (AbstractCard c : replaceWith) {
                      System.out.println("If it's ID is not equal to the ID of " + c);
                        if (card.cardID.equals(c.cardID)) {
                            idCheckBool = false;
                        }
                    }
                    return idCheckBool;
                }
        );
    }
}
