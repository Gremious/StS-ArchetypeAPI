package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class DiscardSilent extends abstractArchetype {
    public static ArrayList<String> discardSilentArchetypeFiles = new ArrayList<>();

    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/discard-Silent-Archetype.json";

    public DiscardSilent() {
        super(discardSilentArchetypeFiles);
    }
}
