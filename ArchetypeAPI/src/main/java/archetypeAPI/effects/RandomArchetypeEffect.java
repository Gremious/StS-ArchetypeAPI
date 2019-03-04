package archetypeAPI.effects;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.characters.customCharacterArchetype;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static archetypeAPI.archetypes.abstractArchetype.removeDupes;
import static archetypeAPI.patches.ArchetypeCardTags.BASIC;
import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;
import static archetypeAPI.util.cardpoolClearance.extendSpecificRarityWithBasics;

public class RandomArchetypeEffect extends AbstractGameEffect {
    // This is totally an effect. Yes.
    private boolean needReinst = false;

    public RandomArchetypeEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            UsedArchetypesCombined.clear();
            ArrayList<AbstractCard> randomArchetypes = new ArrayList<>();
            CardGroup list = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            if (AbstractDungeon.player instanceof customCharacterArchetype) {
                CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();
                int defaultNum = ((customCharacterArchetype) AbstractDungeon.player).numberOfDefaultArchetypes();

                for (AbstractCard basicCheckCard : cardg.group) {
                    if (basicCheckCard.hasTag(BASIC)) {
                        list.addToTop(basicCheckCard);
                    }
                }
                for (AbstractCard c : cardg.group) {
                    if (c.hasTag(SINGLE)) {
                        list.addToTop(c);
                    }
                }
                while (randomArchetypes.size() < defaultNum) {
                    AbstractCard ca = list.getRandomCard(true);
                    if (!randomArchetypes.contains(ca)) {
                        randomArchetypes.add(ca);
                    } else if (randomArchetypes.containsAll(list.group)) {
                        System.out.println("Added every single archetype");
                        break;
                    }
                }
            } else {
                switch (AbstractDungeon.player.chosenClass) {
                    case IRONCLAD:
                        for (AbstractCard basicCheckCard : abstractArchetype.ironcladArchetypeSelectCards.group) {
                            if (basicCheckCard.hasTag(BASIC)) {
                                list.addToTop(basicCheckCard);
                            }
                        }
                        for (AbstractCard c : abstractArchetype.ironcladArchetypeSelectCards.group) {
                            if (c.hasTag(SINGLE)) {
                                list.addToTop(c);
                            }

                            while (randomArchetypes.size() < 6) {
                                AbstractCard ca = list.getRandomCard(true);
                                if (!randomArchetypes.contains(ca)) {
                                    randomArchetypes.add(ca);
                                } else if (randomArchetypes.containsAll(list.group)) {
                                    System.out.println("Added every single archetype");
                                    break;
                                }
                            }
                        }
                        break;
                    case THE_SILENT:
                        for (AbstractCard basicCheckCard : abstractArchetype.silentArchetypeSelectCards.group) {
                            if (basicCheckCard.hasTag(BASIC)) {
                                list.addToTop(basicCheckCard);
                            }
                        }
                        for (AbstractCard c : abstractArchetype.silentArchetypeSelectCards.group) {
                            if (c.hasTag(SINGLE)) {
                                list.addToTop(c);
                            }

                            while (randomArchetypes.size() < 5) {
                                AbstractCard ca = list.getRandomCard(true);
                                if (!randomArchetypes.contains(ca)) {
                                    randomArchetypes.add(ca);
                                } else if (randomArchetypes.containsAll(list.group)) {
                                    System.out.println("Added every single archetype");
                                    break;
                                }
                            }
                        }
                        break;
                    case DEFECT:
                        for (AbstractCard basicCheckCard : abstractArchetype.defectArchetypeSelectCards.group) {
                            if (basicCheckCard.hasTag(BASIC)) {
                                list.addToTop(basicCheckCard);
                            }
                        }
                        for (AbstractCard c : abstractArchetype.defectArchetypeSelectCards.group) {
                            if (c.hasTag(SINGLE)) {
                                list.addToTop(c);
                            }

                            while (randomArchetypes.size() < 6) {
                                AbstractCard ca = list.getRandomCard(true);
                                if (!randomArchetypes.contains(ca)) {
                                    randomArchetypes.add(ca);
                                } else if (randomArchetypes.containsAll(list.group)) {
                                    System.out.println("Added every single archetype");
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("Archetype selection patch says: ???????????????");
                        System.out.println("Is (AbstractDungeon.player instanceof customCharacterArchetype)?: " + ((AbstractDungeon.player instanceof customCharacterArchetype)));
                        System.out.println("AbstractDungeon.player.chosenClass: " + (AbstractDungeon.player.chosenClass.toString()));
                        System.out.println("If top one is false, and bottom one isn't a base-game character, something is really wrong.");
                        break;
                }

                for (AbstractCard c : list.group) {
                    if (c instanceof AbstractArchetypeCard) {
                        System.out.println("Activating " + c);
                        ((AbstractArchetypeCard) c).archetypeEffect();
                    }
                }

                System.out.println("All the archetype effects should have triggered, adding to the card list");
                System.out.println("This is the card list pre-dupe removal:");
                System.out.println(UsedArchetypesCombined);
                removeDupes(UsedArchetypesCombined);
                System.out.println("This is the card list post-dupe removal:");
                System.out.println(UsedArchetypesCombined);
                System.out.println("Writing to card pools.");


                if (!UsedArchetypesCombined.isEmpty()) {
                    CardCrawlGame.dungeon.initializeCardPools();
                }

                CheckPools();

                if (needReinst && !UsedArchetypesCombined.isEmpty()) {
                    System.out.println("Card Pool too small! Adding some basic cards.");
                    CardCrawlGame.dungeon.initializeCardPools();
                }

            }


            tickDuration();
        }
        tickDuration();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    private void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void dispose() {
    }

    private void CheckPools() {
        ArrayList<AbstractCard> commonCheck = new ArrayList<>();
        ArrayList<AbstractCard> uncommonCheck = new ArrayList<>();
        ArrayList<AbstractCard> rareCheck = new ArrayList<>();

        for (AbstractCard ca : UsedArchetypesCombined.group) {
            if (ca.rarity == AbstractArchetypeCard.CardRarity.COMMON) commonCheck.add(ca);
            if (ca.rarity == AbstractArchetypeCard.CardRarity.UNCOMMON) uncommonCheck.add(ca);
            if (ca.rarity == AbstractArchetypeCard.CardRarity.RARE) rareCheck.add(ca);
        }

        if (commonCheck.size() < 3) {
            needReinst = true;

            for (int i = commonCheck.size(); i < 3; i++) {
                extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.COMMON);
            }

        }
        if (uncommonCheck.size() < 3) {
            needReinst = true;

            for (int i = commonCheck.size(); i < 3; i++) {
                extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.UNCOMMON);
            }

        }
        if (rareCheck.size() < 3) {
            needReinst = true;
            for (int i = commonCheck.size(); i < 3; i++) {
                extendSpecificRarityWithBasics(1, AbstractCard.CardRarity.RARE);
            }
        }
    }

}