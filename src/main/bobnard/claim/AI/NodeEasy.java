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

    @Override
    int evaluatePhaseOne() {
        return 0;
    }

    @Override
    int evaluatePhaseTwo() {
        return 0;
    }
}
