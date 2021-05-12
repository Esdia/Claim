package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Card(Faction.GOBLINS, -1));
        assertThrows(IllegalArgumentException.class, () -> new Card(Faction.GOBLINS, 10));

        assertThrows(IllegalArgumentException.class, () -> new Card(Faction.KNIGHTS, 0));
        assertThrows(IllegalArgumentException.class, () -> new Card(Faction.KNIGHTS, 1));
    }

    @Test
    void testEquals() {
        Card gob2_1 = new Card(Faction.GOBLINS, 2);
        Card gob2_2 = new Card(Faction.GOBLINS, 2);

        Card kni_2 = new Card(Faction.KNIGHTS, 2);

        Card gob_3 = new Card(Faction.GOBLINS, 3);

        Object o = new Object();

        assertEquals(gob2_1, gob2_1);
        assertEquals(gob2_1, gob2_2);

        assertNotEquals(gob2_1, o);
        assertNotEquals(gob2_1, kni_2);
        assertNotEquals(gob2_1, gob_3);
    }
}