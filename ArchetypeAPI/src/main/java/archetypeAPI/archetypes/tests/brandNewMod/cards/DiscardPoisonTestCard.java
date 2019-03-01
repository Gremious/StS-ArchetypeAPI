package archetypeAPI.archetypes.tests.brandNewMod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class DiscardPoisonTestCard extends CustomCard {


    // TEXT DECLARATION

    public static final String ID = "DiscardPoisonTestCard";

    public static final String IMG = "archetypeAPIResources/images/ui/missing_texture.png";

    public static final String NAME = "Test Arch Card";
    public static final String DESCRIPTION = "Whenever you discard this card, apply !M! poison to a rando";
    public static final String UPGRADE_DESCRIPTION = "Whenever you discard this card, apply !M! poison to a rando";

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.GREEN;

    private static final int COST = -2;

    private static final int MAGIC = 5;
    private static final int UPGRADED_PLUS_MAGIC = 6;

    // /STAT DECLARATION/


    public DiscardPoisonTestCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = MAGIC;

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public void triggerOnManualDiscard() {
        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new PoisonPower(m, p, magicNumber), magicNumber));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);

//          rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}