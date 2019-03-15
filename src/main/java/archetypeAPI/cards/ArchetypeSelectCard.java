package archetypeAPI.cards;

import archetypeAPI.archetypes.AbstractArchetype;
import archetypeAPI.jsonClasses.ArchetypeStringsClass;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ArchetypeSelectCard extends AbstractArchetypeCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:ArchetypeSelect");
    public static final String TEXT[] = uiStrings.TEXT;

    private ArchetypeStringsClass stringsClass;

    public ArchetypeSelectCard(ArchetypeStringsClass archetype) {
        super("<TODO>", archetype.NAME, archetype.IMG, makeDescription(archetype), archetype.CARD_TYPE, getCardColor(archetype));

        stringsClass = archetype;

        for (String tag : archetype.TAGS) {
            tags.add(CardTags.valueOf(tag));
        }
    }

    private static String makeDescription(ArchetypeStringsClass archetype) {
        return String.format(TEXT[0], archetype.NAME);
    }

    private static CardColor getCardColor(ArchetypeStringsClass archetype) {
        AbstractPlayer player = CardCrawlGame.characterManager.getCharacter(archetype.CHARACTER);
        if (player == null) {
            return CardColor.COLORLESS;
        }
        return player.getCardColor();
    }

    @Override
    public void archetypeEffect() {
        AbstractArchetype.addCardsFromArchetypes(stringsClass.CARD_IDS);
    }

    @Override
    public String getTooltipName() {
        return TEXT[1];
    }

    @Override
    public String getTooltipDesc() {
        // TODO
        return null;
    }
}
