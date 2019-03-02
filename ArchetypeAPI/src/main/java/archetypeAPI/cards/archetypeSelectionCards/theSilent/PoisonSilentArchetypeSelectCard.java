package archetypeAPI.cards.archetypeSelectionCards.theSilent;

import archetypeAPI.archetypes.theSilent.poisonSilent;
import archetypeAPI.cards.AbstractArchetypeCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class PoisonSilentArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:BasicSilentArchetypeSelectCard";
    public static final String IMG = "archetypeAPIResources/images/ui/missing_texture.png";
    public static final String NAME = "Basic Silent";
    public static final String DESCRIPTION = "Add the Basic Archetype to your Card Draft Pool.";

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:Flavor");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.GREEN;


    public PoisonSilentArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        poisonSilent poisonSilent = new poisonSilent(true);
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], FLAVOR_STRINGS[1]));
        return tips;
    }

}