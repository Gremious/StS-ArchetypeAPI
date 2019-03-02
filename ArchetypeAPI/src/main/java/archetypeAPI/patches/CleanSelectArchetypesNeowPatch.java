package archetypeAPI.patches;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.effects.SelectArchetypeEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;

import static archetypeAPI.ArchetypeAPI.selectArchetypes;

@SpirePatch(
        clz = NeowEvent.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                boolean.class
        }
)

public class CleanSelectArchetypesNeowPatch {

    public static void Postfix(NeowEvent __instance, boolean isDone) {
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("Archetype cards: " + abstractArchetype.archetypeCards);

        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (Settings.isStandardRun() && (!Settings.isEndless || AbstractDungeon.floorNum > 1)) { // Only the first room ever
                AbstractDungeon.topLevelEffectsQueue.add(new SelectArchetypeEffect());

                if (selectArchetypes) {  // [1.] If you chose to select the archetypes
                    System.out.println("You chose to select your archetype.");
                    AbstractDungeon.gridSelectScreen.open(abstractArchetype.archetypeCards, 999, true, "Select Your Archetypes");
                    System.out.println("The Grid Select Screen has now opened.");
                    System.out.println("The size of selected cards is: " + AbstractDungeon.gridSelectScreen.selectedCards.size());
                    if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        System.out.println("Archetype cards selected: " + AbstractDungeon.gridSelectScreen.selectedCards);
                        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                            System.out.println("c instance of check: " + (c instanceof AbstractArchetypeCard));

                            if (c instanceof AbstractArchetypeCard) {
                                System.out.println("Card in Loop " + c);
                                ((AbstractArchetypeCard) c).archetypeEffect();
                            }
                        }
                    }
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    System.out.println("Full list of archetypes was selected from: " + abstractArchetype.archetypeCards);
                    System.out.println("All the archetype effects should have triggered, adding to the card list");
                    System.out.println("This is the card list:");
                    System.out.println(abstractArchetype.UsedArchetypesCombined);
                    System.out.println("Proceed reinit card pools:");
                    if (!abstractArchetype.UsedArchetypesCombined.isEmpty()) {
                        CardCrawlGame.dungeon.initializeCardPools();
                    }
                } else {

                }

            }
        }
    }
}