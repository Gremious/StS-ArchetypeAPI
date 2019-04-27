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
    
    public static void insert(Defect __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        final Logger logger = LogManager.getLogger(DefectCardPoolPatch.class.getName());
        
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            makeSureWeMeetMinimum();
            CardpoolMaintenance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
            
            CustomSavable<List<String>> cards = new defectSavableCards(tmpPool);
            
            cards.onSave();
            BaseMod.addSaveField("defectArchetypeCardRewards", cards);
        } else {
            CardLibrary.addBlueCards(tmpPool);
        }
        System.out.println("Archetype API Log: Defect card pool patch. You are playing with: " + tmpPool.size() + " cards.");
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
    
    private static class defectSavableCards implements CustomSavable<List<String>> {
        final Logger logger = LogManager.getLogger(defectSavableCards.class.getName());
        ArrayList<AbstractCard> tmpPool;
        
        defectSavableCards(ArrayList<AbstractCard> tmpPool) {
            this.tmpPool = tmpPool;
        }
        
        @Override
        public List<String> onSave() {
            List<String> listOfIDs = tmpPool.stream().map(c -> c.cardID).collect(Collectors.toList());
            logger.info("Attempting to save tmpPool: " + tmpPool.toString());
            logger.info("As an ID list " + listOfIDs.toString());
            
            return listOfIDs;
        }
        
        @Override
        public void onLoad(List<String> listOfIDs) {
            for (String id : listOfIDs) {
                logger.info("Attempting to load tmpPool from ID list: " + listOfIDs.toString());
                logger.info("id: " + id);
                cardsOfTheArchetypesInUse.addToTop(CardLibrary.getCard(id));
            }
            makeSureWeMeetMinimum();
            CardpoolMaintenance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
        }
    }
}



