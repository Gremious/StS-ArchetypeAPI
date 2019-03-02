package archetypeAPI.characters;

import com.megacrit.cardcrawl.cards.CardGroup;

public interface customCharacterArchetype {
    CardGroup getArchetypeSelectionCardsPool();

    int numberOfDefaultArchetypes(); // Retuns the number of max archetypes to be loaded when archetypes are randomly selected.
}
