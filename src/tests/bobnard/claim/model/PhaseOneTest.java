package bobnard.claim.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PhaseOneTest {
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
    void testUndead() {
        for (Player player: phase.players) {
            assertEquals(0, player.nbCardsFaction(Faction.UNDEADS));
        }

        Card c1 = new Card(Faction.UNDEADS, 0);
        Card c2 = new Card(Faction.UNDEADS, 1);

        phase.playCard(c1);
        phase.changePlayer();
        phase.players[phase.getCurrentPlayer()].addCard(c2);
        phase.playCard(c2);
        phase.endTrick();

        assertEquals(0, phase.players[phase.getLastTrickLoser() ].nbCardsFaction(Faction.UNDEADS));
        assertEquals(2, phase.players[phase.getLastTrickWinner()].nbCardsFaction(Faction.UNDEADS));


        c1 = new Card(Faction.GOBLINS, 0);
        c2 = new Card(Faction.GOBLINS, 1);
        phase.playCard(c1);
        phase.changePlayer();
        phase.players[phase.getCurrentPlayer()].addCard(c2);
        phase.playCard(c2);
        phase.endTrick();

        for (Faction faction: Faction.values()) {
            if (faction != Faction.UNDEADS) {
                for (Player player: phase.players) {
                    assertEquals(0, player.nbCardsFaction(faction));
                }
            }
        }
    }
}