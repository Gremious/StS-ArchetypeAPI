package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class basicSilent extends abstractArchetype {
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/basic-Silent-Archetype.json";

    public basicSilent() {
        super(archetypeFile);
    }
}
