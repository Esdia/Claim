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

    /**
     * Returns the hand's weakest card according only to its value.
     * <p>
     * Since the comparator use strict comparisons, it strictly follows
     * the faction's declaration order :
     * goblins < knights < undeads < dwarves < doppelgangers
     *
     * @return the hand's weakest card.
     * @see Hand#getStrongestCard()
     * @see Hand#getWeakestCards()
     */
    public Card getWeakestCard() {
        Optional<Card> min = this.stream().min(Comparator.comparingInt(c -> c.value));
        return min.orElse(null);
    }

    /**
     * Returns every card of the same value as the weakest card.
     * <p>
     * By creating a list of the cards of the same value, we get
     * rid of the faction's declaration order, meaning that, for
     * example, Knights 1 and Doppelgangers 1 have the same value.
     *
     * @return The hand's weakest cards.
     * @see Hand#getWeakestCard()
     * @see Hand#getStrongestCards()
     */
    public Hand getWeakestCards() {
        return this.getCardsOfSameValue(this.getWeakestCard());
    }

    /**
     * Returns the hand's strongest card according only to its value.
     * <p>
     * Since the comparator use strict comparisons, it strictly follows
     * the faction's declaration order :
     * goblins > knights > undeads > dwarves > doppelgangers
     *
     * @return the hand's strongest card.
     * @see Hand#getWeakestCard()
     * @see Hand#getStrongestCards()
     */
    public Card getStrongestCard() {
        Optional<Card> max = this.stream().max(Comparator.comparingInt(c -> c.value));
        return max.orElse(null);
    }

    /**
     * Returns every card of the same value as the strongest card.
     * <p>
     * By creating a list of the cards of the same value, we get
     * rid of the faction's declaration order, meaning that, for
     * example, Dwarves 8 and Undeads 8 have the same value.
     *
     * @return The hand's strongest cards.
     * @see Hand#getStrongestCard()
     * @see Hand#getWeakestCards()
     */
    public Hand getStrongestCards() {
        return this.getCardsOfSameValue(this.getStrongestCard());
    }

    /**
     * Returns the hand's weakest playable cards, according to
     * the played faction.
     * <p>
     * If we take every playable cards as a Hand (which is a
     * sub-hand to the main hand), this method is just calling
     * getWeakestCards on it.
     *
     * @param faction The played faction
     * @return the hand's weakest playable cards, according to
     * the played faction.
     * @see Hand#getWeakestCards()
     * @see Hand#getWeakestToBeat(Card)
     */
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

    /**
     * Returns the hand's weakest cards that would beat the
     * given card.
     * <p>
     * If we take every cards that would win as a Hand (which is
     * a sub-hand to the main hand), this method simply calls
     * getWeakestCards on it.
     * <p>
     * If the hand has no cards capable of winning, this method
     * returns the weakest cards overall.
     *
     * @param toBeat The card to beat.
     * @return the hand's weakest cards that would beat the card.
     * @see Hand#getWeakestCards()
     * @see Hand#getWeakestPlayableCards(Faction)
     */
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
