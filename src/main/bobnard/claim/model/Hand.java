package bobnard.claim.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a player's hand.
 * A hand is just a list of cards.
 */
public class Hand extends ArrayList<Card> implements Serializable {
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
    public Stream<Card> getCards(Faction faction) {
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

    public Card getWeakestCard() {
        Optional<Card> min = this.stream().min(Comparator.comparingInt(c -> c.value));
        return min.orElse(null);
    }

    public Card getStrongestCard() {
        Optional<Card> max = this.stream().max(Comparator.comparingInt(c -> c.value));
        return max.orElse(null);
    }

    public Card getWeakestPlayableCard(Faction faction) {
        return playableCards(faction).getWeakestCard();
    }

    private ArrayList<Card> canBeat(Card card) {
        Stream<Card> canBeat = this.stream().filter(
                c -> (c.faction == card.faction || c.faction == Faction.DOPPELGANGERS)
                        && c.value > card.value
        );

        return canBeat.collect(Collectors.toCollection(ArrayList::new));
    }

    public Card getWeakestToBeat(Card toBeat) {
        ArrayList<Card> canBeat = this.canBeat(toBeat);

        if (canBeat.size() == 0) {
            Card card = this.getWeakestPlayableCard(toBeat.faction);
            return card != null ? card : this.getWeakestCard();
        }

        Optional<Card> weakestToBeat = canBeat.stream().min(Card::compareTo);
        return weakestToBeat.orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String valStr;
        for (Faction faction : Faction.values()) {
            valStr = this.getCards(faction).collect(Collectors.toCollection(ArrayList::new)).toString();
            str.append(valStr);
            str.append("\n");
        }
        return str.toString();
    }
}
