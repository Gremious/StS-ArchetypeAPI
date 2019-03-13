package archetypeAPI.patches;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.util.cardpoolClearance;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.rareCardPool;

@SpirePatch(
        clz = AbstractDungeon.class,
        method = "getCardFromPool",
        paramtypez = {
                AbstractCard.CardRarity.class,
                AbstractCard.CardType.class,
                boolean.class
        }
)
public class ERRORCouldNotFindRareCardOfTypePatch {
    public static int numCheck;
    protected static final Logger patchLogger = LogManager.getLogger(ERRORCouldNotFindRareCardOfTypePatch.class.getName());

    @SpireInsertPatch(
            locator = LocatorRare.class,
            localvars = "retVal"
    )
    public static SpireReturn<AbstractCard> fixRareCardPools(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng, AbstractCard retVal) {
        patchLogger.info("ERROR: Could not find Rare card of type: " + type.name() + " but Archetype API has your back.");
        boolean need = true;
        int tempCheck = 0;

        for (AbstractCard c : abstractArchetype.UsedArchetypesCombined.group) {
            if (c.type == type) {
                tempCheck++;
            }
        }

        if (type != AbstractCard.CardType.POWER) {
            if (tempCheck >= 2) {
                need = false;
            }
        } else {
            if (tempCheck >= 1) {
                need = false;
            }
        }

        if (need) {
            patchLogger.info("You need a " + type + " my dude. You only have " + tempCheck);
            if (type != AbstractCard.CardType.POWER) {
                cardpoolClearance.extendSpecificTypeWithBasics(2 - tempCheck, rarity, type);
            } else {
                cardpoolClearance.extendSpecificTypeWithBasics(1, rarity, type);
            }
        }

        for (AbstractCard c : UsedArchetypesCombined.group) {
            if (c.type == type && c.rarity == rarity) {
                patchLogger.info("We're done here.");
                return SpireReturn.Return(retVal);
            }
        }

        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            locator = LocatorUncommon.class,
            localvars = "retVal"
    )
    public static SpireReturn<AbstractCard> fixUncommonCardPools(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng, AbstractCard retVal) {
        patchLogger.info("ERROR: Could not find Rare card of type: " + type.name() + " but Archetype API has your back.");
        boolean need = true;
        int tempCheck = 0;

        for (AbstractCard c : abstractArchetype.UsedArchetypesCombined.group) {
            if (c.type == type) {
                tempCheck++;
            }
        }

        if (type != AbstractCard.CardType.POWER) {
            if (tempCheck >= 2) {
                need = false;
            }
        } else {
            if (tempCheck >= 1) {
                need = false;
            }
        }

        if (need) {
            patchLogger.info("You need a " + type + " my dude. You only have " + tempCheck);
            if (type != AbstractCard.CardType.POWER) {
                cardpoolClearance.extendSpecificTypeWithBasics(2 - tempCheck, rarity, type);
            } else {
                cardpoolClearance.extendSpecificTypeWithBasics(1, rarity, type);
            }
        }

        for (AbstractCard c : UsedArchetypesCombined.group) {
            if (c.type == type && c.rarity == rarity) {
                patchLogger.info("We're done here.");
                return SpireReturn.Return(retVal);
            }
        }

        return SpireReturn.Continue();
    }

    @SpireInsertPatch(
            locator = LocatorCommon.class,
            localvars = "retVal"
    )
    public static SpireReturn<AbstractCard> fixCommonCardPools(AbstractCard.CardRarity rarity, AbstractCard.CardType type, boolean useRng, AbstractCard retVal) {
        patchLogger.info("ERROR: Could not find Rare card of type: " + type.name() + " but Archetype API has your back.");
        boolean need = true;
        int tempCheck = 0;

        for (AbstractCard c : abstractArchetype.UsedArchetypesCombined.group) {
            if (c.type == type) {
                tempCheck++;
            }
        }

        if (type != AbstractCard.CardType.POWER) {
            if (tempCheck >= 2) {
                need = false;
            }
        } else {
            if (tempCheck >= 1) {
                need = false;
            }
        }

        if (need) {
            patchLogger.info("You need a " + type + " my dude. You only have " + tempCheck);
            if (type != AbstractCard.CardType.POWER) {
                cardpoolClearance.extendSpecificTypeWithBasics(2 - tempCheck, rarity, type);
            } else {
                cardpoolClearance.extendSpecificTypeWithBasics(1, rarity, type);
            }
        }

        for (AbstractCard c : UsedArchetypesCombined.group) {
            if (c.type == type && c.rarity == rarity) {
                patchLogger.info("We're done here.");
                return SpireReturn.Return(retVal);
            }
        }

        return SpireReturn.Continue();
    }


    private static class LocatorRare extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
            //  return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }

    private static class LocatorUncommon extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
            //  return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
        }
    }

    private static class LocatorCommon extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Logger.class, "info");
            //  return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[2]};
        }
    }
}

