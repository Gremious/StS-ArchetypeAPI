package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.abstractArchetype;

import java.util.ArrayList;

public class PoisonSilent extends abstractArchetype {
    public static ArrayList<String> poisonSilentArchetypeFiles = new ArrayList<>();

    public PoisonSilent() {
        super(poisonSilentArchetypeFiles);
    }
}
