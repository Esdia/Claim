package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void remainingCards() {
        Deck deck = new Deck();

        for (int i = 0; i < 52; i++) {
            assertEquals(deck.remainingCards(), 52 - i);
            assertFalse(deck.isEmpty());
            deck.draw();
        }

        assertEquals(deck.remainingCards(), 0);
        assertTrue(deck.isEmpty());

        assertThrows(IllegalStateException.class, deck::draw);
    }

    @Test
    void draw() {
        Deck deck = new Deck();

        int num_gob0 = 0;
        Card gob0 = new Card(Faction.GOBLINS, 0);

        int nbGoblins = 0;
        int nbKnights = 0;
        int nbUndeads = 0;
        int nbDwarves = 0;
        int nbDoppelgangers = 0;

        ArrayList<Card> seen = new ArrayList<>();

        Card c;
        //noinspection ConstantConditions
        while (!deck.isEmpty()) {
            c = deck.draw();

            if (c.equals(gob0)) {
                num_gob0++;
                nbGoblins++;
            } else {
                assertFalse(seen.contains(c));
                seen.add(c);
                switch (c.faction) {
                    case GOBLINS:
                        nbGoblins++;
                        break;
                    case KNIGHTS:
                        nbKnights++;
                        break;
                    case UNDEADS:
                        nbUndeads++;
                        break;
                    case DWARVES:
                        nbDwarves++;
                        break;
                    case DOPPELGANGERS:
                        nbDoppelgangers++;
                        break;
                }
            }
        }

        assertEquals(num_gob0, 5);

        assertEquals(nbGoblins, 14);
        assertEquals(nbKnights, 8);
        assertEquals(nbUndeads, 10);
        assertEquals(nbDwarves, 10);
        assertEquals(nbDoppelgangers, 10);
    }
}