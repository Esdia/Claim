package bobnard.claim.model;

import bobnard.claim.UI.Audio;

import java.util.ArrayList;

public class Game {
    private Phase phase;
    private final Player[] players;

    private boolean isDone;
    private int winnerID;

    public Game() {
        players = new Player[2];

        players[0] = new Player();
        players[1] = new Player();

        this.isDone = false;

        this.startPhaseOne();
    }

    //region GAME MANAGEMENT
    public void changePlayer() {
        this.phase.changePlayer();
    }

    public boolean isDone() {
        return this.isDone;
    }

    private void endGame() {
        this.isDone = true;

        int factionsPlayerZero = 0;

        int n0, n1;
        int nMax0, nMax1;

        for (Faction faction: Faction.values()) {
            n0 = players[0].nbCardsFaction(faction);
            n1 = players[1].nbCardsFaction(faction);

            if (n0 > n1) {
                factionsPlayerZero++;
            } else if (n0 == n1) {
                nMax0 = players[0].maxValueFaction(faction);
                nMax1 = players[1].maxValueFaction(faction);

                if (nMax0 > nMax1) {
                    factionsPlayerZero++;
                }
            }
        }

        this.winnerID = factionsPlayerZero >= 3 ? 1 : 2;

        System.out.println("Player " + winnerID + " won the game!");
    }
    //endregion

    //region PHASE MANAGEMENT
    int getPhaseNum() {
        if (this.phase instanceof PhaseOne) {
            return 1;
        } else {
            return 2;
        }
    }

    private void startPhaseOne() {
        Audio.playBGM(1);
        System.out.println("Beginning phase 1");
        this.phase = new PhaseOne(this.players);
    }

    private void startPhaseTwo() {
        if (this.getPhaseNum() != 1) {
            throw new IllegalStateException();
        }
        Audio.getBGM().stop();
        Audio.playBGM(2);
        System.out.println("Beginning phase 2");
        this.phase = new PhaseTwo(players);
    }
    //endregion

    //region TRICK MANAGEMENT
    public boolean isLegalMove(Card card) {
        return this.phase.isLegalMove(card);
    }

    public void playCard(Card card) {
        this.phase.playCard(card);
    }

    public boolean trickReady() {
        return this.phase.trickReady();
    }

    public void endTrick() {
        this.phase.endTrick();

        if (this.phase.isDone()) {
            if (this.getPhaseNum() == 1) {
                this.startPhaseTwo();
            } else {
                this.endGame();
            }
        }
    }
    //endregion

    //region GETTERS
    public int getCurrentPlayerID() {
        return this.phase.getCurrentPlayer();
    }

    public Player getPlayer(int id) {
        return this.players[id];
    }

    public Hand getCards(int playerID) {
        return this.players[playerID].getCards();
    }

    public Card[] getPlayedCards() {
        return this.phase.getPlayedCards();
    }

    public Card getFlippedCard() {
        if (this.phase instanceof PhaseOne) {
            return ((PhaseOne) this.phase).getFlippedCard();
        } else {
            return null;
        }
    }

    public Hand getPlayableCards(int player, Faction faction) {
        return this.players[player].playableCards(faction);
    }

    public int getWinnerID() {
        if (!this.isDone()) {
            throw new IllegalStateException();
        }
        return this.winnerID;
    }
    //endregion

    //region DEBUG PRINT
    public void printDebugInfo() {
        Card[] playedCards = this.getPlayedCards();

        System.out.println("------ DEBUG ------");
        if (this.getFlippedCard() != null) {
            System.out.println("flipped card : " + this.getFlippedCard());
        }
        System.out.println("Player 0 :");
        System.out.println(this.players[0]);
        System.out.println("Played card : " + playedCards[0]);
        System.out.println("\nPlayer 1 :");
        System.out.println(this.players[1]);
        System.out.println("Played card : " + playedCards[1]);
        System.out.println("---- END DEBUG ----");
    }
    //endregion
}
