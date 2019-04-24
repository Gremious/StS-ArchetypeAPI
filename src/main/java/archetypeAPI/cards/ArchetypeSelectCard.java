package archetypeAPI.cards;

import archetypeAPI.archetypes.AbstractArchetype;
import archetypeAPI.jsonClasses.ArchetypeStringsClass;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArchetypeSelectCard extends AbstractArchetypeCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:ArchetypeSelect");
    public static final String TEXT[] = uiStrings.TEXT;

    private ArchetypeStringsClass stringsClass;

    public ArchetypeSelectCard(ArchetypeStringsClass archetype) {
        super("<TODO>", archetype.NAME, archetype.IMG, makeDescription(archetype), archetype.CARD_TYPE, getCardColor(archetype));

        stringsClass = archetype;

        tags.addAll(Arrays.asList(archetype.TAGS));
    }

    private static String makeDescription(ArchetypeStringsClass archetype) {
        return String.format(TEXT[1], archetype.NAME);
    }

    private static CardColor getCardColor(ArchetypeStringsClass archetype) {
        AbstractPlayer player = CardCrawlGame.characterManager.getCharacter(archetype.CHARACTER);
        if (player == null) {
            return CardColor.COLORLESS;
        }
        return player.getCardColor();
    }

    @Override
    public void loadCardImage(String img) {
        TextureAtlas cardAtlas = (TextureAtlas) ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");
        TextureAtlas.AtlasRegion atlasRegion = cardAtlas.findRegion(img);
        if (atlasRegion != null) {
            textureImg = null;
            ReflectionHacks.setPrivate(this, AbstractCard.class, "portrait", atlasRegion);
        } else {
            super.loadCardImage(img);
        }
    }

    @Override
    public void archetypeEffect() {
        AbstractArchetype.addCardsFromArchetypes(stringsClass.CARD_IDS);
    }

    @Override
    public String getTooltipName() {
        return TEXT[2];
    }

    @Override
    public String getTooltipDesc() {
        if (stringsClass.FEATURES == null || stringsClass.FEATURES.length == 0) {
            return null;
        }

        StringBuilder desc = new StringBuilder(makeDescription(stringsClass) + TEXT[3]);
        String features = Arrays.stream(stringsClass.FEATURES)
                .map(CardLibrary::getCard)
                .map(Optional::ofNullable)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(card -> {
                    String color = "g";
                    switch (card.rarity) {
                        case COMMON: color = "g"; break;
                        case UNCOMMON: color = "b"; break;
                        case RARE: color = "y"; break;
                    }
                    return FontHelper.colorString(card.name, color);
                })
                .collect(Collectors.joining(TEXT[4]));
        desc.append(features).append(TEXT[5]);
        return desc.toString();
    }
}
