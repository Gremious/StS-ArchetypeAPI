package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.AbstractArchetype;

import java.util.ArrayList;

public class PoisonSilent extends AbstractArchetype {
    public static ArrayList<String> poisonSilentArchetypeFiles = new ArrayList<>();

    public PoisonSilent() {
        super(poisonSilentArchetypeFiles);
    }
}
