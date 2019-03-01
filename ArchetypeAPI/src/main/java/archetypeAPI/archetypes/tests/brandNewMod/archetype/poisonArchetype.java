package archetypeAPI.archetypes.tests.brandNewMod.archetype;

import archetypeAPI.archetypes.abstractArchetype;

public class poisonArchetype extends abstractArchetype {
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/test_Archetype-Json.json";

    public poisonArchetype(boolean useArchetype) {
        super(archetypeFile, useArchetype);
    }
}
