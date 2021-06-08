package bobnard.claim.AI;

import bobnard.claim.model.*;

import java.util.ArrayList;

public class AIMinimaxEasy extends AIMinimax {
    /**
     * Creates a new AIMinimaxEasy
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    public AIMinimaxEasy(Game game, int id) {
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
        return new NodeEasy(
                game,
                this.getId(),
                NodeType.MAX
        );
    }

    /**
     * Calculates the AI's next move in the first phase.
     *
     * In easy mode, the AI will simply try to win every trick, even
     * if the flipped card is really bad.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see AIMinimax#nextCard()
     */
    @Override
    ArrayList<Integer> getMovesPhaseOne() {
        Hand nextMoves;

        if (game.getState() == GameState.WAITING_LEADER_ACTION) {
            // AI plays first
            nextMoves = this.getCards().getWeakestCards();
        } else if (game.getState() == GameState.WAITING_FOLLOW_ACTION) {
            // AI plays second
            nextMoves = this.getCards().getWeakestToBeat(lastShownCard);
        } else {
            throw new IllegalStateException();
        }

        return this.getIndexes(nextMoves);
    }
}
