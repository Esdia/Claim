package bobnard.claim.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Stack;

/**
 * This class represents a Deck.
 * A deck is just a stack of cards
 */
public class Deck extends Stack<Card> implements Serializable {
    /**
     * Creates a new deck and initialize it so that
     * it can be used in the game.
     *
     * The generated deck contains :
     * - 14 goblins (5 0's, then one per value from 1 to 9)
     * - 10 dwarves, doppelgangers and undeads (one per value
     * from 0 to 9)
     * - 8 knights (one per value from 2 to 9)
     *
     * The deck is shuffled right after being created
     */
    public Deck() {
        for (int i = 0; i < 10; i++) {
            this.push(new Card(Faction.GOBLINS, i));
            this.push(new Card(Faction.UNDEADS, i));
            this.push(new Card(Faction.DWARVES, i));
            this.push(new Card(Faction.DOPPELGANGERS, i));
            if (i >= 2) {
                this.push(new Card(Faction.KNIGHTS, i));
            }
        }

        // We add the 4 remaining goblin 0
        for (int i = 0; i < 4; i++) {
            this.push(new Card(Faction.GOBLINS, 0));
        }

        this.shuffle();
    }

    private void shuffle() {
        Collections.shuffle(this);
    }

    /**
     * Returns the number of cards remaining in the deck.
     *
     * @return the number of cards remaining in the deck.
     */
    int remainingCards() {
        return this.size();
    }

    /**
     * Draw a card from the deck.
     * The card is removed from the deck.
     *
     * @return The card on top of the deck.
     */
    Card draw() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.pop();
    }
}
