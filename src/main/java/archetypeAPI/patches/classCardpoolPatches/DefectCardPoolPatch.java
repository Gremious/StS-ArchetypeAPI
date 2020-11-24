package archetypeAPI.patches.classCardpoolPatches;

import archetypeAPI.util.CardpoolMaintenance;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.CardpoolMaintenance.makeSureWeMeetMinimum;

@SpirePatch(
        clz = Defect.class,
        method = "getCardPool"
)

public class DefectCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    
    public static void insert(Defect __instance, ArrayList<AbstractCard> tmpPool) {
        final Logger logger = LogManager.getLogger(DefectCardPoolPatch.class.getName());
        
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            makeSureWeMeetMinimum();
            CardpoolMaintenance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
            
            // Save the card pools
            CustomSavable<List<String>> DefectCardpoolSave = new CardpoolSavable(cardsOfTheArchetypesInUse.group);
            
            if (BaseMod.getSaveFields().get("defectArchetypeCardRewards") == null) {
                BaseMod.addSaveField("defectArchetypeCardRewards", DefectCardpoolSave);
            }
            DefectCardpoolSave.onSave();
        }
        
        logger.info("Archetype API Log: Defect card pool patch. You are playing with: " + tmpPool.size() + " cards.");
        logger.info("These cards are: " + tmpPool.toString());
    
        ArrayList<AbstractCard> testAllCards = CardpoolMaintenance.testMissing(AbstractCard.CardColor.BLUE, tmpPool);
        logger.info("You are not playing with: " + testAllCards.size() + " cards.");
        logger.info("These cards are: " + testAllCards);
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



