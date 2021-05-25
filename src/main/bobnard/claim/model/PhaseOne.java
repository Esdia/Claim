package bobnard.claim.model;

class PhaseOne extends Phase {
    private final Deck deck;

    private Card flippedCard;

    PhaseOne(Player[] players) {
        super(players);

        this.deck = new Deck();
        this.dealCards();
        this.flipCard();
    }

    /* Copy constructor */
    PhaseOne(Player[] players, Deck deck, Card flippedCard) {
        super(players);
        this.deck = (Deck) deck.clone();
        this.flippedCard = flippedCard;
    }

    //region INIT
    private void dealCards() {
        for (int i = 0; i < 13; i++) {
            this.players[0].addCard(this.deck.draw());
            this.players[1].addCard(this.deck.draw());
        }

        this.players[0].sortHand();
        this.players[1].sortHand();
    }

    private void replaceDeck(Deck newDeck) {
        this.deck.clear();
        this.deck.addAll(newDeck);
    }
    //endregion

    //region MANAGE CENTRAL CARDS
    private void flipCard() {
        this.flippedCard = deck.draw();
        System.out.println("Flipped card : " + this.flippedCard);
    }

    private void giveCentralCards() {
        this.players[this.getLastTrickWinner()].addFollower(this.getFlippedCard());
        this.players[this.getLastTrickLoser() ].addFollower(deck.draw());
    }
    //endregion

    //region OVERRIDES
    @Override
    void endTrick() {
        super.endTrick();
        this.giveCentralCards();
        if (!this.isDone()) {
            this.flipCard();
        }
    }

    @Override
    void dealWithPlayedCards() {
        for (Card card: this.getPlayedCards()) {
            if (card.faction == Faction.UNDEADS) {
                this.players[this.getLastTrickWinner()].addToScore(card);
            }
        }
    }

    @Override
    Phase getInstance(Player[] players) {
        return new PhaseOne(players, this.deck, this.flippedCard);
    }
    //endregion

    //region GETTERS
    Card getFlippedCard() {
        return flippedCard;
    }
    //endregion

    PhaseOne copy(Player[] players) {
        return (PhaseOne) super.copy(players);
    }
}
