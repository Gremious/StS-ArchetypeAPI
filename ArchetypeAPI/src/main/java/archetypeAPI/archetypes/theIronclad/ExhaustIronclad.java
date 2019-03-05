package archetypeAPI.archetypes.theIronclad;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class ExhaustIronclad extends abstractArchetype {
    public static ArrayList<String> exhaustIroncladArchetypeFiles = new ArrayList<>();
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theIronclad/exhaust-Ironclad-Archetype.json";

    public ExhaustIronclad() {
        super(exhaustIroncladArchetypeFiles);
    }
}
