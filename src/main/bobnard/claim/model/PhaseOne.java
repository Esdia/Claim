package bobnard.claim.model;

import java.io.Serializable;

/**
 * Represents the first phase of the gme
 */
class PhaseOne extends Phase implements Serializable {
    private final Deck deck;

    private Card flippedCard;

    /**
     * Creates the first phase.
     *
     * @param players The game's players.
     */
    PhaseOne(Player[] players) {
        super(players);

        this.deck = new Deck();
        this.dealCards();
        this.flipCard();
    }

    /**
     * This constructor is used to make copies of the phase.
     * It does not create a deck.
     *
     * @param players     A copy of the phase's players
     * @param deck        The phase's deck.
     * @param flippedCard The phase's currently flipped card.
     */
    PhaseOne(Player[] players, Deck deck, Card flippedCard) {
        super(players);
        this.deck = (Deck) deck.clone();
        this.flippedCard = flippedCard;
    }

    //region INIT
    private void dealCards() {
        Card card;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 2; j++) {
                card = this.deck.draw();
                this.players[j].addCard(card);
                this.players[j].showCard(card);
            }
        }

        this.players[0].sortHand();
        this.players[1].sortHand();
    }
    //endregion

    //region MANAGE CENTRAL CARDS

    /**
     * Sets the currently flipped card.
     *
     * @param card The currently flipped card.
     */
    void setFlippedCard(Card card) {
        this.flippedCard = card;
    }

    private void flipCard() {
        this.setFlippedCard(deck.draw());
        // System.out.println("Flipped card : " + this.flippedCard);

        for (Player player : this.players) {
            player.showFlippedCard(this.flippedCard);
        }
    }

    private void giveCentralCards() {
        this.players[this.getLastTrickWinner()].addFollower(this.getFlippedCard());

        Player loser = this.players[this.getLastTrickLoser()];

        Card drawnCard = deck.draw();

        loser.showCard(drawnCard);
        loser.addFollower(drawnCard);
    }
    //endregion

    //region OVERRIDES

    /**
     * Ends the trick.
     * <p>
     * beside finishing the trick, this method gives the flipped card
     * and the next drawn card to the players according to the trick's
     * result.
     *
     * @see Phase#endTrick()
     */
    @Override
    void endTrick() {
        super.endTrick();
        this.giveCentralCards();
        if (!this.isDone()) {
            this.flipCard();
        }
    }

    /**
     * Deals with the played cards.
     * In phase one, the played cards are discarded, except if they
     * are undeads, in which case they go to the winner's score stack.
     *
     * @see Phase#dealWithPlayedCards()
     */
    @Override
    void dealWithPlayedCards() {
        for (Card card : this.getPlayedCards()) {
            if (card.faction == Faction.UNDEADS) {
                this.players[this.getLastTrickWinner()].addToScore(card);
            }
        }
    }

    /**
     * Returns a new instance of PhaseOne.
     * <p>
     * This method is used to create copies of the Phase.
     *
     * @param players A list of copies of the phase's players
     * @return A new instance of PhaseOne
     * @see Phase#getInstance(Player[])
     */
    @Override
    Phase getInstance(Player[] players) {
        return new PhaseOne(players, this.deck, this.flippedCard);
    }

    /**
     * Returns the number of the phase (here, 1).
     *
     * @return the number of the phase (1).
     * @see Phase#getPhaseNum()
     */
    @Override
    int getPhaseNum() {
        return 1;
    }
    //endregion

    //region GETTERS

    /**
     * Returns the currently flipped card.
     *
     * @return the currently flipped card
     */
    Card getFlippedCard() {
        return flippedCard;
    }
    //endregion

    /**
     * Returns a copy of the Phase.
     *
     * @param players A list of copies of the phase's players
     * @return a copy of the Phase.
     */
    PhaseOne copy(Player[] players) {
        return (PhaseOne) super.copy(players);
    }
}
