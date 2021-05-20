package bobnard.claim.model;

import java.util.ArrayList;

abstract class Phase {
    protected final Player[] players;

    private int currentLeader;
    private int currentPlayer;

    private Trick trick;

    Phase(Player[] players) {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        if (players.length != 2 || players[0] == null || players[1] == null) {
            throw new IllegalArgumentException();
        }

        this.players = players;

        this.currentLeader = 0;
        this.resetTrick();
    }

    //region PHASE MANAGEMENT
    void changePlayer() {
        this.currentPlayer = 1 - this.currentPlayer;
    }

    boolean isDone() {
        return !this.players[this.getLastTrickLoser()].hasCards();
    }
    //endregion

    //region TRICK MANAGEMENT
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

    public boolean trickReady() {
        return this.trick.isReady();
    }

    public Faction getPlayedFaction() {
        return this.trick.getFaction();
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

    private void resetTrick() {
        this.trick = new Trick(currentLeader);
    }
    //endregion

    //region ABSTRACT
    abstract void dealWithPlayedCards();
    //endregion

    //region GETTERS
    protected int getLastTrickWinner() {
        return this.currentLeader;
    }

    protected int getLastTrickLoser() {
        return 1 - this.currentLeader;
    }

    int getCurrentPlayer() {
        return this.currentPlayer;
    }

    Card[] getPlayedCards() {
        Card[] cards = new Card[2];

        cards[currentLeader] = this.trick.getC1();
        cards[1-currentLeader] = this.trick.getC2();

        return cards;
    }
    //endregion
}
