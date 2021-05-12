package bobnard.claim.model;

import java.util.ArrayList;

public class Game {
    private Phase phase;

    private final Player[] players;

    private boolean isDone;

    public Game() {
        players = new Player[2];

        players[0] = new Player();
        players[1] = new Player();

        this.isDone = false;

        this.startPhaseOne();
    }

    void startPhaseOne() {
        System.out.println("Beginning phase 1");
        this.phase = new PhaseOne(this.players);
    }

    void startPhaseTwo() {
        if (this.getPhaseNum() != 1) {
            throw new IllegalStateException();
        }

        System.out.println("Beginning phase 2");
        this.phase = new PhaseTwo(players);
    }

    public int getPhaseNum() {
        if (this.phase instanceof PhaseOne) {
            return 1;
        } else {
            return 2;
        }
    }

    public void playCard(Card card) {
        this.phase.playCard(card);

        if (this.phase.isDone()) {
            if (this.getPhaseNum() == 1) {
                this.startPhaseTwo();
            } else {
                this.endGame();
            }
        }
    }

    public void endGame() {
        this.isDone = true;

        int factionsPlayerOne = 0;

        for (Faction faction: Faction.values()) {
            if (this.players[0].wonFaction(faction)) {
                factionsPlayerOne++;
            }
        }

        int winner = factionsPlayerOne >= 3 ? 1 : 2;

        System.out.println("Player " + winner + " won the game!");
    }

    public ArrayList<Card> getCards(int player) {
        return this.players[player].getCards();
    }

    public ArrayList<Card> getPlayableCard(int player, Faction faction) {
        return this.players[player].playableCards(faction);
    }

    public int getCurrentPlayer() {
        return this.phase.getCurrentPlayer();
    }

    public Player getPlayer(int index) {
        return this.players[index];
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void printDebugInfo() {
        System.out.println("------ DEBUG ------");
        System.out.println("Player 0 :");
        System.out.println(this.players[0]);
        System.out.println("\nPlayer 1 :");
        System.out.println(this.players[1]);
        System.out.println("---- END DEBUG ----");
    }
}
