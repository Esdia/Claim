package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;

import java.util.Random;

/*
 * This class was only used for testing the AI
 * infrastructure. Since it is not used anymore
 * but we still wish to keep it, we have to
 * suppress the warning to not upset the IDE
 */
@SuppressWarnings("unused")
public class AIRandom extends AI {
    private final Random random;

    public AIRandom(Game game, int id) {
        super(game, id);
        this.random = new Random();
    }

    @Override
    public void action() {
        this.play(
                random.nextInt(this.game.getPlayableCards().size())
        );
    }

    @Override
    public void showCard(Card card) {
    }
}
