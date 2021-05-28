package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

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

    @Test
    void testCompare() {
        Card gob0 = new Card(Faction.GOBLINS, 0);
        Card gob0_1 = new Card(Faction.GOBLINS, 0);
        Card gob1 = new Card(Faction.GOBLINS, 1);
        Card dop0 = new Card(Faction.DOPPELGANGERS, 0);

        assertTrue(gob0.compareTo(gob1) < 0);
        assertEquals(0, gob0.compareTo(gob0_1));
        assertTrue(dop0.compareTo(gob1) > 0);
    }

    @Test
    void testName() {
        Random random = new Random();

        Faction faction = Faction.values()[random.nextInt(5)];
        int value = random.nextInt(8) + 2;

        Card card = new Card(faction, value);

        assertEquals(faction.toString() + value, card.name);
        assertEquals(card.name, card.toString());
    }
}