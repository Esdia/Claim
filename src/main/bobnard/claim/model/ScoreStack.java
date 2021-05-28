package bobnard.claim.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * Represents the score of a player.
 */
public class ScoreStack extends Stack<Card> {
    private final HashMap<Faction, Integer> occ;

    /**
     * Creates a new score stack.
     */
    ScoreStack() {
        super();

        this.occ = new HashMap<>();

        for (Faction faction : Faction.values()) {
            this.occ.put(faction, 0);
        }
    }

    //region UTILS
    private Stream<Card> getCards(Faction faction) {
        return this.stream().filter(c -> c.faction == faction);
    }

    /**
     * Returns the value of the strongest card of
     * the given faction in the score stack.
     *
     * @param faction The faction we want to get the max value of.
     * @return the value of the strongest card of the faction
     * in the score stack. -1 if the player has no card of this faction.
     */
    int maxValueFaction(Faction faction) {
        Optional<Card> maxCard = this.getCards(faction).max(Card::compareTo);

        return maxCard.map(card -> card.value).orElse(-1);
    }
    //endregion

    //region GETTERS

    /**
     * Returns the number of cards of a faction in the score stack).
     *
     * @param faction The faction we want to count the cards of.
     * @return The number of cards of the faction in the score stack.
     */
    public int getNbCardsFaction(Faction faction) {
        return this.occ.get(faction);
    }
    //endregion

    //region OVERRIDES

    /**
     * Adds a card to the score stack.
     *
     * @param card The card to be added
     * @return The added card.
     */
    @Override
    public Card push(Card card) {
        super.push(card);
        this.occ.computeIfPresent(card.faction, (k, v) -> v + 1);
        return card;
    }

    /**
     * Removes the last card from the score stack.
     *
     * @return The last card from the score stack.
     */
    @Override
    public Card pop() {
        Card card = super.pop();
        this.occ.computeIfPresent(card.faction, (k, v) -> v - 1);
        return card;
    }

    /**
     * Clears the score stack
     */
    @Override
    public void clear() {
        super.clear();
        this.occ.replaceAll((f, v) -> 0);
    }
    //endregion
}
