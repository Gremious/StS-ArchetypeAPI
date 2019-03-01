package archetypeAPI.actions.ui;

import archetypeAPI.ArchetypeAPI;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static archetypeAPI.ArchetypeAPI.selectArchetypes;

public class SelectArchetypeAction extends AbstractGameAction {

    public SelectArchetypeAction() {
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (selectArchetypes) {
                AbstractDungeon.gridSelectScreen.open(ArchetypeAPI.archetypeCards, 999, true, "Select Your Archetypes");
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                this.tickDuration();
            } else {
                this.tickDuration();
            }
        } else {
            if (selectArchetypes) {
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        if (c instanceof AbstractArchetypeCard) {
                            ((AbstractArchetypeCard) c).archetypeEffect();
                        }
                    }
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
            } else {
                this.tickDuration();
            }
        }
    }

}
