package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;

import java.util.Random;

/**
 * An AI that plays randomly.
 * <p>
 * This class was only used for testing the AI
 * infrastructure. Since it is not used anymore
 * but we still wish to keep it, we have to
 * suppress the warning to not upset the IDE
 */
@SuppressWarnings("unused")
public class AIRandom extends AI {
    private final Random random;

    /**
     * Creates a new AIRandom.
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    public AIRandom(Game game, int id) {
        super(game, id);
        this.random = new Random();
    }

    @Override
    public void init() {
    }

    @Override
    int nextCard() {
        return random.nextInt(this.game.getPlayableCards().size());
    }

    /**
     * Shows a card to the AI.
     * <p>
     * This method is empty since this AI cannot count cards.
     *
     * @param card The card we want to show to the AI
     */
    @Override
    public void showCard(Card card) {
    }
}
