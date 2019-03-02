package archetypeAPI.patches;

import archetypeAPI.ArchetypeAPI;
import archetypeAPI.archetypes.tests.brandNewMod.cards.DiscardPoisonArchetypeSelectCard;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
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

    public static void Postfix(NeowEvent __instance,  boolean isDone) {
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");


        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (!Settings.isStandardRun() && (!Settings.isEndless || AbstractDungeon.floorNum > 1)) {

                if (!gridSelectUsed) {
                    AbstractDungeon.gridSelectScreen.open(ArchetypeAPI.archetypeCards, 999, true, "Select Your Archetypes");

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

            }

        }
    }
}