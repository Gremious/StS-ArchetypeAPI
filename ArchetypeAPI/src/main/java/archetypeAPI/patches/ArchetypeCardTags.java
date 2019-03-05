package archetypeAPI.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ArchetypeCardTags {
    @SpireEnum
    public static AbstractCard.CardTags BASIC;
    // For custom characters, this is the tag for the archetype selection card of the non-archetype cards, or, cards you want always included.
    // THE ARCHETYPE TAGGED WITH THIS WILL *ALWAYS* BE ADDED TO THE CARDPOOL WHEN ROLLED RANDOMLY.
    // This is also to make sure you load at least a minimal number of cards rather than generating new ones and

    public static AbstractCard.CardTags SINGLE;
    // For single/core archetypes. Cards that say "poison" and only add the poison archetype and nothing more.
    // KEEP IN MIND THAT IF THE PLAYER DOESN'T HAVE THE "CHOOSE ARCHETYPES ON RUN START" OPTION SELECTED,
    // ONLY CARDS TAGGED WITH THIS WILL BE IN THE RANDOM AUTO-SELECT POOL. TAG UR CARDS!
    // This is to prevent the following 2 groups from being randomly selected in place of an actual archetype:

    public static AbstractCard.CardTags INCLUDE_SUPPORT;
    // If you are adding *new* orb archetypes, tag them with this. This API checks for this tag to know
    // whether or not it can add the "Orb Support" archetype to the pool. That archetype would not be added if you don't
    // roll any orbs.

    public static AbstractCard.CardTags SUPPORT;
    // SUPPORT is excluded from the RNG selection pool unless a card with INCLUDE_SUPPORT is rolled as an archetype.
    // There are cards that support Defect Orbs but would be useless if the player has NO orbs in the game (Focus cards for example).
    // Of course, if you are making a custom character that uses MECHANIC and SUPPORT_MECHANIC - feel free to use these tags too.

    public static AbstractCard.CardTags CUSTOM_MIX;
    // For exmaple, an option card that says "Poison + Discard" and adds a specific selection of only cards in those archetypes to a run.
    // Or maybe "Nothing but Claw and Double-tap."

    public static AbstractCard.CardTags OPTION;
    // For a card that doesn't add an archetype to the pool itself, but perhaps edits it in some other way
    // For exmaple "Upgrade all poison cards if any" or something like that.

}