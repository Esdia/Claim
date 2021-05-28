package bobnard.claim.AI;

import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;

/**
 * Represents a Node for an AI in easy mode.
 */
public class NodeEasy extends Node {
    /**
     * Creates a new NodeEasy.
     *
     * @param game                  The game on which the AI will play.
     * @param aiCards               The AI's cards.
     * @param opponentPossibleCards The cards that could be in the opponent's hand.
     * @param aiID                  The AI's player ID
     * @param type                  The type of the Node.
     */
    NodeEasy(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, NodeType type) {
        super(game, aiCards, opponentPossibleCards, aiID, type);
    }

    /**
     * Returns an instance of Node
     *
     * @return An instance of Node.
     * @see Node#newInstance(Game, Hand, Hand, int, NodeType)
     */
    @Override
    Node newInstance(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, NodeType type) {
        return new NodeEasy(game, aiCards, opponentPossibleCards, aiID, type);
    }

    /**
     * Heuristic depending on the difficulty.
     * <p>
     * Must return how much the config is in favor of the AI.
     *
     * @return The evaluation of the node's configuration.
     * @see Node#evaluateState()
     */
    @Override
    int evaluateState() {
        if (this.game.isDone()) {
            /*
             * If the game is done, it is very easy to evaluate the configuration :
             * If the AI won, it's very good.
             * If the AI lost, it's very bad.
             */
            if (this.game.getWinnerID() == this.aiID) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        /*
         * Here, we are evaluating a intermediate configuration,
         * so we have to estimate if it is rather favorable to
         * the AI or to the opponent.
         */
        return 0;
    }
}
