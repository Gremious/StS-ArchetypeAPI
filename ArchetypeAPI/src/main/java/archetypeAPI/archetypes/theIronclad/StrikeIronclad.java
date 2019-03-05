package archetypeAPI.archetypes.theIronclad;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class StrikeIronclad extends abstractArchetype {
    public static ArrayList<String> strikeIroncladArchetypeFiles = new ArrayList<>();
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theIronclad/strike-Ironclad-Archetype.json";

    public StrikeIronclad() {
        super(strikeIroncladArchetypeFiles);
    }
}
