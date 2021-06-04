package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Deck;
import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;

/**
 * An AI implementing the Minimax Algorithm
 */
abstract class AIMinimax extends AI {
    protected final Hand possibleOpponentCards;
    private final Hand possibleOpponentFollowers;
    protected Card lastShownCard;

    /**
     * Creates a new AIMinimax
     *
     * @param game The game on which the AI will play.
     * @param id   The AI's player ID.
     */
    public AIMinimax(Game game, int id) {
        super(game, id);

        this.possibleOpponentCards = new Hand();
        this.possibleOpponentFollowers = new Hand();
    }

    private void initOpponentCards() {
        possibleOpponentCards.clear();
        possibleOpponentCards.addAll(new Deck());
        possibleOpponentCards.sort();
    }

    @Override
    public void init() {
        this.initOpponentCards();
    }

    @Override
    protected void followersToHand() {
        super.followersToHand();

        this.possibleOpponentFollowers.removeAll(this.getCards());
        this.possibleOpponentCards.addAll(this.possibleOpponentFollowers);
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
        this.lastShownCard = card;
    }

    @Override
    public void showFlippedCard(Card card) {
        this.showCard(card);
        this.possibleOpponentFollowers.add(card);
    }

    /**
     * Returns an instance of Node according to the AI's difficulty level
     *
     * @return an instance of Node
     * @see AIMinimaxEasy#getNodeInstance()
     */
    abstract protected Node getNodeInstance();

    /**
     * Calculates the AI's next move in the first phase.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see #nextCard()
     */
    abstract int getMovePhaseOne();

    /**
     * Calculates the AI's next move in the second phase.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see Node#getNextMove()
     */
    private int getMovePhaseTwo() {
        Node node = this.getNodeInstance();
        return node.getNextMove();
    }

    /**
     * Calculates the AI's next move.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see #getMovePhaseOne()
     * @see #getMovePhaseTwo()
     */
    @Override
    int nextCard() {
        if (game.getPhaseNum() == 1) {
            return this.getMovePhaseOne();
        } else { /* game.getPhaseNum() == 2 */
            return this.getMovePhaseTwo();
        }
    }
}
