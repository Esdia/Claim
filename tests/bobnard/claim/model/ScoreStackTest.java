package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreStackTest {

    @Test
    void testWonFaction() {
        ScoreStack scoreStack = new ScoreStack();

        Card dwa0 = new Card(Faction.DWARVES, 0);

        for (int i = 0; i < 6; i++) {
            assertFalse(scoreStack.wonFaction(Faction.DWARVES));
            scoreStack.push(dwa0);
        }

        assertTrue(scoreStack.wonFaction(Faction.DWARVES));

        scoreStack.pop();
        scoreStack.pop();

        assertFalse(scoreStack.wonFaction(Faction.DWARVES));
        scoreStack.push(new Card(Faction.DWARVES, 9));
        assertTrue(scoreStack.wonFaction(Faction.DWARVES));

        scoreStack.pop();

        assertFalse(scoreStack.wonFaction(Faction.DWARVES));
        scoreStack.push(new Card(Faction.DWARVES, 8));
        assertFalse(scoreStack.wonFaction(Faction.DWARVES));
    }
}