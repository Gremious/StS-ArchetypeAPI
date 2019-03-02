package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

public class poisonSilent extends abstractArchetype {
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/poison-Silent-Archetype.json";

    public poisonSilent(boolean useArchetype) {
        super(archetypeFile, useArchetype);
    }
}
