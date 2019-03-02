/*
package archetypeAPI.patches;

import archetypeAPI.archetypes.tests.brandNewMod.cards.DiscardPoisonArchetypeSelectCard;
import archetypeAPI.archetypes.tests.brandNewMod.cards.DiscardPoisonTestCard;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.neow.NeowEvent;
import javassist.CtBehavior;

import static archetypeAPI.ArchetypeAPI.selectArchetypes;

@SpirePatch(
        clz = NeowEvent.class,
        method = "dailyBlessing"
)

public class SelectArchetypesNeowPatch {

    public static void Postfix(NeowEvent __instance) {
        CardGroup archetypeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        System.out.println("NEOW ARCHETYPE LOG PATCH START");
        archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());


        System.out.println("Select Archetypes Options is: " + selectArchetypes);

        System.out.println("Open Grid Select Screen");
        AbstractDungeon.gridSelectScreen.open(archetypeCards, 3, true, "Select Your Archetypes");

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (c instanceof AbstractArchetypeCard) {
                    ((AbstractArchetypeCard) c).archetypeEffect();
                }
            }
        }

        AbstractDungeon.gridSelectScreen.selectedCards.clear();

        if (!selectArchetypes) {
            int archNumber = 3;
            for (int i = 0; i < archNumber; i++) {
                AbstractCard c = archetypeCards.getRandomCard(true);
                if (c instanceof AbstractArchetypeCard) {
                    ((AbstractArchetypeCard) c).archetypeEffect();
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            //  return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[5]};
        }
    }
}



*/
