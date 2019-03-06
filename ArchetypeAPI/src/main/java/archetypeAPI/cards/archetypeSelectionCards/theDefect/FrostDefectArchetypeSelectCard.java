package archetypeAPI.cards.archetypeSelectionCards.theDefect;

import archetypeAPI.archetypes.theDefect.BasicDefect;
import archetypeAPI.archetypes.theDefect.FrostDefect;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class FrostDefectArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:FrostDefectArchetypeSelectCard";

    public static final String IMG = "archetypeAPIResources/images/cards/adrenaline.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:FlavorDefect");

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.BLUE;
    public static final CardType TYPE = CardType.SKILL;

    public FrostDefectArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        FrostDefect frostDefect = new FrostDefect();
    }

    @Override
    public String getTooltipName() {
        return FLAVOR_STRINGS[0];
    }

    @Override
    public String getTooltipDesc() {
        return FLAVOR_STRINGS[4];
    }

}