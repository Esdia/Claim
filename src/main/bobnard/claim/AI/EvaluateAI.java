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
        int[] victories = {0, 0};

        for (int i = 0; i < 10; i++) {
            System.out.println("-----------------\n\nSTARTING GAME n°" + (i+1) + "\n\n-----------------");

            game.printDebugInfo();

            while (!game.isDone()) {
                game.nextStep();
            }

            victories[game.getWinnerID() - 1]++;

            game.nextStep(); // To restart the game
            game.start();
        }

        System.out.println("------\n\nRESULT\n\n------");

        for (int i = 0; i < 2; i++) {
            String name = game.getPlayer(i).getClass().getSimpleName();
            System.out.println("Player " + (i + 1) + " (" + name + ") won " + victories[i] + " games.");
        }
    }
}
