package archetypeAPI.cards.archetypeSelectionCards.theSilent;

import archetypeAPI.archetypes.theSilent.ShivSilent;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class ShivSilentArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:ShivSilentArchetypeSelectCard";
    public static final String IMG = "archetypeAPIResources/images/cards/shiv.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:FlavorSilent");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.GREEN;
    public static final CardType TYPE = CardType.ATTACK;

    public ShivSilentArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        ShivSilent shivSilent = new ShivSilent();
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