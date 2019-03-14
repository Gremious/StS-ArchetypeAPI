package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import basemod.abstracts.CustomPlayer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.cardpoolClearance.makeSureWeMeetMinimum;

@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class BasemodCardPoolPatch {

    @SpirePrefixPatch
    public static SpireReturn<ArrayList<AbstractCard>> Prefix(CustomPlayer __instance, ArrayList<AbstractCard> tmpPool) {
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            makeSureWeMeetMinimum();
            cardpoolClearance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);

            System.out.println("Archetype API Log: Custom Character card pool patch. You are playing with: " + tmpPool.size() + " cards.");
            System.out.println("These cards are: " + tmpPool.toString());

            return SpireReturn.Return(tmpPool);
        }
        return SpireReturn.Continue();

    }

}



