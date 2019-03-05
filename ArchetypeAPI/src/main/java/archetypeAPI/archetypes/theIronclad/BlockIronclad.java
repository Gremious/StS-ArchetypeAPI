package archetypeAPI.archetypes.theIronclad;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class BlockIronclad extends abstractArchetype {
    public static ArrayList<String> blockIroncladArchetypeFiles = new ArrayList<>();
    private static String archetypeFile = "archetypeAPIResources/localization/eng/archetypes/theIronclad/block-Ironclad-Archetype.json";

    public BlockIronclad() {
        super(blockIroncladArchetypeFiles);
    }
}
