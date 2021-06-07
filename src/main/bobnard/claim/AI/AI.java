package bobnard.claim.AI;

import bobnard.claim.UI.CardUI;
import bobnard.claim.model.*;

/**
 * Represents a blank AI.
 */
public abstract class AI extends Player {
    private transient CardUI[] cardUIs = null;
    protected final Game game;

    private boolean evaluating;

    /**
     * Creates a blank AI.
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    AI(Game game, int id) {
        super(id);
        this.game = game;
        this.evaluating = false;
    }

    @Override
    public abstract void init();

    void setEvaluating() {
        this.evaluating = true;
    }

    /**
     * Sets the cardUIs.
     * <p>
     * At any point of time, each of these cardUIs is linked to a card
     * owned by the AI. The AI needs to go through these cardUIs to play
     * a card.
     *
     * @param cardUIs An array of cardUIs linked to the cards owned by the AI.
     */
    public void setCardUIs(CardUI[] cardUIs) {
        this.cardUIs = cardUIs;
    }

    /**
     * Plays a card.
     *
     * @param index The index of the played card. This must be an index
     *              in the AI's playable cards, not in every cards.
     */
    protected void play(int index) {
        Card card = this.game.getPlayableCards().get(index);

        if (this.evaluating) {
            this.game.playCard(card);
            return;
        }

        if (this.cardUIs == null) {
            throw new IllegalStateException();
        }

        for (CardUI cardUI : this.cardUIs) {
            if (cardUI.getCard().equals(card)) {
                cardUI.action();
                break;
            }
        }
    }

    /**
     * Returns true if the player is an AI.
     *
     * @return true if the player is an AI (here, true).
     * @see Player#isAI()
     */
    @Override
    public boolean isAI() {
        return true;
    }

    /**
     * Calculates the AI's next move.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     */
    abstract int nextCard();

    /**
     * Describes the action taken by the AI at the
     * beginning of it's turn.
     * <p>
     * This method calculates the AI's next move and plays it.
     */
    @Override
    public void action() {
        this.play(this.nextCard());
    }

    /**
     * Shows a card to the AI.
     * <p>
     * This method is called when a hidden card is revealed
     * to the AI (so, when the opponent plays it, or when it
     * is drawn from the deck).
     * It is used so that the AI can count cards.
     *
     * @param card The card we want to show to the AI
     */
    @Override
    public abstract void showCard(Card card);

    @Override
    public abstract void showFlippedCard(Card card);
}
