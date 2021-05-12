package bobnard.claim.model;

public class PhaseTwo extends Phase {
    public PhaseTwo(Player[] players) {
        super(players);

        this.players[0].followersToHand();
        this.players[1].followersToHand();
    }

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
}
