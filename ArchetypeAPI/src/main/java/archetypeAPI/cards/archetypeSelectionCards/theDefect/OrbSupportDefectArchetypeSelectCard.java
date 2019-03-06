package archetypeAPI.cards.archetypeSelectionCards.theDefect;

import archetypeAPI.archetypes.theDefect.BasicDefect;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class OrbSupportDefectArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:BasicDefectArchetypeSelectCard";

    public static final String IMG = "archetypeAPIResources/images/cards/adrenaline.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:Flavor");

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.BLUE;
    public static final CardType TYPE = CardType.SKILL;

    public OrbSupportDefectArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        BasicDefect basicDefect = new BasicDefect();
    }

    @Override
    public String getTooltipName() {
        return FLAVOR_STRINGS[0];
    }

    @Override
    public String getTooltipDesc() {
        return FLAVOR_STRINGS[1];
    }

}