package archetypeAPI.patches.classCardpoolPatches;

import archetypeAPI.util.CardpoolMaintenance;
import basemod.abstracts.CustomPlayer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.CardpoolMaintenance.makeSureWeMeetMinimum;

@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class BasemodCardPoolPatch {

    @SpirePrefixPatch
    public static SpireReturn<ArrayList<AbstractCard>> Prefix(CustomPlayer __instance, ArrayList<AbstractCard> tmpPool) {
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            makeSureWeMeetMinimum();
            CardpoolMaintenance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);

            System.out.println("Archetype API Log: Custom Character card pool patch. You are playing with: " + tmpPool.size() + " cards.");
            System.out.println("These cards are: " + tmpPool.toString());

            return SpireReturn.Return(tmpPool);
        }
        return SpireReturn.Continue();

    }

}



