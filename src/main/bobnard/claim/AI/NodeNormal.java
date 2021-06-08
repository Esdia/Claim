package bobnard.claim.AI;

import bobnard.claim.model.*;

/**
 * Represents a Node for an AI in normal mode.
 */
public class NodeNormal extends Node {
    private static final int DEPTH = 4;

    /**
     * Creates a new Node.
     *
     * @param game The game on which the AI will play.
     * @param aiID The AI's player ID
     * @param type The type of the Node.
     */
    NodeNormal(Game game, int aiID, NodeType type) {
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
        return new NodeNormal(game, aiID, type);
    }

    /**
     * Returns the starting depth.
     *
     * @return the starting depth
     */
    @Override
    int getStartingDepth() {
        return DEPTH;
    }

    /**
     * Returns the completion percentage of the faction in the score stack
     *
     * @param ss      The score stack in which we are counting the cards of the faction
     * @param faction The faction we are counting the cards of
     * @return The completion percentage of the faction in the score stack
     */
    int getFactionCompletionPercentage(ScoreStack ss, Faction faction) {
        int nbCards = ss.getNbCardsFaction(faction);
        int needed = 1 + getMaxNbCardsFaction(faction) / 2;

        return (100 * nbCards) / needed;
    }

    /**
     * Returns true if the player who owns the score stack won the faction.
     *
     * @param ss      The score stack we are checking
     * @param faction The faction we are checking
     * @return True if the player won the faction
     */
    boolean wonFaction(ScoreStack ss, Faction faction) {
        return this.getFactionCompletionPercentage(ss, faction) >= 100;
    }

    /**
     * Evaluates an intermediate configuration in phase two.
     *
     * @return The evaluation of the configuration
     */
    @Override
    int evaluatePhaseTwo() {
        Player ai = this.game.getPlayer(aiID);
        ScoreStack scoreAI = ai.getScoreStack();
        ScoreStack scoreOpponent = game.getPlayer(1 - aiID).getScoreStack();

        int n1 = 0, n2 = 0;
        int n = 0;
        for (Faction faction : Faction.values()) {
            if (this.wonFaction(scoreAI, faction)) {
                n1++;
                n += 100;
            } else if (this.wonFaction(scoreOpponent, faction)) {
                n2++;
                n -= 50;
            } else {
                n += this.getFactionCompletionPercentage(scoreAI, faction);
            }
        }

        if (n1 > 2) {
            return Integer.MAX_VALUE;
        } else if (n2 > 2) {
            return Integer.MIN_VALUE;
        }

        int handVal = ai.getCards().stream().mapToInt(c -> c.value).sum();
        //int scoreVal = scoreAI.size() + n + (100 * n1) - (50 * n2);

        return (n + 100 * n1 - 50 * n2) + 10 * handVal;
    }
}
