package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Game;
import bobnard.claim.model.Player;

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
    private static final Random random = new Random();

    /**
     * Creates a new AIRandom.
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    public AIRandom(Game game, int id) {
        super(game, id);
    }

    public AIRandom(AIRandom base) {
        super(base);
    }

    /**
     * Initialize the AI at the start of the game
     */
    @Override
    public void init() {
    }

    /**
     * Calculates the AI's next move.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     */
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

    /**
     * Shows the flipped card to the AI.
     * <p>
     * In order to correctly keep track of the opponent's hand, we have
     * to treat flipped cards differently, which is why this method exists.
     *
     * @param card The flipped card.
     */
    @Override
    public void showFlippedCard(Card card) {
    }

    @Override
    protected Player copy() {
        return new AIRandom(this);
    }
}
