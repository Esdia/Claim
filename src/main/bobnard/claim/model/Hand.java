package bobnard.claim.model;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Represents a player's hand.
 * A hand is just a list of cards.
 */
public class Hand extends ArrayList<Card> {
    /**
     * Sorts the hand according to Card::compareTo.
     */
    public void sort() {
        this.sort(Card::compareTo);
    }

    /**
     * Returns a stream containing every cards of
     * the hand of the given faction.
     *
     * @param faction The faction whose cards we want to fetch
     * @return Every cards of the hand of the given faction
     */
    Stream<Card> getCards(Faction faction) {
        return this.stream().filter(c -> c.faction == faction);
    }

    /**
     * Returns the cards the player can play, given the faction
     * played by the leader.
     *
     * @param faction The faction played by the leader (null if the
     *                current player is the leader).
     * @return The cards the player can play.
     */
    Hand playableCards(Faction faction) {
        Hand cards = new Hand();

        if (faction == null) {
            cards.addAll(this);
            return cards;
        }

        getCards(faction).forEach(cards::add);

        if (cards.size() > 0) {
            if (faction != Faction.DOPPELGANGERS) {
                // We only need to add the doppelgangers, if they are not the asked faction
                getCards(Faction.DOPPELGANGERS).forEach(cards::add);
            }
        } else {
            // We can play anything
            cards.addAll(this);
        }

        return cards;
    }
}
