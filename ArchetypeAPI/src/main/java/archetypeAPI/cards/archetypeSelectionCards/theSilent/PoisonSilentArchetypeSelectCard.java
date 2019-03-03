package archetypeAPI.cards.archetypeSelectionCards.theSilent;

import archetypeAPI.archetypes.theSilent.poisonSilent;
import archetypeAPI.cards.AbstractArchetypeCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class PoisonSilentArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:PoisonSilentArchetypeSelectCard";
    public static final String IMG = "archetypeAPIResources/images/cards/noxious_fumes.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:Flavor");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.GREEN;
    public static final CardType TYPE = CardType.POWER;


    public PoisonSilentArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        poisonSilent poisonSilent = new poisonSilent();
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