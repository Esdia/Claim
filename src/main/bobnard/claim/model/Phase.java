package bobnard.claim.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a game's phase.
 */
abstract class Phase  implements Serializable {
    protected final Player[] players;

    private int currentLeader;
    private int currentPlayer;

    private Trick trick;

    /**
     * Creates a new "blank" phase.
     * <p>
     * Since the class is abstract, the child
     * classes will determine whether it is
     * the first phase or the second one.
     *
     * @param players        The game's players.
     * @param startingPlayer The phase's starting player
     * @throws IllegalArgumentException if the player list is badly formed.
     *                                  The list must contains 2 non null players.
     */
    Phase(Player[] players, int startingPlayer) {
        if (players == null) {
            throw new IllegalArgumentException();
        }

        if (players.length != 2 || players[0] == null || players[1] == null) {
            throw new IllegalArgumentException();
        }

        this.players = players;

        this.currentLeader = startingPlayer;
        this.currentPlayer = startingPlayer;
        this.resetTrick();
    }

    /**
     * Creates a new "blank" phase.
     * <p>
     * Since the class is abstract, the child
     * classes will determine whether it is
     * the first phase or the second one.
     *
     * @param players The game's players.
     * @throws IllegalArgumentException if the player list is badly formed.
     *                                  The list must contains 2 non null players.
     */
    Phase(Player[] players) {
        this(players, 0);
    }

    //region PHASE MANAGEMENT

    /**
     * Switches the current player.
     * <p>
     * 1 -> 0 and 0 -> 1.
     */
    void changePlayer() {
        this.currentPlayer = 1 - this.currentPlayer;
    }

    /**
     * Returns true if the phase is finished.
     * <p>
     * A phase if finished when both players played all of their cards.
     *
     * @return true if the phase is finished
     */
    boolean isDone() {
        return !this.players[this.getLastTrickLoser()].hasCards();
    }
    //endregion

    //region TRICK MANAGEMENT

    /**
     * Checks if the current player is allowed to play the given
     * card.
     *
     * @param card The card the current player is trying to play.
     * @return true if the current player is allowed to play the card.
     */
    boolean isLegalMove(Card card) {
        if (this.currentPlayer == this.currentLeader) {
            return true;
        }

        Faction playedFaction = this.trick.getFaction();
        ArrayList<Card> playableCards = this.players[this.currentPlayer].playableCards(playedFaction);

        return playableCards.contains(card);
    }

    /**
     * Plays a card.
     * <p>
     * This method adds the card to the current trick and
     * removes it from the current player's hand.
     * It will fail silently if the move is illegal.
     *
     * @param card the played card.
     */
    void playCard(Card card) {
        if (!this.isLegalMove(card)) {
            return;
        }

        this.trick.addCard(card, currentPlayer == currentLeader);

        this.players[currentPlayer].removeCard(card);

        Player otherPlayer = this.players[1 - currentPlayer];
        otherPlayer.showCard(card);
    }

    /**
     * Returns true if the current trick is ready (i.e. both
     * players played a card).
     *
     * @return true if the trick is ready.
     * @see Trick#isReady()
     */
    public boolean trickReady() {
        return this.trick.isReady();
    }

    /**
     * Returns the faction of the card played by the leader.
     *
     * @return The faction played by the leader. Null if
     * the leader has not played yet.
     * @see Trick#getFaction()
     */
    public Faction getPlayedFaction() {
        return this.trick.getFaction();
    }

    /**
     * Ends the trick.
     * <p>
     * This method manages the followings:
     * - Sets the next trick's leader to the current trick's winner.
     * - Do something with the played cards (depending on the phase)
     * - Starts a new trick
     *
     * @throws IllegalStateException if the trick is not ready.
     */
    void endTrick() {
        if (!this.trick.isReady()) {
            throw new IllegalStateException();
        }

        this.currentLeader = this.trick.getWinner();
        this.currentPlayer = this.getLastTrickWinner();

        // System.out.println("Player " + currentPlayer + " won the trick");

        this.dealWithPlayedCards();

        this.resetTrick();
    }

    /**
     * Start a new trick.
     */
    private void resetTrick() {
        this.trick = new Trick(currentLeader);
    }
    //endregion

    //region ABSTRACT

    /**
     * Do something with the played cards at the end of
     * each trick.
     */
    abstract void dealWithPlayedCards();

    /**
     * Returns the number of the phase (1 or 2).
     *
     * @return the number of the phase.
     */
    abstract int getPhaseNum();
    //endregion

    //region GETTERS

    /**
     * Returns the winner of the current trick.
     *
     * @return The winner of the current trick.
     * @throws IllegalStateException if the trick is not ready.
     * @see Trick#getWinner()
     */
    protected int getTrickWinnerID() {
        return this.trick.getWinner();
    }

    /**
     * Returns the winner of the last trick. 0 if no trick
     * has been played yet.
     *
     * @return the winner of the last trick.
     */
    protected int getLastTrickWinner() {
        return this.currentLeader;
    }

    /**
     * Returns the loser of the last trick. 1 if no trick
     * has been played yet.
     *
     * @return the loser of the last trick.
     */
    protected int getLastTrickLoser() {
        return 1 - this.currentLeader;
    }

    /**
     * Returns the current player's ID.
     *
     * @return the current player's ID.
     */
    int getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Returns a list of the cards played in the current trick.
     * <p>
     * The list will always have a size of two, and its first
     * element will always be the card played by the leader.
     * Some elements may be null if no card has been played.
     *
     * @return a list of the cards played in the current trick.
     */
    Card[] getPlayedCards() {
        Card[] cards = new Card[2];

        cards[currentLeader] = this.trick.getC1();
        cards[1 - currentLeader] = this.trick.getC2();

        return cards;
    }
    //endregion

    /**
     * Returns an instance of Phase.
     * <p>
     * The children classes have to override this method
     * and make it return an instance of themselves.
     * <p>
     * This method is used to create copies of the phase.
     *
     * @param players A list of copies of the phase's players
     * @return An instance of Phase.
     */
    abstract Phase getInstance(Player[] players);

    /**
     * Returns a copy of the Phase.
     *
     * @param players A list of copies of the phase's players
     * @return A copy of the phase.
     */
    Phase copy(Player[] players) {
        Phase phase = this.getInstance(players);
        phase.currentPlayer = this.currentPlayer;
        phase.currentLeader = this.currentLeader;
        phase.trick = this.trick.copy();

        return phase;
    }
}
