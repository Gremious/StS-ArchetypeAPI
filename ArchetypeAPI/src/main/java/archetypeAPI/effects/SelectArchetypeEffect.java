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

import static archetypeAPI.ArchetypeAPI.selectArchetypes;

public class SelectArchetypeEffect extends AbstractGameEffect {

    public SelectArchetypeEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = false;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (selectArchetypes) {
                System.out.println("You chose to select your archetype.");
                AbstractDungeon.gridSelectScreen.open(abstractArchetype.archetypeCards, 999, true, "Select Your Archetypes");
                System.out.println("The Grid Select Screen has now opened.");
                this.duration -= Gdx.graphics.getDeltaTime();
                if (this.duration < 0.0F) {
                    this.isDone = true;
                }

            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
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
                System.out.println("Full list of archetypes was selected from: " + abstractArchetype.archetypeCards);
                System.out.println("All the archetype effects should have triggered, adding to the card list");
                System.out.println("This is the card list:");
                System.out.println(abstractArchetype.UsedArchetypesCombined);
                System.out.println("Proceed reinit card pools:");


                if (!abstractArchetype.UsedArchetypesCombined.isEmpty()) {
                    CardCrawlGame.dungeon.initializeCardPools();
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    @Override
    public void dispose() {
    }
}
