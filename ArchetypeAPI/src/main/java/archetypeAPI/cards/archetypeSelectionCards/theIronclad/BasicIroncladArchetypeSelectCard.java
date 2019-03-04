package archetypeAPI.cards.archetypeSelectionCards.theIronclad;

import archetypeAPI.archetypes.theIronclad.basicIronclad;
import archetypeAPI.cards.AbstractArchetypeCard;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class BasicIroncladArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:BasicIroncladArchetypeSelectCard";

    public static final String IMG = "archetypeAPIResources/images/cards/adrenaline.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:FlavorIronclad");

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.RED;
    public static final CardType TYPE = CardType.ATTACK;

    public BasicIroncladArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        this.assetUrl = IMG;
        Object cardAtlas = ReflectionHacks.getPrivate(this, AbstractCard.class, "cardAtlas");
        this.portrait = ((TextureAtlas)cardAtlas).findRegion(assetUrl);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        basicIronclad basicIronclad = new basicIronclad();
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