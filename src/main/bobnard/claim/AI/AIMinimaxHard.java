package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;
import bobnard.claim.model.GameState;

public class AIMinimaxHard extends AIMinimax {
    /**
     * Creates a new AIMinimax
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    public AIMinimaxHard(Game game, int id) {
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
        return new NodeHard(
                game,
                this.getCards(),
                possibleOpponentCards,
                this.getId(),
                NodeType.MAX
        );
    }

    /**
     * Calculates the AI's next move in the first phase.
     * <p>
     * In easy mode, the AI will simply try to win every trick, even
     * if the flipped card is really bad.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see AIMinimax#nextCard()
     */
    @Override
    int getMovePhaseOne() {
        Card nextMove = null;

        boolean want = (this.game.getFlippedCard().value >= 5);

        if (game.getState() == GameState.WAITING_LEADER_ACTION) {
            // AI plays first
            if (want) {
                nextMove = this.getCards().getStrongestCard();
            }

            if (!want || nextMove.value < 7) {
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
