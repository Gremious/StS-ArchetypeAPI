package archetypeAPI.jsonClasses;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class ArchetypeStringsClass {
    public String CHARACTER;
    public String NAME;
    public String IMG;
    public AbstractCard.CardType CARD_TYPE;
    public String[] TAGS;
    public String[] CARD_IDS;

    public AbstractPlayer.PlayerClass getCHARACTER() {
        return AbstractPlayer.PlayerClass.valueOf(CHARACTER);
    }
}
