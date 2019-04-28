package archetypeAPI.patches.classCardpoolPatches;

import archetypeAPI.util.CardpoolMaintenance;
import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;

@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class BasemodCardPoolPatch {
    
    @SpirePrefixPatch
    public static SpireReturn<ArrayList<AbstractCard>> Prefix(CustomPlayer __instance, ArrayList<AbstractCard> tmpPool) {
        final Logger logger = LogManager.getLogger(BasemodCardPoolPatch.class.getName());
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            // makeSureWeMeetMinimum();
            logger.info("Card pool for " + AbstractDungeon.player.chosenClass + " is about to be replaced.");
            logger.info("Original tmpPool: " + tmpPool.toString());
            logger.info("Cards to replace with: " + cardsOfTheArchetypesInUse.group.toString());
            CardpoolMaintenance.replaceCardpool(tmpPool, cardsOfTheArchetypesInUse);
            
            // Save the card pools
            CustomSavable<List<String>> CustomCharacterCardpoolSave = new CardpoolSavable(cardsOfTheArchetypesInUse.group);
            
            if (BaseMod.getSaveFields().get(AbstractDungeon.player.chosenClass + "ArchetypeCardRewards") == null) {
                BaseMod.addSaveField(AbstractDungeon.player.chosenClass + "ArchetypeCardRewards", CustomCharacterCardpoolSave);
            }
            CustomCharacterCardpoolSave.onSave();
            
            logger.info("Archetype API Log: Custom Character card pool patch. You are playing with: " + tmpPool.size() + " cards.");
            logger.info("These cards are: " + tmpPool.toString());
            
            return SpireReturn.Return(tmpPool);
        }
        return SpireReturn.Continue();
    }
}



