package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;
import bobnard.claim.model.GameState;
import bobnard.claim.model.Hand;

public abstract class Node {
    private static final int DEPTH = 5;

    private final Game game;
    private final int aiID;

    private final Hand aiCards;
    private final Hand opponentPossibleCards;

    private final Hand playableCards;
    private final Hand playableCardsCopy;

    private final NodeType type;

    private int nextMove = 0;

    Node(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, boolean random) {
        this.game = game;
        this.aiID = aiID;

        if (random) {
            this.type = NodeType.RANDOM;
        } else {
            this.type = (aiID == game.getCurrentPlayerID()) ? NodeType.MAX : NodeType.MIN;
        }

        this.aiCards = (Hand) aiCards.clone();
        this.opponentPossibleCards = (Hand) opponentPossibleCards.clone();

        this.playableCards = switch (this.type) {
            case MAX -> this.aiCards;
            case MIN, RANDOM -> this.opponentPossibleCards;
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
            case RANDOM -> {
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
        boolean random = gameCopy.getState() == GameState.TRICK_FINISHED;

        this.playableCards.remove(card);
        Node res = this.newInstance(
                gameCopy,
                aiCards,
                opponentPossibleCards,
                aiID,
                random
        );
        this.playableCards.add(card);

        return res;
    }

    abstract Node newInstance(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, boolean random);

    /*
     * Heuristic depending on the difficulty
     * Must return how much the config is in favor of the node's CURRENT player
     *
     * We suppress the "SameReturnValue" warning only until we correctly implement
     * this method
     */
    @SuppressWarnings("SameReturnValue")
    abstract int evaluateState();

    int getNextMove() {
        this.expectiminimax();

        return this.nextMove;
    }
}
