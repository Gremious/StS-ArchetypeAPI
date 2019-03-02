package archetypeAPI.effects;

import archetypeAPI.archetypes.abstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SelectArchetypeEffect extends AbstractGameEffect {
    private boolean cardsWereUsed;
    private boolean openedGridScreen;

    public SelectArchetypeEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
        cardsWereUsed = false;
        openedGridScreen = false;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.openedGridScreen && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                tickDuration();
            } else if (!openedGridScreen) {
                AbstractDungeon.gridSelectScreen.open(abstractArchetype.archetypeCards, 999, true, "Select Your Archetypes");
                this.openedGridScreen = true;
            }
        } else {
            if (!cardsWereUsed) {
                System.out.println("The size of selected cards is: " + AbstractDungeon.gridSelectScreen.selectedCards.size());
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        if (c instanceof AbstractArchetypeCard) {
                            System.out.println("Card in Loop " + c);
                            ((AbstractArchetypeCard) c).archetypeEffect();
                        }
                    }
                }
                System.out.println("Full list of archetypes was selected from: " + abstractArchetype.archetypeCards);
                System.out.println("All the archetype effects should have triggered, adding to the card list");
                System.out.println("This is the card list:");
                System.out.println(abstractArchetype.UsedArchetypesCombined);
                System.out.println("Proceed reinit card pools:");


                if (!abstractArchetype.UsedArchetypesCombined.isEmpty()) {
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
