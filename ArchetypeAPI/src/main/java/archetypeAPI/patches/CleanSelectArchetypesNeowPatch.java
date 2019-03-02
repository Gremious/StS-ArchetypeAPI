package archetypeAPI.patches;

import archetypeAPI.archetypes.tests.brandNewMod.cards.DiscardPoisonArchetypeSelectCard;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;

@SpirePatch(
        clz = NeowEvent.class,
        method = "dailyBlessing"
)

public class CleanSelectArchetypesNeowPatch {

    public static void Postfix(NeowEvent __instance) {
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAA");

        CardGroup archetypeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());
        archetypeCards.addToTop(new DiscardPoisonArchetypeSelectCard());

        AbstractDungeon.gridSelectScreen.open(archetypeCards, 3, true, "Select Your Archetypes");

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (c instanceof AbstractArchetypeCard) {
                    ((AbstractArchetypeCard) c).archetypeEffect();
                }
            }
        }

    }
}