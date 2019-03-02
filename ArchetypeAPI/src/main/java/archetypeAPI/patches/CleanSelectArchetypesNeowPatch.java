package archetypeAPI.patches;

import archetypeAPI.ArchetypeAPI;
import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.archetypes.tests.brandNewMod.cards.archetypeSelectCards.DiscardPoisonArchetypeSelectCard;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;

@SpirePatch(
        clz = NeowEvent.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                boolean.class
        }
)

public class CleanSelectArchetypesNeowPatch {
    private static boolean gridSelectUsed;

    public static void Postfix(NeowEvent __instance, boolean isDone) {
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        abstractArchetype.archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        abstractArchetype.archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        abstractArchetype.archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        System.out.println("Archetype cards: " + abstractArchetype.archetypeCards);

        System.out.println("Settings.isEndless: " + Settings.isEndless);
        System.out.println("AbstractDungeon.floorNum: " + AbstractDungeon.floorNum);
        System.out.println("!Settings.isStandardRun(): " + Settings.isStandardRun());
        System.out.println("Settings.isEndless: " + Settings.isEndless);
        System.out.println("AbstractDungeon.floorNum: " + AbstractDungeon.floorNum);

        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (Settings.isStandardRun() && (!Settings.isEndless || AbstractDungeon.floorNum > 1)) {

                if (!gridSelectUsed) {
                    AbstractDungeon.gridSelectScreen.open(abstractArchetype.archetypeCards, 999, true, "Select Your Archetypes");

                    if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {

                        System.out.println("Cards Selected: " + AbstractDungeon.gridSelectScreen.selectedCards);

                        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                            if (c instanceof AbstractArchetypeCard) {
                                System.out.println("Card in Loop " + c);
                                ((AbstractArchetypeCard) c).archetypeEffect();
                                AbstractDungeon.topLevelEffects.add(new RoomTintEffect(Color.GREEN, 1.0f));
                            }
                        }

                        gridSelectUsed = true;
                    }

                }
                if (ArchetypeAPI.selectArchetypes
                        && !abstractArchetype.archetypeCards.isEmpty()
                        && !abstractArchetype.UsedArchetypesCombined.isEmpty()) {
                    System.out.println("Full list of archetypes was selected from: " + abstractArchetype.archetypeCards);
                    System.out.println("Full list of cards added: " + abstractArchetype.UsedArchetypesCombined);

                    CardCrawlGame.dungeon.initializeCardPools();
                }
            }
        }
    }
}