package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;
import bobnard.claim.model.GameState;
import bobnard.claim.model.Hand;

/**
 * Represents a Node in the tree generated
 * by the Minimax Algorithm.
 */
public abstract class Node {
    private static final int DEPTH = 5;

    protected final Game game;
    protected final int aiID;

    private final Hand aiCards;
    private final Hand opponentPossibleCards;

    private final Hand playableCards;
    private final Hand playableCardsCopy;

    private final NodeType type;

    private int nextMove = 0;

    /**
     * Creates a new Node.
     *
     * @param game                  The game on which the AI will play.
     * @param aiCards               The AI's cards.
     * @param opponentPossibleCards The cards that could be in the opponent's hand.
     * @param aiID                  The AI's player ID
     * @param type                  The type of the Node.
     */
    Node(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, NodeType type) {
        this.game = game;
        this.aiID = aiID;

        this.type = type;

        this.aiCards = (Hand) aiCards.clone();
        this.opponentPossibleCards = (Hand) opponentPossibleCards.clone();

        this.playableCards = switch (this.type) {
            case MAX -> this.aiCards;
            case MIN, FLIP_CARD, DRAW_CARD -> this.opponentPossibleCards;
        };
        this.playableCardsCopy = (Hand) this.playableCards.clone();
    }

    private int expectiminimax(int depth, double alpha, double beta) {
        //System.out.println("depth = " + depth);
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
                for (Card card : this.playableCardsCopy) {
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
                for (Card card : this.playableCardsCopy) {
                    value = Math.min(
                            value,
                            this.nextChild(card).expectiminimax(depth - 1, alpha, beta)
                    );
                    beta = Math.min(value, beta);
                    if (beta <= alpha) break; // Alpha cut
                }
            }
            case FLIP_CARD, DRAW_CARD -> {
                int counter = 0;
                for (Card card : this.playableCardsCopy) {
                    counter++;
                    value += this.nextChild(card).expectiminimax(depth - 1, alpha, beta);
                }

                value /= counter;
            }
        }

        return (int) value;
    }

    private void expectiminimax() {
        this.expectiminimax(
                DEPTH,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY
        );
    }

    private boolean isLeaf() {
        return this.playableCards.size() == 0;
    }

    private Node nextChild(Card card) {
        if (this.isLeaf()) {
            throw new IllegalStateException();
        }

        Game gameCopy = this.game.copy();
        gameCopy.setSimulator();
        gameCopy.simulatePlay(card);

        NodeType nodeType;
        if (gameCopy.getState() == GameState.TRICK_FINISHED) {
            nodeType = NodeType.DRAW_CARD;
        } else if (gameCopy.getState() == GameState.SIMULATED_DRAWN_CARD) {
            nodeType = NodeType.FLIP_CARD;
        } else if (gameCopy.getCurrentPlayerID() == aiID) {
            nodeType = NodeType.MAX;
        } else {
            nodeType = NodeType.MIN;
        }

        this.playableCards.remove(card);
        Node res = this.newInstance(
                gameCopy,
                aiCards,
                opponentPossibleCards,
                aiID,
                nodeType
        );
        this.playableCards.add(card);

        return res;
    }

    /**
     * Returns an instance of Node
     * <p>
     * The children classes have to override this method
     * and make it return an instance of themselves.
     *
     * @return An instance of Node.
     */
    abstract Node newInstance(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, NodeType type);

    /**
     * Heuristic depending on the difficulty.
     * <p>
     * Must return how much the config is in favor of the AI.
     *
     * @return The evaluation of the node's configuration.
     */
    abstract int evaluateState();

    /**
     * Returns the AI's next move.
     *
     * @return the AI's next move.
     */
    int getNextMove() {
        this.expectiminimax();

        return this.nextMove;
    }
}
