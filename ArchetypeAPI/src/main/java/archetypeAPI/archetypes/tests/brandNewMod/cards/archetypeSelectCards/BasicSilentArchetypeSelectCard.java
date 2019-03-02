package archetypeAPI.archetypes.tests.brandNewMod.cards.archetypeSelectCards;

import archetypeAPI.archetypes.theSilent.basicSilent;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class BasicSilentArchetypeSelectCard extends AbstractArchetypeCard {


    // TEXT DECLARATION

    public static final String ID = "BasicTestCard";

    public static final String IMG = "archetypeAPIResources/images/ui/missing_texture.png";

    public static final String NAME = "Basic Silent";
    public static final String DESCRIPTION = "Add the Basic Archetype to your Card Draft Pool.";

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.GREEN;

    private static final int COST = -2;


    // /STAT DECLARATION/


    public BasicSilentArchetypeSelectCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        tags.add(SINGLE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {
    }

    @Override
    public void archetypeEffect() {
        basicSilent basicSilent = new basicSilent(true);
    }
}