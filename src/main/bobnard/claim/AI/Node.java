package bobnard.claim.AI;

import bobnard.claim.model.*;

/**
 * Represents a Node in the tree generated
 * by the Minimax Algorithm.
 */
public abstract class Node {
    protected final Game game;
    protected final int aiID;

    private final Hand playableCards;

    private final NodeType type;

    private int nextMove = 0;

    /**
     * Creates a new Node.
     *
     * @param game The game on which the AI will play.
     * @param aiID The AI's player ID
     * @param type The type of the Node.
     */
    Node(Game game, int aiID, NodeType type) {
        this.game = game;
        this.aiID = aiID;

        this.type = type;

        /* Not cheating because the second phase has perfect information. */
        this.playableCards = this.game.getPlayableCards();
    }

    private int expectiminimax(int depth, double alpha, double beta) {
        if (depth == 0 || this.isLeaf()) {
            return this.evaluateState();
        }

        double value = 0;

        switch (this.type) {
            case MAX -> {
                // Calculating the AI's move
                value = Double.NEGATIVE_INFINITY;
                int i = 0;
                int tmp;
                for (Card card : this.playableCards) {
                    tmp = this.nextChild(card).expectiminimax(depth - 1, alpha, beta);
                    if (tmp > value) {
                        value = tmp;
                        this.nextMove = i;
                    }
                    alpha = Math.max(value, alpha);
                    if (alpha >= beta) break; // Beta cut
                    i++;
                }
            }
            case MIN -> {
                value = Double.POSITIVE_INFINITY;
                for (Card card : this.playableCards) {
                    value = Math.min(
                            value,
                            this.nextChild(card).expectiminimax(depth - 1, alpha, beta)
                    );
                    beta = Math.min(value, beta);
                    if (beta <= alpha) break; // Alpha cut
                }
            }
        }

        return (int) value;
    }

    abstract int getStartingDepth();

    private void expectiminimax() {
        System.out.println("Started calculating move...");
        long start = System.currentTimeMillis();

        this.expectiminimax(
                this.getStartingDepth(),
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY
        );

        System.out.println("Finished calculating move");
        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - start) + " ms");
    }

    private boolean isLeaf() {
        return this.game.isDone();
    }

    private NodeType getNextType(Game gameCopy) {
        NodeType nodeType;

        if (gameCopy.getCurrentPlayerID() == aiID) {
            nodeType = NodeType.MAX;
        } else {
            nodeType = NodeType.MIN;
        }

        return nodeType;
    }

    private Node nextChild(Card card) {
        if (this.isLeaf()) {
            throw new IllegalStateException();
        }

        Game gameCopy = this.game.copy();
        gameCopy.simulatePlay(card);

        return this.newInstance(
                gameCopy,
                aiID,
                this.getNextType(gameCopy)
        );
    }

    /**
     * Returns an instance of Node
     * <p>
     * The children classes have to override this method
     * and make it return an instance of themselves.
     *
     * @return An instance of Node.
     */
    abstract Node newInstance(Game game, int aiID, NodeType type);

    /**
     * Evaluates an intermediate configuration in phase two.
     *
     * @return The evaluation of the configuration
     */
    abstract int evaluatePhaseTwo();

    /**
     * Heuristic depending on the difficulty.
     * <p>
     * Must return how much the config is in favor of the AI.
     *
     * @return The evaluation of the node's configuration.
     */
    private int evaluateState() {
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
        if (this.game.getPhaseNum() == 2) {
            return this.evaluatePhaseTwo();
        } else { /* this.game.getPhaseNum() == 1 */
            throw new IllegalStateException();
        }
    }

    /**
     * Returns the number of cards of the faction that are still
     * in the game.
     * <p>
     * This method has to be used in phase two.
     * Since this phase has perfect information, it is possible to
     * know how many cards of a given faction are in the game.
     * To win this faction, you need to get n/2 cards, n being
     * the return value of this method.
     *
     * @param faction The faction we are counting the cards of
     * @return The number of cards of the faction in the game
     * @throws IllegalStateException if used in phase one.
     */
    int getMaxNbCardsFaction(Faction faction) {
        if (game.getPhaseNum() != 2) {
            throw new IllegalStateException();
        }

        Player ai = this.game.getPlayer(aiID);
        Player opponent = this.game.getPlayer(1 - aiID);

        int possessed = (int) ai.getCards().getCards(faction).count();
        int remaining = (int) opponent.getCards().getCards(faction).count();

        int inScoreStack = 0;

        ScoreStack s;
        for (int i = 0; i < 2; i++) {
            s = game.getPlayer(i).getScoreStack();
            inScoreStack += s.getNbCardsFaction(faction);
        }

        return possessed + remaining + inScoreStack;
    }

    /**
     * Returns the AI's next move.
     *
     * @return the AI's next move.
     */
    int getNextMove() {
        if (game.getPlayableCards().size() == 1) {
            return 0;
        }

        this.expectiminimax();

        return this.nextMove;
    }
}
