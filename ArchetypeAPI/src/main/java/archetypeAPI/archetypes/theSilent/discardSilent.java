package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

public class discardSilent extends abstractArchetype {
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/discard-Silent-Archetype.json";

    public discardSilent(boolean useArchetype) {
        super(archetypeFile, useArchetype);
    }
}
