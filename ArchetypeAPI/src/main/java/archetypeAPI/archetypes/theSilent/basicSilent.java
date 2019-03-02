package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

public class basicSilent extends abstractArchetype {
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/basic-Silent-Archetype.json";

    public basicSilent(boolean useArchetype) {
        super(archetypeFile, useArchetype);
    }
}
