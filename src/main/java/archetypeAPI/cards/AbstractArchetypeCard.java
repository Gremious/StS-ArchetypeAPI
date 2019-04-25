package archetypeAPI.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractArchetypeCard extends CustomCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:ArchetypeSelect");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public AbstractArchetypeCard(final String id,
                                 final String name,
                                 final String img,
                                 final String rawDescription,
                                 final CardType type,
                                 final CardColor color) {
        super(id, name, img, -2, rawDescription, type, color, CardRarity.SPECIAL, CardTarget.NONE);
    }

    public abstract void archetypeEffect();


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {
    }

    //  public abstract void setCanSoloUse();

    //   public abstract boolean getCanSoloUse();

    public abstract String getTooltipName();

    public abstract String getTooltipDesc();

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        String tooltipName;
        String tooltipDesc;

        if (getTooltipName() != null) {
            tooltipName = getTooltipName();
        } else {
            tooltipName = FLAVOR_STRINGS[2];
        }

        if (getTooltipDesc() != null) {
            tooltipDesc = getTooltipDesc();
        } else {
            tooltipDesc = FLAVOR_STRINGS[0];
        }

        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(tooltipName, tooltipDesc));
        return tips;
    }
}

