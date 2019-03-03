package archetypeAPI.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractArchetypeCard extends CustomCard {
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
}

