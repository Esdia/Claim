package bobnard.claim.AI;

import bobnard.claim.model.*;

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
     * Evaluates an intermediate configuration in phase two.
     *
     * @return The evaluation of the configuration
     */
    @Override
    int evaluatePhaseTwo() {
        int handVal = this.aiCards.stream().mapToInt(c -> c.value).sum();

        Player ai = this.game.getPlayer(aiID);
        ScoreStack ss = ai.getScoreStack();
        int scoreVal = ss.size();

        for (Faction faction : Faction.values()) {
            if (ss.getNbCardsFaction(faction) > getMaxNbCardsFaction(faction) / 2) {
                // The AI won this faction, and cannot lose it later.
                scoreVal += 5;
            }
        }

        return handVal + 3 * scoreVal;
    }
}
