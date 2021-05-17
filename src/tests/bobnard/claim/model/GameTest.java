package bobnard.claim.model;

import java.util.ArrayList;
import java.util.Scanner;

public class GameTest {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        int currentPlayer;
        ArrayList<Card> cards;
        int n;

        Card card;

        while (!game.isDone()) {
            currentPlayer = game.getCurrentPlayerID();
            cards = game.getCards(currentPlayer);
            System.out.println("Player " + game.getCurrentPlayerID() + "'s turn");
            System.out.println(cards);
            do {
                System.out.print("Input card index : ");
                n = scanner.nextInt();
            } while (n < 0 || n >= cards.size());
            System.out.println();

            card = cards.get(n);
            game.playCard(card);

            currentPlayer = game.getCurrentPlayerID();
            cards = game.getCards(currentPlayer);
            System.out.println("Player " + game.getCurrentPlayerID() + "'s turn");
            System.out.println(cards);
            cards = game.getPlayableCards(currentPlayer, card.faction);
            System.out.println("Playable cards : " + cards);

            do {
                System.out.print("Input card index : ");
                n = scanner.nextInt();
            } while (n < 0 || n >= cards.size());
            System.out.println();

            card = cards.get(n);
            game.playCard(card);

            game.printDebugInfo();
        }
    }
}
