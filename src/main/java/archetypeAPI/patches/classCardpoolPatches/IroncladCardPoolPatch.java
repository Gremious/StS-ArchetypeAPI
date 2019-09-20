package archetypeAPI.patches.classCardpoolPatches;

import archetypeAPI.util.CardpoolMaintenance;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.CardpoolMaintenance.makeSureWeMeetMinimum;

@SpirePatch(
        clz = Ironclad.class,
        method = "getCardPool"
)

public class IroncladCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    
    public static void insert(Ironclad __instance, ArrayList<AbstractCard> tmpPool) {
        final Logger logger = LogManager.getLogger(IroncladCardPoolPatch.class.getName());
        
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            makeSureWeMeetMinimum(); // We are guaranteed ot have triggered this at this point but might as well?
            CardpoolMaintenance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
            
            // Save the card pools
            CustomSavable<List<String>> IrconladCardpoolSave = new CardpoolSavable(cardsOfTheArchetypesInUse.group);
            
            if (BaseMod.getSaveFields().get("ironcladArchetypeCardRewards") == null) {
                BaseMod.addSaveField("ironcladArchetypeCardRewards", IrconladCardpoolSave);
            }
            IrconladCardpoolSave.onSave();
        }
        System.out.println("Archetype API Log: Ironclad card pool patch. You are playing with: " + tmpPool.size() + " cards.");
        System.out.println("These cards are: " + tmpPool.toString());
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}



