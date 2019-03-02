package archetypeAPI.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ArchetypeCardTags {
    @SpireEnum
    public static AbstractCard.CardTags BASIC;
    // For custom characters, tag the archetype selection card of the non-archetype cards/cards you want always included with this.
    // THE ARCHETYPE TAGGED WITH THIS WILL *ALWAYS* BE ADDED TO THE CARDPOOL.
    // This is also to make sure you load at least a minimal number of cards. So make sure that pool adds at least 1 of each type and rarity.

    public static AbstractCard.CardTags SINGLE;
    // For single/core archetypes. Cards that say "poison" and only add the poison archetype and nothing more.
    // KEEP IN MIND THAT IF THE PLAYER DOESN'T HAVE THE "CHOOSE ARCHETYPES ON RUN START" OPTION SELECTED,
    // ONLY CARDS TAGGED WITH THIS WILL BE IN THE RANDOM AUTO-SELECT POOL. TAG UR CARDS!
    // This is to prevent the following 2 groups from being randomly selected in place of an actual archetype:

    public static AbstractCard.CardTags CUSTOM_MIX;
    // For exmaple, an option card that says "Poison + Discard" and adds only cards in those archetypes to a run.

    public static AbstractCard.CardTags OPTION;
    // For a card that doesn't add an archetype to the pool itself, but perhaps edits it in some other way
    // For exmaple "Upgrade all poison cards if any" or something like that.

}
