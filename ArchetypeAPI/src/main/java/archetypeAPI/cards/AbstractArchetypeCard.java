package archetypeAPI.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public abstract class AbstractArchetypeCard extends CustomCard {


    public AbstractArchetypeCard(final String id,
                                 final String name,
                                 final String img,
                                 final int cost,
                                 final String rawDescription,
                                 final CardType type,
                                 final CardColor color,
                                 final CardRarity rarity,
                                 final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

    }

    public abstract void archetypeEffect();


}