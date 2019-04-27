package archetypeAPI.patches.classCardpoolPatches;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.CardpoolMaintenance.makeSureWeMeetMinimum;

public class CardpoolSavable implements CustomSavable<List<String>> {
    private Logger logger = LogManager.getLogger(CardpoolSavable.class.getName());
    private static ArrayList<AbstractCard> tmpTmpPool = new ArrayList<>();
    private static List<String> IDList;
    
    CardpoolSavable(ArrayList<AbstractCard> tmpPool) {
        this.tmpTmpPool.addAll(tmpPool);
        IDList = tmpTmpPool.stream().map(c -> c.cardID).collect(Collectors.toList());
    }
    
    @Override
    public List<String> onSave() {
        logger.info("Attempting to save tmpPool: " + tmpTmpPool.toString());
        logger.info("As an ID list " + IDList.toString());
        return IDList;
    }
    
    @Override
    public void onLoad(List<String> listOfIDs) {
        if (cardsOfTheArchetypesInUse.isEmpty()) {
            logger.info("In use cardpool is empty, looking for save");
            if (!listOfIDs.isEmpty()) {
                logger.info("List of ID's to load pre-load: " + listOfIDs.toString());
                
                for (String id : listOfIDs) {
                    logger.info("Attempting to load tmpPool from ID list: " + listOfIDs.toString());
                    logger.info("id: " + id);
                    cardsOfTheArchetypesInUse.addToTop(CardLibrary.getCard(id));
                }
                
                makeSureWeMeetMinimum();
                if (!cardsOfTheArchetypesInUse.isEmpty()) {
                    logger.info("Cardpool loaded successfully, initialising.");
                    CardCrawlGame.dungeon.initializeCardPools();
                }
            }
        }
    }
}