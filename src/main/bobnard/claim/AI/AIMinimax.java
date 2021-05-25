package bobnard.claim.AI;

import bobnard.claim.model.Game;

public class AIMinimax extends AI {
    private final Difficulty difficulty;

    public AIMinimax(Game game, Difficulty difficulty) {
        super(game);
        this.difficulty = difficulty;
    }

    int nextCard() {
        /* Useless switch for now, will become useful when adding more difficulties */
        Node node = switch (this.difficulty) {
            case EASY -> new NodeEasy(game);
        };

        return node.getNextMove();
    }

    @Override
    public void action() {
        this.play(this.nextCard());
    }
}
