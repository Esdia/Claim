package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;

import java.util.Iterator;

public abstract class Node {
    private static final int DEPTH = 5;

    private final Game game;
    private final boolean isLeaf;

    private final Iterator<Card> handIterator;

    private final int sign;
    private int maxIndex;

    Node(Game game, int sign) {
        this.game = game;
        this.handIterator = game.getPlayableCards().iterator();
        this.isLeaf = !this.hasNextChild();
        this.sign = sign;
    }

    Node(Game game) {
        this(game, 1);
    }

    abstract Node newInstance(Game game, int sign);

    private boolean isLeaf() {
        return this.isLeaf;
    }

    private boolean hasNextChild() {
        return this.handIterator.hasNext();
    }

    private Node nextChild() {
        if (!this.hasNextChild()) {
            throw new IllegalStateException();
        }

        Game gameCopy = this.game.copy();
        gameCopy.simulatePlay(handIterator.next());
        return this.newInstance(gameCopy, -this.sign);
    }

    /*
     * Heuristic depending on the difficulty
     * Must return how much the config is in favor of the node's CURRENT player
     */
    abstract int evaluateState();

    private int negamax(int depth, double alpha, double beta) {
        if (depth == 0 || this.isLeaf()) {
            return sign * this.evaluateState();
        }

        int i = 0;

        double v = Double.NEGATIVE_INFINITY;
        Node child;
        int tmp;

        while (this.hasNextChild()) {
            child = nextChild();
            tmp = child.negamax(depth-1, -beta, -alpha);
            if (tmp > v) {
                v = tmp;
                this.maxIndex = i;
            }
            alpha = Math.max(alpha, v);
            if (alpha >= beta) {
                break;
            }

            i++;
        }

        return (int) v;
    }

    private void negamax() {
        this.negamax(
                DEPTH,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY
        );
    }

    int getNextMove() {
        this.negamax();

        return this.maxIndex;
    }
}
