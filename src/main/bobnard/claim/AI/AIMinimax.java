package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Deck;
import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;

/**
 * An AI implementing the Minimax Algorithm
 */
public class AIMinimax extends AI {
    private final Difficulty difficulty;

    private final Hand possibleOpponentCards;

    /**
     * Creates a new AIMinimax
     *
     * @param game       The game on which the AI will play.
     * @param id         The AI's player ID.
     * @param difficulty The AI's level
     */
    public AIMinimax(Game game, int id, Difficulty difficulty) {
        super(game, id);
        this.difficulty = difficulty;

        this.possibleOpponentCards = new Hand();
        this.initOpponentCards();
    }

    private void initOpponentCards() {
        possibleOpponentCards.addAll(new Deck());
        possibleOpponentCards.sort();
        possibleOpponentCards.removeAll(this.getCards());
    }

    /**
     * Shows a card to the AI.
     * <p>
     * This method is called when a hidden card is revealed
     * to the AI (so, when the opponent plays it, or when it
     * is drawn from the deck).
     * It is used so that the AI can count cards.
     *
     * @param card The card we want to show to the AI
     */
    @Override
    public void showCard(Card card) {
        this.possibleOpponentCards.remove(card);
    }

    /**
     * Calculates the AI's next move.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see Node#getNextMove()
     */
    int nextCard() {
        Node node;
        if (this.difficulty == Difficulty.EASY) {
            node = new NodeEasy(game, this.getCards(), possibleOpponentCards, this.getId(), NodeType.MAX);
        } else {
            throw new IllegalStateException("Unexpected value: " + this.difficulty);
        }

        return node.getNextMove();
    }

    /**
     * Describes the action taken by the AI at the
     * beginning of it's turn.
     * <p>
     * This AI calculates its move using the Minimax algorithm
     */
    @Override
    public void action() {
        this.play(this.nextCard());
    }
}
