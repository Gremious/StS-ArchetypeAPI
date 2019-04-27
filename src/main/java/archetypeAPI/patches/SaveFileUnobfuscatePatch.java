package archetypeAPI.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFileObfuscator;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(
        clz = SaveFileObfuscator.class,
        method = "encode"
)
public class SaveFileUnobfuscatePatch {
    protected static final Logger logger = LogManager.getLogger(SaveFileUnobfuscatePatch.class.getName());
    
    public static SpireReturn<String> Prefix(String s, String key) {
        logger.info(SaveFileUnobfuscatePatch.class.getSimpleName() + " triggered");
        return SpireReturn.Return(s);
    }
    
}