package bobnard.claim.AI;

import bobnard.claim.model.Game;
import bobnard.claim.model.Player;

public class EvaluateAI {
    public static void main(String[] args) {
        Game game = new Game();
        Player[] players = new Player[]{
                new AIMinimaxEasy(game, 0),
                new AIRandom(game, 1)
        };
        for (Player player : players) {
            ((AI) player).setEvaluating();
        }

        game.setPlayers(players);
        game.start();

        loop(game);
    }

    private static void loop(Game game) {
        int victoriesP1 = 0;
        int victoriesP2 = 0;

        for (int i = 0; i < 10; i++) {
            System.out.println("-----------------\n\nSTARTING NEW GAME\n\n-----------------");

            game.printDebugInfo();

            while (!game.isDone()) {
                game.nextStep();
            }

            if (game.getWinnerID() == 1) {
                victoriesP1++;
            } else {
                victoriesP2++;
            }

            game.nextStep(); // To restart the game
            game.start();
        }

        System.out.println("------\n\nRESULT\n\n------");

        System.out.println("AI 1 won " + victoriesP1 + " games");
        System.out.println("AI 2 won " + victoriesP2 + " games");
    }
}
