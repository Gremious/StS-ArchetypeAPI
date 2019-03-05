package archetypeAPI.archetypes.theIronclad;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class StrengthIronclad extends abstractArchetype {
    public static ArrayList<String> strengthIroncladArchetypeFiles = new ArrayList<>();

    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theIronclad/strength-Ironclad-Archetype.json";

    public StrengthIronclad() {
        super(strengthIroncladArchetypeFiles);
    }
}
