package bobnard.claim.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PhaseTest {
    PhaseOne phase;
    Player[] players;

    final static Random random = new Random();

    @BeforeEach
    void init() {
        players = new Player[] {
                new Player(),
                new Player()
        };
        phase = new PhaseOne(players);
    }

    @Test
    void testConstructor() {
        assertDoesNotThrow(() -> new PhaseOne(players));

        assertThrows(IllegalArgumentException.class, () -> new PhaseOne(null));

        players[1] = null;
        assertThrows(IllegalArgumentException.class, () -> new PhaseOne(players));

        players[0] = null;
        assertThrows(IllegalArgumentException.class, () -> new PhaseOne(players));
    }

    @Test
    void testLegalMove() {
        Faction faction = Faction.values()[random.nextInt(5)];
        int value = random.nextInt(8) + 2;

        Card c1 = new Card(faction, value);
        assertTrue(phase.isLegalMove(c1));

        phase.playCard(c1);
        phase.changePlayer();

        phase.players[phase.getCurrentPlayer()].addCard(c1); // To make sure there is at least one legal move
        for (Card c2: phase.players[phase.getCurrentPlayer()].getCards()) {
            if (c2.faction == c1.faction || c2.faction == Faction.DOPPELGANGERS) {
                assertTrue(phase.isLegalMove(c2));
            } else {
                if (phase.isLegalMove(c2)) {
                    System.out.println("c1 = " + c1);
                    System.out.println("c2 = " + c2);
                }
                assertFalse(phase.isLegalMove(c2));

                phase.playCard(c2);
                assertFalse(phase.trickReady());
            }
        }
    }

    @Test
    void testTrick() {
        assertThrows(IllegalStateException.class, phase::endTrick);
        assertFalse(phase.trickReady());

        Card c1 = new Card(Faction.GOBLINS, 0);

        phase.playCard(c1);
        assertThrows(IllegalStateException.class, phase::endTrick);
        assertFalse(phase.trickReady());

        int firstPlayer = phase.getCurrentPlayer();
        phase.changePlayer();
        int secondPlayer = phase.getCurrentPlayer();

        Card c2 = phase.players[secondPlayer].playableCards(c1.faction).get(0);
        phase.playCard(c2);

        int expectedWinner = (c2.compareTo(c1) > 0) ? secondPlayer : firstPlayer;

        assertTrue(phase.trickReady());
        assertDoesNotThrow(phase::endTrick);
        assertThrows(IllegalStateException.class, phase::endTrick);
        assertFalse(phase.trickReady());

        assertEquals(expectedWinner, phase.getLastTrickWinner());
        assertEquals(1-expectedWinner, phase.getLastTrickLoser());

        assertEquals(expectedWinner, phase.getCurrentPlayer());
    }
}