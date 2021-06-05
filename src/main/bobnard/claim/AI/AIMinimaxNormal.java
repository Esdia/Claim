package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;
import bobnard.claim.model.GameState;

public class AIMinimaxNormal extends AIMinimax {
    /**
     * Creates a new AIMinimax
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    public AIMinimaxNormal(Game game, int id) {
        super(game, id);
    }

    /**
     * Returns an instance of NodeEasy
     *
     * @return an instance of Node
     * @see AIMinimax#getNodeInstance()
     */
    @Override
    protected Node getNodeInstance() {
        return new NodeNormal(
                game,
                this.getId(),
                NodeType.MAX
        );
    }

    /**
     * Calculates the AI's next move in the first phase.
     * <p>
     * In normal mode, the AI will simply try to win every trick if
     * the flipped card's value is at least 5.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see AIMinimax#nextCard()
     */
    @Override
    int getMovePhaseOne() {
        Card nextMove;

        boolean want = (this.game.getFlippedCard().value >= 5);

        if (game.getState() == GameState.WAITING_LEADER_ACTION) {
            // AI plays first
            if (want) {
                nextMove = this.getCards().getStrongestCard();
            } else {
                nextMove = this.getCards().getWeakestCard();
            }
        } else if (game.getState() == GameState.WAITING_FOLLOW_ACTION) {
            // AI plays second
            if (want) {
                nextMove = this.getCards().getWeakestToBeat(lastShownCard);
            } else {
                nextMove = this.getCards().getWeakestPlayableCard(lastShownCard.faction);
            }
        } else {
            throw new IllegalStateException();
        }

        return this.getIndex(nextMove);
    }
}
