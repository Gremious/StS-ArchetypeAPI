package archetypeAPI.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ArchetypeCardTags {
    @SpireEnum
    public static AbstractCard.CardTags SINGLE_CORE;
    // For single/core archetypes. Cards that say "poison" and only add the poison archetype and nothing more.
    // KEEP IN MIND THAT IF THE PLAYER DOESN'T HAVE THE "CHOOSE ARCHETYPES ON RUN START" OPTION SELECTED,
    // ONLY CARDS TAGGED WITH THIS WILL BE IN THE RANDOM AUTO-SELECT POOL. TAG UR CARDS.

    public static AbstractCard.CardTags PRE_MIX;
    // For exmaple, an option card that says "Poison + Discard" and adds both of those + , say, basic as a run

    public static AbstractCard.CardTags OPTION;
    // For a card that doesn't add an archetype to the pool itself, but perhaps edits it in some other way
    // For exmaple "Upgrade all poison cards if any" or something like that.

}
