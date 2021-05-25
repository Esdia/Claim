package bobnard.claim.model;

class PhaseTwo extends Phase {
    private PhaseTwo(Player[] players, boolean copy) {
        super(players);

        if (!copy) {
            this.players[0].followersToHand();
            this.players[1].followersToHand();
        }
    }

    PhaseTwo(Player[] players) {
        this(players, false);
    }

    //region OVERRIDES
    @Override
    void dealWithPlayedCards() {
        for (Card card: this.getPlayedCards()) {
            if (card.faction == Faction.DWARVES) {
                this.players[this.getLastTrickLoser() ].addToScore(card);
            } else {
                this.players[this.getLastTrickWinner()].addToScore(card);
            }
        }
    }

    @Override
    Phase getInstance(Player[] players) {
        return new PhaseTwo(players, true);
    }
    //endregion

    PhaseTwo copy(Player[] players) {
        return (PhaseTwo) super.copy(players);
    }
}
