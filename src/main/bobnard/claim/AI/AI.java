package bobnard.claim.AI;

import bobnard.claim.UI.CardUI;
import bobnard.claim.model.*;

public abstract class AI extends Player {
    private CardUI[] cardUIs = null;
    private final Game game;

    AI(Game game) {
        this.game = game;
    }

    public void setCardUIs(CardUI[] cardUIs) {
        this.cardUIs = cardUIs;
    }

    Hand getPlayableCards() {
        return this.playableCards(this.game.getPlayedFaction());
    }

    /* Expected param : the index of the card in the PLAYABLE cards */
    protected void play(int index) {
        if (this.cardUIs == null) {
            throw new IllegalStateException();
        }

        Card card = this.getPlayableCards().get(index);

        for (CardUI cardUI: this.cardUIs) {
            if (cardUI.getCard().equals(card)) {
                cardUI.action();
                break;
            }
        }
    }

    public abstract void action();
}
