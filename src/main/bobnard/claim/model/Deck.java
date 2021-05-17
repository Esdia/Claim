package bobnard.claim.model;

import java.util.Collections;
import java.util.Stack;

class Deck {
    private final Stack<Card> deck;

    Deck() {
        this.deck = new Stack<>();
        for (int i = 0; i < 10; i++) {
            this.deck.push(new Card(Faction.GOBLINS, i));
            this.deck.push(new Card(Faction.UNDEADS, i));
            this.deck.push(new Card(Faction.DWARVES, i));
            this.deck.push(new Card(Faction.DOPPELGANGERS, i));
            if (i >= 2) {
                this.deck.push(new Card(Faction.KNIGHTS, i));
            }
        }

        // We add the 4 remaining goblin 0
        for (int i = 0; i < 4; i++) {
            this.deck.push(new Card(Faction.GOBLINS, 0));
        }

        this.shuffle();
    }

    private void shuffle() {
        Collections.shuffle(this.deck);
    }

    int remainingCards() {
        return this.deck.size();
    }

    boolean isEmpty() {
        return this.deck.isEmpty();
    }

    Card draw() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.deck.pop();
    }
}
