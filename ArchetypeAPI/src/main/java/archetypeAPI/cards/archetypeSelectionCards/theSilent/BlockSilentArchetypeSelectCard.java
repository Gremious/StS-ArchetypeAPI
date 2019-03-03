package archetypeAPI.cards.archetypeSelectionCards.theSilent;

import archetypeAPI.archetypes.theSilent.blockSilent;
import archetypeAPI.archetypes.theSilent.shivSilent;
import archetypeAPI.cards.AbstractArchetypeCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class BlockSilentArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:BlockSilentArchetypeSelectCard";
    public static final String IMG = "archetypeAPIResources/images/cards/blur.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:Flavor");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.GREEN;
    public static final CardType TYPE = CardType.ATTACK;

    public BlockSilentArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        blockSilent blockSilent = new blockSilent();
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], FLAVOR_STRINGS[5]));
        return tips;
    }

}