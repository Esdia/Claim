package bobnard.claim.AI;

import bobnard.claim.UI.CardUI;
import bobnard.claim.model.*;

public abstract class AI extends Player {
    private CardUI[] cardUIs = null;
    protected final Game game;

    AI(Game game, int id) {
        super(id);
        this.game = game;
    }

    public void setCardUIs(CardUI[] cardUIs) {
        this.cardUIs = cardUIs;
    }

    /* Expected param : the index of the card in the PLAYABLE cards */
    protected void play(int index) {
        if (this.cardUIs == null) {
            throw new IllegalStateException();
        }

        Card card = this.game.getPlayableCards().get(index);

        for (CardUI cardUI: this.cardUIs) {
            if (cardUI.getCard().equals(card)) {
                cardUI.action();
                break;
            }
        }
    }

    @Override
    public boolean isAI() {
        return true;
    }

    @Override
    public abstract void action();

    @Override
    public abstract void showCard(Card card);
}
