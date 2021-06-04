package bobnard.claim.AI;

import bobnard.claim.model.*;

/**
 * Represents a Node for an AI in easy mode.
 */
public class NodeEasy extends Node {
    private static final int DEPTH = 4;

    /**
     * Creates a new NodeEasy.
     *
     * @param game The game on which the AI will play.
     * @param aiID The AI's player ID
     * @param type The type of the Node.
     */
    NodeEasy(Game game, int aiID, NodeType type) {
        super(game, aiID, type);
    }

    /**
     * Returns an instance of Node
     *
     * @return An instance of Node.
     * @see Node#newInstance(Game, int, NodeType)
     */
    @Override
    Node newInstance(Game game, int aiID, NodeType type) {
        return new NodeEasy(game, aiID, type);
    }

    @Override
    int getStartingDepth() {
        return DEPTH;
    }

    /**
     * Evaluates an intermediate configuration in phase two.
     *
     * @return The evaluation of the configuration
     */
    @Override
    int evaluatePhaseTwo() {
        Player ai = this.game.getPlayer(aiID);
        int handVal = ai.getCards().stream().mapToInt(c -> c.value).sum();
        ScoreStack ss = ai.getScoreStack();
        int scoreVal = ss.size();

        for (Faction faction : Faction.values()) {
            if (ss.getNbCardsFaction(faction) > getMaxNbCardsFaction(faction) / 2) {
                // The AI won this faction, and cannot lose it later.
                scoreVal += 5;
            }
        }

        return handVal + 2 * scoreVal;
    }
}
