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

    private Hand getCardsOfSameValue(Card card) {
        if (card == null) throw new IllegalArgumentException();

        return this.stream()
                .filter(c -> c.value == card.value)
                .collect(Collectors.toCollection(Hand::new));
    }

    public Card getWeakestCard() {
        Optional<Card> min = this.stream().min(Comparator.comparingInt(c -> c.value));
        return min.orElse(null);
    }

    public Hand getWeakestCards() {
        return this.getCardsOfSameValue(this.getWeakestCard());
    }

    public Card getStrongestCard() {
        Optional<Card> max = this.stream().max(Comparator.comparingInt(c -> c.value));
        return max.orElse(null);
    }

    public Hand getStrongestCards() {
        return this.getCardsOfSameValue(this.getStrongestCard());
    }

    public Hand getWeakestPlayableCards(Faction faction) {
        return playableCards(faction).getWeakestCards();
    }

    private Hand canBeat(Card card) {
        Stream<Card> canBeat = this.stream().filter(
                c -> (c.faction == card.faction || c.faction == Faction.DOPPELGANGERS)
                        && c.value > card.value
        );

        return canBeat.collect(Collectors.toCollection(Hand::new));
    }

    public Hand getWeakestToBeat(Card toBeat) {
        Hand canBeat = this.canBeat(toBeat);

        if (canBeat.size() == 0) {
            Hand cards = this.getWeakestPlayableCards(toBeat.faction);
            return cards.size() > 0 ? cards : this.getWeakestCards();
        }

        return canBeat.getWeakestCards();
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
