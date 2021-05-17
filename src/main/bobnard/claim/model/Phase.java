package bobnard.claim.model;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Phase {
    protected final Player[] players;

    protected int currentLeader;
    private int currentPlayer;

    private Trick trick;

    public Phase(Player[] players) {
        if (players.length != 2 || players[0] == null || players[1] == null) {
            throw new IllegalArgumentException();
        }

        this.players = players;

        this.currentLeader = 0;
        this.resetTrick();
    }

    int getLastTrickWinner() {
        return this.currentLeader;
    }

    int getLastTrickLoser() {
        return 1 - this.currentLeader;
    }

    void changePlayer() {
        this.currentPlayer = 1 - this.currentPlayer;
    }

    void endTrick() {
        if (!this.trick.isReady()) {
            throw new IllegalStateException();
        }

        this.currentLeader = this.trick.getWinner();
        this.currentPlayer = this.getLastTrickWinner();

        System.out.println("Player " + currentPlayer + " won the trick");

        this.dealWithPlayedCards();

        this.resetTrick();
    }

    void resetTrick() {
        this.trick = new Trick(currentLeader);
    }

    boolean isLegalMove(Card card) {
        if (this.currentPlayer == this.currentLeader) {
            return true;
        }

        Faction playedFaction = this.trick.getFaction();
        ArrayList<Card> playableCards = this.players[this.currentPlayer].playableCards(playedFaction);

        return playableCards.contains(card);
    }

    void playCard(Card card) {
        if (!this.isLegalMove(card)) {
            return;
        }

        this.trick.addCard(card, currentPlayer == currentLeader);
        this.players[currentPlayer].removeCard(card);
    }

    protected Card[] getPlayedCards() {
        Card[] cards = new Card[2];

        cards[currentLeader] = this.trick.getC1();
        cards[1-currentLeader] = this.trick.getC2();

        return cards;
    }

    abstract void dealWithPlayedCards();

    boolean isDone() {
        return !this.players[this.getLastTrickLoser()].hasCards();
    }

    int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean trickReady() {
        return this.trick.isReady();
    }
}
