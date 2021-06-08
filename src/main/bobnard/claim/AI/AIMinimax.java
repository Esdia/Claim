package bobnard.claim.AI;

import bobnard.claim.model.Card;
import bobnard.claim.model.Deck;
import bobnard.claim.model.Game;
import bobnard.claim.model.Hand;

import java.util.ArrayList;
import java.util.Random;

/**
 * An AI implementing the Minimax Algorithm
 */
abstract class AIMinimax extends AI {
    private static final Random random = new Random();

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

    /**
     * Initialize the AI at the start of the game.
     */
    @Override
    public void init() {
        this.initOpponentCards();
    }

    /**
     * Transfers the player's follower to their
     * hand.
     * <p>
     * This method is meant to only be called at
     * the beginning of phase 2
     */
    @Override
    protected void followersToHand() {
        super.followersToHand();

        this.possibleOpponentFollowers.removeAll(this.getCards());
        this.possibleOpponentCards.addAll(this.possibleOpponentFollowers);
    }

    private int getIndex(Card card) {
        return this.playableCards(game.getPlayedFaction()).indexOf(card);
    }

    /**
     * Converts a list of cards (a Hand) to a list of the indexes of the hand's
     * cards in the list of the AI's playable cards.
     *
     * @param hand The hand we want to convert to a list of indexes.
     * @return The indexes of the hand's cards in the AI's playable cards
     */
    protected ArrayList<Integer> getIndexes(Hand hand) {
        ArrayList<Integer> indexes = new ArrayList<>();
        hand.stream().mapToInt(this::getIndex).forEach(indexes::add);
        return indexes;
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
    abstract ArrayList<Integer> getMovesPhaseOne();

    /**
     * Calculates the AI's next move in the second phase.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see Node#getNextMoves()
     */
    private ArrayList<Integer> getMovesPhaseTwo() {
        Node node = this.getNodeInstance();
        return node.getNextMoves();
    }

    /**
     * Calculates the AI's next move.
     *
     * @return The index of the AI's next move in the list of
     * it's playable cards.
     * @see #getMovesPhaseOne()
     * @see #getMovesPhaseTwo()
     */
    @Override
    int nextCard() {
        ArrayList<Integer> nextCardsIndexes;

        if (game.getPhaseNum() == 1) {
            nextCardsIndexes = this.getMovesPhaseOne();
        } else { /* game.getPhaseNum() == 2 */
            nextCardsIndexes = this.getMovesPhaseTwo();
        }

        return nextCardsIndexes.get(random.nextInt(nextCardsIndexes.size()));
    }
}
