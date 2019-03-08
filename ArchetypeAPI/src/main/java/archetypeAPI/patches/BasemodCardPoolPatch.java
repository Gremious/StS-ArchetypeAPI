package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import basemod.abstracts.CustomPlayer;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;

@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class BasemodCardPoolPatch {

    public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result, CustomPlayer __instance, ArrayList<AbstractCard> tmpPool) {
        if (!UsedArchetypesCombined.isEmpty()) {
            cardpoolClearance.replaceCardpool(__result, UsedArchetypesCombined);
        }
        System.out.println("Archetype API Log: Custom Character card pool patch. You are playing with: " + tmpPool.size() + " cards.");
        return __result;

    }

}



