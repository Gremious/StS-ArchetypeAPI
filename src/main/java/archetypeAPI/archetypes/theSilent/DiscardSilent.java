package archetypeAPI.archetypes.theSilent;

import archetypeAPI.archetypes.AbstractArchetype;

import java.util.ArrayList;

public class DiscardSilent extends AbstractArchetype {
    public static ArrayList<String> discardSilentArchetypeFiles = new ArrayList<>();
 
    public DiscardSilent() {
        super(discardSilentArchetypeFiles);
    }
}
