package bobnard.claim.AI;

import bobnard.claim.model.Game;

public class NodeEasy extends Node {
    NodeEasy(Game game, int sign) {
        super(game, sign);
    }

    NodeEasy(Game game) {
        super(game);
    }

    @Override
    Node newInstance(Game game, int sign) {
        return new NodeEasy(game, sign);
    }

    @Override
    int evaluateState() {
        return 0;
    }
}
