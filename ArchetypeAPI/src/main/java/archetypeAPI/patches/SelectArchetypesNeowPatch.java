package archetypeAPI.patches;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.characters.customCharacterArchetype;
import archetypeAPI.effects.SelectArchetypeEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;

import java.util.ArrayList;

import static archetypeAPI.ArchetypeAPI.selectArchetypes;
import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

@SpirePatch(
        clz = NeowEvent.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = {
                boolean.class
        }
)

public class SelectArchetypesNeowPatch {

    public static void Postfix(NeowEvent __instance, boolean isDone) {
        if (!Settings.isEndless || AbstractDungeon.floorNum <= 1) {
            if (Settings.isStandardRun() && (!Settings.isEndless || AbstractDungeon.floorNum > 1)) { // Only the first room ever

                if (selectArchetypes) {
                    AbstractDungeon.effectList.add(new SelectArchetypeEffect());
                } else {
                    ArrayList<AbstractCard> randomArchetypes = new ArrayList<>();
                    CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    if (AbstractDungeon.player instanceof customCharacterArchetype) {
                        CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();
                        for (AbstractCard c : cardg.group) {
                            if (c.hasTag(SINGLE)) {
                                list.addToTop(c);
                            }
                        }
                    } else {
                        switch (AbstractDungeon.player.chosenClass) {
                            case IRONCLAD:
                                for (AbstractCard c : abstractArchetype.ironcladArchetypeCards.group) {
                                    if (c.hasTag(SINGLE)) {
                                        list.addToTop(c);
                                    }
                                }
                                break;
                            case THE_SILENT:
                                for (AbstractCard c : abstractArchetype.silentArchetypeCards.group) {
                                    if (c.hasTag(SINGLE)) {
                                        list.addToTop(c);
                                    }
                                }
                                break;
                            case DEFECT:
                                for (AbstractCard c : abstractArchetype.defectArchetypeCards.group) {
                                    if (c.hasTag(SINGLE)) {
                                        list.addToTop(c);
                                    }
                                }
                                break;
                            default:
                                System.out.println("Archetype selection patch says: ???????????????");
                                System.out.println("Is (AbstractDungeon.player instanceof customCharacterArchetype)?: " + ((AbstractDungeon.player instanceof customCharacterArchetype)));
                                System.out.println("AbstractDungeon.player.chosenClass: " + (AbstractDungeon.player.chosenClass.toString()));
                                break;
                        }
                    }

                    while (randomArchetypes.size() < 1) {
                        AbstractCard c = list.getRandomCard(true);
                        if (!randomArchetypes.contains(c)) {
                            randomArchetypes.add(c);
                        } else if (randomArchetypes.containsAll(list.group)) {
                            System.out.println("Added every single archetype");
                            break;
                        }
                    }
                }
            }


        }
    }
}
/*
*
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
*
*/