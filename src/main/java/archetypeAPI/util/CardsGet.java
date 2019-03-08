package archetypeAPI.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class CardsGet {
    public static CardGroup cleanCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static void populateTrulyFullClassCardList(AbstractCard.CardColor CardColor) {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(CardLibrary.getAllCards());

        for (AbstractCard c : allCards) {
            if (c.color == CardColor
                    && c.rarity != AbstractCard.CardRarity.SPECIAL
                    && c.rarity != AbstractCard.CardRarity.BASIC) {
                cleanCards.addToTop(c);
            }
        }
    }

    public static AbstractCard getSuperRandomCard(AbstractCard.CardRarity rarity) {
        if (cleanCards.isEmpty()) {
            populateTrulyFullClassCardList(AbstractDungeon.player.getCardColor());
        }
        return cleanCards.getRandomCard(true, rarity);
    }
}
