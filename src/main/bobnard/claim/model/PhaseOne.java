package bobnard.claim.model;

import bobnard.claim.UI.*;

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
        
        MainWindow.gameUI.setplayers(this.players[0], this.players[1]);
        MainWindow.gameUI.repaint();
     }

    void flipCard() {
        this.flippedCard = deck.draw();
        System.out.println("Flipped card : " + this.flippedCard);
        MainWindow.gameUI.FlippedCard = this.flippedCard;
        }

    @Override
    void endTrick() {
        super.endTrick();
        this.giveCentralCards();
        if (!this.isDone()) {
            this.flipCard();
            MainWindow.gameUI.PlayedCard1.myGui.setplayer(-1, false);
            MainWindow.gameUI.PlayedCard2.myGui.setplayer(-1, false);
            MainWindow.gameUI.PlayedCard1 = null;
            MainWindow.gameUI.PlayedCard2 = null;
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
}
