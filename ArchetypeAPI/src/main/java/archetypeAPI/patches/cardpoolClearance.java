package archetypeAPI.patches;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class cardpoolClearance {

    public static void replaceCardpool(ArrayList<AbstractCard> tmpPool, ArrayList<AbstractCard> replaceWith) {

        tmpPool.removeIf(card -> {
                    boolean idCheckBool = true;
                    System.out.println("oo this is inside a lambda fansy");
                    System.out.println("We are going to remove " + card);
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
