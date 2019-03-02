package archetypeAPI.archetypes.tests.brandNewMod.cards.archetypeSelectCards;

import archetypeAPI.archetypes.tests.brandNewMod.archetype.poisonArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE_CORE;

public class DiscardPoisonArchetypeSelectCard extends AbstractArchetypeCard {


    // TEXT DECLARATION

    public static final String ID = "DiscardPoisonTestCard";

    public static final String IMG = "archetypeAPIResources/images/ui/missing_texture.png";

    public static final String NAME = "Discard Poison Custom Cardpool";
    public static final String DESCRIPTION = "Add the Discard Poison card to your Card Draft Pool.";

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.GREEN;

    private static final int COST = -2;


    // /STAT DECLARATION/


    public DiscardPoisonArchetypeSelectCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        tags.add(SINGLE_CORE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public void triggerOnManualDiscard() {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void archetypeEffect() {
        poisonArchetype poisonArchetype = new poisonArchetype(true);
    }
}