package archetypeAPI.patches;

import basemod.abstracts.CustomPlayer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Ironclad;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;

@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class BasemodCardPoolPatch {

    public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result, CustomPlayer __instance, ArrayList<AbstractCard> tmpPool) {
        System.out.println("CUSTOM PLAYER CARD POOL PATCH STARTED");
        System.out.println("Replacing: " + __result);
        System.out.println("With: " + UsedArchetypesCombined);
        System.out.println("(Unless it's empty)");

        if (!UsedArchetypesCombined.isEmpty()) {
            cardpoolClearance.replaceCardpool(__result, UsedArchetypesCombined);
        }

        System.out.println("Final tmpPool: " + __result);
        System.out.println("DONE, CUSTOM PLAYER CARD POOL PATCH ENDING");

        return __result;

    }

}



