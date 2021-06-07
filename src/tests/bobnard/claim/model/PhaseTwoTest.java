package bobnard.claim.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PhaseTwoTest {
    PhaseTwo phase;
    Player[] players;

    final Random random = new Random();

    @BeforeEach
    void init() {
        players = new Player[]{
                new Player(0),
                new Player(1)
        };
        phase = new PhaseTwo(players, 0);
    }

    @Test
    void testDwarves() {
        for (Player player : phase.players) {
            assertEquals(0, player.nbCardsFaction(Faction.DWARVES));
        }

        Card c1 = new Card(Faction.DWARVES, 0);
        Card c2 = new Card(Faction.DWARVES, 1);

        phase.playCard(c1);
        phase.changePlayer();
        phase.players[phase.getCurrentPlayer()].addCard(c2);
        phase.playCard(c2);
        phase.endTrick();

        assertEquals(2, phase.players[phase.getLastTrickLoser()].nbCardsFaction(Faction.DWARVES));
        assertEquals(0, phase.players[phase.getLastTrickWinner()].nbCardsFaction(Faction.DWARVES));
    }

    @Test
    void testEndTrick() {
        for (Faction faction : Faction.values()) {
            for (Player player : players) {
                assertEquals(0, player.nbCardsFaction(faction));
            }
        }

        Card c1 = new Card(
                Faction.values()[random.nextInt(5)],
                3
        );

        Card c2;
        do {
            c2 = new Card(
                    Faction.values()[random.nextInt(5)],
                    3
            );
        } while (c2.faction == c1.faction);

        phase.playCard(c1);
        phase.changePlayer();
        phase.players[phase.getCurrentPlayer()].addCard(c2);
        phase.playCard(c2);
        phase.endTrick();

        int winner = phase.getLastTrickWinner();
        int loser = phase.getLastTrickLoser();
        for (Faction faction : Faction.values()) {
            if (faction == c1.faction || faction == c2.faction) {
                if (faction == Faction.DWARVES) {
                    assertEquals(1, players[loser].nbCardsFaction(faction));
                    assertEquals(0, players[winner].nbCardsFaction(faction));
                } else {
                    assertEquals(0, players[loser].nbCardsFaction(faction));
                    assertEquals(1, players[winner].nbCardsFaction(faction));
                }
            } else {
                assertEquals(0, players[loser].nbCardsFaction(faction));
                assertEquals(0, players[winner].nbCardsFaction(faction));
            }
        }
    }
}