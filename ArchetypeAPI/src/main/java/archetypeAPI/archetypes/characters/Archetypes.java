package archetypeAPI.archetypes.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Archetypes {
    protected static ArrayList<AbstractCard> UsedArchetypesCombined = new ArrayList<>();

    protected static void addToWholeFinalList(ArrayList<AbstractCard> fill) {
        UsedArchetypesCombined.addAll(fill);
    }

    public static ArrayList<AbstractCard> getArchetypes() {
        return UsedArchetypesCombined;
    }

    protected static ArrayList<AbstractCard> removeDupes() {
        Set<AbstractCard> dupeRemoveSet = new LinkedHashSet<>();
        dupeRemoveSet.addAll(UsedArchetypesCombined);
        UsedArchetypesCombined.clear();
        UsedArchetypesCombined.addAll(dupeRemoveSet);
        dupeRemoveSet.clear();
        return UsedArchetypesCombined;
    }

}
