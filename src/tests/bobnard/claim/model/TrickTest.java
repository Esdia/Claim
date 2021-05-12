package bobnard.claim.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrickTest {
    Trick trick;

    @BeforeEach
    void setup() {
        this.trick = new Trick(1);
    }

    @Test
    void testAddCard() {
        Card c1 = new Card(Faction.GOBLINS, 0);
        Card c2 = new Card(Faction.UNDEADS, 1);

        trick.addCard(c1, true);
        trick.addCard(c2, false);

        assertEquals(c1, trick.getC1());
        assertEquals(c2, trick.getC2());
    }

    @Test
    void testReady() {
        assertFalse(trick.isReady());

        Card c = new Card(Faction.GOBLINS, 0);

        trick.addCard(c, true);
        assertFalse(trick.isReady());
        trick.addCard(c, false);
        assertTrue(trick.isReady());

        trick = new Trick(1);
        trick.addCard(c, false);
        assertFalse(trick.isReady());
        trick.addCard(c, true);
        assertTrue(trick.isReady());
    }

    @Test
    void testWinner() {

    }
}