package bobnard.claim.AI;

import bobnard.claim.model.Game;
import bobnard.claim.model.Player;

public class EvaluateAI {
    public static void main(String[] args) {
        Game game = new Game();
        Player[] players = new Player[]{
                new AIMinimaxEasy(game, 0),
                new AIMinimaxNormal(game, 1)
        };
        for (int i = 0; i < 2; i++) {
            ((AI) players[i]).setEvaluating();
        }

        game.setPlayers(players);
        game.start();

        loop(game);
    }

    private static void loop(Game game) {
        int[] victories = {0, 0};
        int nbGames = 20;

        int start = (int) System.currentTimeMillis();

        for (int i = 0; i < nbGames; i++) {
            System.out.println("-----------------\nSTARTING GAME n°" + (i + 1) + "\n-----------------");

            // game.printDebugInfo();

            while (!game.isDone()) {
                game.nextStep();
            }

            victories[game.getWinnerID() - 1]++;
            System.out.println(game.getPlayer(game.getWinnerID() - 1).getClass().getSimpleName() + " won the game\n");

            game.nextStep(); // To restart the game

            if (i + 1 == nbGames / 2) {
                System.out.println("--------");
                System.out.println("SWAPPING");
                System.out.println("--------");
                Player[] players2 = new Player[2];
                for (int j = 0; j < 2; j++) {
                    players2[j] = game.getPlayer(1 - j);
                    players2[j].setID(j);
                }
                game.setPlayers(players2);
                int tmp = victories[0];
                victories[0] = victories[1];
                victories[1] = tmp;
            }

            game.start();
        }

        System.out.println("------\n\nRESULT\n\n------");

        double ratio;
        for (int i = 0; i < 2; i++) {
            String name = game.getPlayer(i).getClass().getSimpleName();
            ratio = ((double) victories[i] * 100) / nbGames;
            System.out.println("Player " + (i + 1) + " (" + name + ") won " + victories[i] + " games. (" + ratio + "%)");
        }

        int end = (int) System.currentTimeMillis();
        int time = end - start;

        System.out.println("Total execution time : " + time + " ms (" + (time / 1000) + "s).");
    }
}
