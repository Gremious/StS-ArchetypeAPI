package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class ShivSilent extends abstractArchetype {
    public static ArrayList<String> shivSilentArchetypeFiles = new ArrayList<>();
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/shiv-Silent-Archetype.json";

    public ShivSilent() {
        super(shivSilentArchetypeFiles);
    }
}
