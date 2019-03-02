package archetypeAPI.effects;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.characters.customCharacterArchetype;
import archetypeAPI.jsonClasses.uiStrings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;
import static archetypeAPI.archetypes.abstractArchetype.removeDupes;

public class SelectArchetypeEffect extends AbstractGameEffect {
    private boolean cardsWereUsed;
    private boolean openedGridScreen;
    private String gridSelectText;

    public SelectArchetypeEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
        cardsWereUsed = false;
        openedGridScreen = false;

        InputStream in = abstractArchetype.class.getResourceAsStream("/archetypeAPIResources/localization/eng/gridSelect-Strings.json");
        uiStrings gridSelectText = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), uiStrings.class);
        this.gridSelectText = gridSelectText.TEXT;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.openedGridScreen && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                tickDuration();
            } else if (!openedGridScreen) {
                UsedArchetypesCombined.clear();


                if (AbstractDungeon.player instanceof customCharacterArchetype) {
                    CardGroup cardg = ((customCharacterArchetype) AbstractDungeon.player).getArchetypeSelectionCardsPool();
                    AbstractDungeon.gridSelectScreen.open(cardg, 999, true, gridSelectText);
                    this.openedGridScreen = true;
                } else {
                    switch (AbstractDungeon.player.chosenClass) {
                        case IRONCLAD:
                            AbstractDungeon.gridSelectScreen.open(abstractArchetype.ironcladArchetypeSelectCards, 999, true, gridSelectText);
                            this.openedGridScreen = true;
                            break;
                        case THE_SILENT:
                            AbstractDungeon.gridSelectScreen.open(abstractArchetype.silentArchetypeSelectCards, 999, true, gridSelectText);
                            this.openedGridScreen = true;
                            break;
                        case DEFECT:
                            AbstractDungeon.gridSelectScreen.open(abstractArchetype.defectArchetypeSelectCards, 999, true, gridSelectText);
                            this.openedGridScreen = true;
                            break;
                        default:
                            System.out.println("Archetype selection effect says: ???????????????");
                            System.out.println("Is (AbstractDungeon.player instanceof customCharacterArchetype)?: " + ((AbstractDungeon.player instanceof customCharacterArchetype)));
                            System.out.println("AbstractDungeon.player.chosenClass: " + (AbstractDungeon.player.chosenClass.toString()));
                            System.out.println("If top one is false, and bottom one isn't a base-game character, something is really wrong.");
                            break;
                    }

                }

            }
        } else {
            if (!cardsWereUsed) {
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        if (c instanceof AbstractArchetypeCard) {
                            System.out.println("Card in Loop " + c);
                            ((AbstractArchetypeCard) c).archetypeEffect();
                        }
                    }
                }
                System.out.println("All the archetype effects should have triggered, adding to the card list");
                System.out.println("This is the card list pre-dupe removal:");
                System.out.println(UsedArchetypesCombined);
                System.out.println("This is the card list post-dupe removal:");
                removeDupes(UsedArchetypesCombined);
                System.out.println("Writing to card pools.");

                if (!UsedArchetypesCombined.isEmpty()) {
                    CardCrawlGame.dungeon.initializeCardPools();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                cardsWereUsed = true;
            }

            tickDuration();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    public void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void dispose() {
    }
}
