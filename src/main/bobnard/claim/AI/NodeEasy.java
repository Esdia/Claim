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
     * @param random                Whether or not this node should manage a random event (draw
     *                              a card from the deck).
     */
    NodeEasy(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, boolean random) {
        super(game, aiCards, opponentPossibleCards, aiID, random);
    }

    /**
     * Returns an instance of Node
     *
     * @return An instance of Node.
     * @see Node#newInstance(Game, Hand, Hand, int, boolean)
     */
    @Override
    Node newInstance(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, boolean random) {
        return new NodeEasy(game, aiCards, opponentPossibleCards, aiID, random);
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
        return 0;
    }
}
