package archetypeAPI.cards.archetypeSelectionCards.theDefect;

import archetypeAPI.archetypes.theDefect.BasicDefect;
import archetypeAPI.archetypes.theDefect.DarkDefect;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import static archetypeAPI.patches.ArchetypeCardTags.*;

public class DarkDefectArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:DarkDefectArchetypeSelectCard";

    public static final String IMG = "archetypeAPIResources/images/cards/adrenaline.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:FlavorDefect");

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.BLUE;
    public static final CardType TYPE = CardType.SKILL;

    public DarkDefectArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
        tags.add(INCLUDE_SUPPORT);
    }

    @Override
    public void archetypeEffect() {
        DarkDefect darkDefect = new DarkDefect();
    }

    @Override
    public String getTooltipName() {
        return FLAVOR_STRINGS[0];
    }

    @Override
    public String getTooltipDesc() {
        return FLAVOR_STRINGS[3];
    }

}