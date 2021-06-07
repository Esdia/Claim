package bobnard.claim.model;

/**
 * Represents the second phase of the game.
 */
class PhaseTwo extends Phase {
    private PhaseTwo(Player[] players, boolean copy, int startingPlayer) {
        super(players, startingPlayer);

        if (!copy) {
            this.players[0].followersToHand();
            this.players[1].followersToHand();
        }
    }

    /**
     * Creates the second phase.
     *
     * @param players The game's players.
     */
    PhaseTwo(Player[] players, int startingPlayer) {
        this(players, false, startingPlayer);
    }

    //region OVERRIDES

    /**
     * Deals with the played cards.
     * In phase two, the played cards go to the winner's score stack
     * except if they are dwarves, in which case they go to the
     * loser's score stack
     *
     * @see Phase#dealWithPlayedCards()
     */
    @Override
    void dealWithPlayedCards() {
        for (Card card : this.getPlayedCards()) {
            if (card.faction == Faction.DWARVES) {
                this.players[this.getLastTrickLoser()].addToScore(card);
            } else {
                this.players[this.getLastTrickWinner()].addToScore(card);
            }
        }
    }

    /**
     * Returns a new instance of PhaseTwo.
     * <p>
     * This method is used to create copies of the Phase.
     *
     * @param players A list of copies of the phase's players
     * @return A new instance of PhaseTwo
     * @see Phase#getInstance(Player[])
     */
    @Override
    Phase getInstance(Player[] players) {
        return new PhaseTwo(players, true, this.getCurrentPlayer());
    }

    /**
     * Returns the number of the phase (here, 2).
     *
     * @return the number of the phase (2).
     * @see Phase#getPhaseNum()
     */
    @Override
    int getPhaseNum() {
        return 2;
    }
    //endregion

    /**
     * Returns a copy of the Phase.
     *
     * @param players A list of copies of the phase's players
     * @return a copy of the Phase.
     */
    PhaseTwo copy(Player[] players) {
        return (PhaseTwo) super.copy(players);
    }
}
