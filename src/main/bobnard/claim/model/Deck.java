package bobnard.claim.model;

import java.util.Collections;
import java.util.Stack;

public class Deck extends Stack<Card> {
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

    int remainingCards() {
        return this.size();
    }

    Card draw() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.pop();
    }
}
