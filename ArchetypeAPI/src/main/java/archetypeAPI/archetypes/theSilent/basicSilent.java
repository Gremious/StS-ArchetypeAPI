package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

public class basicSilent extends abstractArchetype {
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/archetype-Silent-Strings.json";

    public basicSilent(boolean useArchetype) {
        super(archetypeFile, useArchetype);
    }
}
