package bobnard.claim.model;

public class PhaseOne extends Phase {
    private final Deck deck;

    private Card flippedCard;

    public PhaseOne(Player[] players) {
        super(players);

        this.deck = new Deck();
        this.dealCards();
        this.flipCard();
    }

    void dealCards() {
        for (int i = 0; i < 13; i++) {
            this.players[0].addCard(this.deck.draw());
            this.players[1].addCard(this.deck.draw());
        }

        this.players[0].sortHand();
        this.players[1].sortHand();
     }

    void flipCard() {
        this.flippedCard = deck.draw();
        System.out.println("Flipped card : " + this.flippedCard);
        }

    @Override
    void endTrick() {
        super.endTrick();
        this.giveCentralCards();
        if (!this.isDone()) {
            this.flipCard();
        }
    }

    void giveCentralCards() {
        this.players[this.getLastTrickWinner()].addFollower(this.flippedCard);
        this.players[this.getLastTrickLoser() ].addFollower(deck.draw());
    }

    @Override
    void dealWithPlayedCards() {
        for (Card card: this.getPlayedCards()) {
            if (card.faction == Faction.UNDEADS) {
                this.players[this.getLastTrickWinner()].addToScore(card);
            }
        }
    }

    public Card getFlippedCard() {
        return flippedCard;
    }
}
