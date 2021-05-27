package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Random random = new Random();

    void testGame() {
        Game game = new Game(false);
        assertFalse(game.isDone());

        int currentPlayer;
        Hand cards;
        Card card;

        assertThrows(IllegalStateException.class, game::getWinnerID);

        for (int j = 1; j < 3; j++) {
            for (int i = 0; i < 13; i++) {
                assertFalse(game.isDone());
                assertEquals(j, game.getPhaseNum());

                currentPlayer = game.getCurrentPlayerID();
                cards = game.getCards(currentPlayer);
                //System.out.println("Player " + game.getCurrentPlayerID() + "'s turn");
                //System.out.println(cards);
                card = cards.get(random.nextInt(cards.size()));
                game.playCard(card);
                game.changePlayer();
                assertFalse(game.trickReady());


                currentPlayer = game.getCurrentPlayerID();
                //cards = game.getCards(currentPlayer);
                //System.out.println("Player " + game.getCurrentPlayerID() + "'s turn");
                //System.out.println(cards);
                cards = game.getPlayableCards(currentPlayer, card.faction);
                card = cards.get(random.nextInt(cards.size()));
                assertTrue(game.isLegalMove(card));
                game.playCard(card);
                assertTrue(game.trickReady());

                game.printDebugInfo();
                game.endTrick();
            }
        }

        assertTrue(game.isDone());

        ScoreStack[] scoreStacks = new ScoreStack[] {
                game.getPlayer(0).getScoreStack(),
                game.getPlayer(1).getScoreStack()
        };

        int factionsPlayerZero = 0;

        int nb0, nb1;
        for (Faction faction: Faction.values()) {
            nb0 = scoreStacks[0].getNbCardsFaction(faction);
            nb1 = scoreStacks[1].getNbCardsFaction(faction);
            if (nb0 > nb1) {
                factionsPlayerZero++;
            } else if (nb0 == nb1) {
                nb0 = scoreStacks[0].maxValueFaction(faction);
                nb1 = scoreStacks[1].maxValueFaction(faction);
                if (nb0 > nb1) {
                    factionsPlayerZero++;
                }
            }
        }

        int expectedWinner = factionsPlayerZero >= 3 ? 1 : 2;

        assertEquals(expectedWinner, game.getWinnerID());
    }

    @Test
    void testGames() {
        for (int i = 0; i < 100; i++) {
            this.testGame();
        }
    }
}