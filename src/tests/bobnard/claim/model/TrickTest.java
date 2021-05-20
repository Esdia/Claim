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
        Trick trick = new Trick(1);

        assertThrows(IllegalStateException.class, trick::getWinner);

        Card gob0 = new Card(Faction.GOBLINS, 0);
        Card gob1 = new Card(Faction.GOBLINS, 1);
        Card gob9 = new Card(Faction.GOBLINS, 9);
        Card dwa0 = new Card(Faction.DWARVES, 0);
        Card kni2 = new Card(Faction.KNIGHTS, 2);
        Card dop9 = new Card(Faction.DOPPELGANGERS, 9);

        trick.addCard(gob0, true);
        assertThrows(IllegalStateException.class, trick::getWinner);
        trick.addCard(gob0, false);

        assertEquals(1, trick.getWinner()); // Tie : the leader wins

        trick.addCard(gob0, true);
        trick.addCard(gob1, false);
        assertEquals(0, trick.getWinner()); // The stronger wins

        trick.addCard(dwa0, true);
        trick.addCard(gob1, false);
        assertEquals(1, trick.getWinner()); // Couldn't follow : the leader wins

        trick.addCard(gob9, true);
        trick.addCard(kni2, false);
        assertEquals(0, trick.getWinner()); // Knight's power : the leader loses

        trick.addCard(dwa0, true);
        trick.addCard(kni2, false);
        assertEquals(1, trick.getWinner()); // Knight's power only applies to goblins

        trick.addCard(gob1, true);
        trick.addCard(dop9, false);
        assertEquals(0, trick.getWinner()); // Doppelgangers can follow anything
    }

    @Test
    void testFaction() {
        Trick trick = new Trick(1);

        assertNull(trick.getFaction());

        Card c1 = new Card(Faction.GOBLINS, 0);
        Card c2 = new Card(Faction.DWARVES, 0);

        trick.addCard(c1, false);
        trick.addCard(c2, true);
        assertEquals(Faction.DWARVES, trick.getFaction());

        trick = new Trick(0);
        trick.addCard(c1, true);
        trick.addCard(c2, false);
        assertEquals(Faction.GOBLINS, trick.getFaction());
    }
}