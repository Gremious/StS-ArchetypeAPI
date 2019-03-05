package archetypeAPI.archetypes.theIronclad;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class SelfDamageIronclad extends abstractArchetype {
    public static ArrayList<String> selfDamageIroncladArchetypeFiles = new ArrayList<>();
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theIronclad/selfDamage-Ironclad-Archetype.json";

    public SelfDamageIronclad() {
        super(selfDamageIroncladArchetypeFiles);
    }
}
