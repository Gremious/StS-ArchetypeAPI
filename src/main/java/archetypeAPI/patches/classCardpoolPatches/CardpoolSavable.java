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
    
    CardpoolSavable(ArrayList<AbstractCard> tmpPool) {
        this.tmpTmpPool.addAll(tmpPool);
    }
    
    @Override
    public List<String> onSave() {
        List<String> listOfIDs = tmpTmpPool.stream().map(c -> c.cardID).collect(Collectors.toList());
        logger.info("Attempting to save tmpPool: " + tmpTmpPool.toString());
        logger.info("As an ID list " + listOfIDs.toString());
        tmpTmpPool.clear();
        return listOfIDs;
    }
    
    @Override
    public void onLoad(List<String> listOfIDs) {
        for (String id : listOfIDs) {
            logger.info("Attempting to load tmpPool from ID list: " + listOfIDs.toString());
            logger.info("id: " + id);
            cardsOfTheArchetypesInUse.clear();
            cardsOfTheArchetypesInUse.addToTop(CardLibrary.getCard(id));
        }
        
        listOfIDs.clear();
        makeSureWeMeetMinimum();
        if (!cardsOfTheArchetypesInUse.isEmpty()) {
            logger.info("Cardpool loaded successfully, initialising.");
            CardCrawlGame.dungeon.initializeCardPools();
        }
    }
}