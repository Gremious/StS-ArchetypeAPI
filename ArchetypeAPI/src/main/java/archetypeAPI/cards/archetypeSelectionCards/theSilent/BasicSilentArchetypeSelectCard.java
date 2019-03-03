package archetypeAPI.cards.archetypeSelectionCards.theSilent;

import archetypeAPI.archetypes.theSilent.basicSilent;
import archetypeAPI.cards.AbstractArchetypeCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class BasicSilentArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:BasicSilentArchetypeSelectCard";

    public static final String IMG = "archetypeAPIResources/images/cards/adrenaline.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:Flavor");

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.GREEN;
    public static final CardType TYPE = CardType.SKILL;

    public BasicSilentArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        basicSilent basicSilent = new basicSilent();
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], FLAVOR_STRINGS[1]));
        return tips;
    }

}