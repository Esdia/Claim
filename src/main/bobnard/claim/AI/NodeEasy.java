package bobnard.claim.AI;

import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;

public class NodeEasy extends Node {
    NodeEasy(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, boolean random) {
        super(game, aiCards, opponentPossibleCards, aiID, random);
    }

    @Override
    Node newInstance(Game game, Hand aiCards, Hand opponentPossibleCards, int aiID, boolean random) {
        return new NodeEasy(game, aiCards, opponentPossibleCards, aiID, random);
    }

    @Override
    int evaluateState() {
        return 0;
    }
}
