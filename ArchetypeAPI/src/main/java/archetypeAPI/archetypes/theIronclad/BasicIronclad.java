package archetypeAPI.archetypes.theIronclad;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class BasicIronclad extends abstractArchetype {
    public static ArrayList<String> basicIroncladArchetypeFiles = new ArrayList<>();
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theIronclad/basic-Ironclad-Archetype.json";

    public BasicIronclad() {
        super(basicIroncladArchetypeFiles);
    }
}
