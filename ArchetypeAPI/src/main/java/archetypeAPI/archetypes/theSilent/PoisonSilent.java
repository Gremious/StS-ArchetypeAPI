package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class PoisonSilent extends abstractArchetype {
    public static ArrayList<String> poisonSilentArchetypeFiles = new ArrayList<>();

    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theSilent/poison-Silent-Archetype.json";

    public PoisonSilent() {
        super(poisonSilentArchetypeFiles);
    }
}
