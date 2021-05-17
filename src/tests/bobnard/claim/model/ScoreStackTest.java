package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ScoreStackTest {

    @Test
    void testStack() {
        ScoreStack stack = new ScoreStack();
        Random random = new Random();

        Faction[] factions = Faction.values();

        Faction x;
        int nbDwarves = 0;

        int tmp;
        int max = 0;

        for (int i = 0; i < 50; i++) {
            x = factions[random.nextInt(5)];
            if (x == Faction.DWARVES) {
                nbDwarves++;
            }

            tmp = random.nextInt(8) + 2;
            if (tmp > max) {
                max = tmp;
            }

            stack.push(new Card(x, tmp));
        }

        assertEquals(nbDwarves, stack.nbCardsFaction(Faction.DWARVES));
        assertEquals(max, stack.maxValueFaction(Faction.DWARVES));
    }
}