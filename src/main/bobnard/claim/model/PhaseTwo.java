package bobnard.claim.model;

class PhaseTwo extends Phase {
    PhaseTwo(Player[] players) {
        super(players);

        this.players[0].followersToHand();
        this.players[1].followersToHand();
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
        return new PhaseTwo(players);
    }
    //endregion

    PhaseTwo copy() {
        return (PhaseTwo) super.copy();
    }
}
