package bobnard.claim.model;

import java.io.Serializable;
import java.util.Stack;

/**
 * This class represents a player.
 */
public class Player implements Serializable {
    private int id;

    private final Hand hand;
    private final ScoreStack scoreStack;
    private final Stack<Card> followers;

    /**
     * Creates a new player.
     * <p>
     * The ID is either 0 or 1. It is mainly used
     * by the AIs, so that they can know whether or
     * not it is their turn to play.
     *
     * @param id The player's ID.
     */
    public Player(int id) {
        this.id = id;
        this.hand = new Hand();
        this.scoreStack = new ScoreStack();
        this.followers = new Stack<>();
    }

    public void setID(int id) {
        this.id = id;
    }

    //region HAND MANAGEMENT

    /**
     * Return true if the player has any cards left.
     *
     * @return true if the player has any cards left.
     */
    boolean hasCards() {
        return !this.hand.isEmpty();
    }

    /**
     * Give a card to the player.
     *
     * @param card The card to be given.
     */
    void addCard(Card card) {
        this.hand.add(card);
    }

    /**
     * Remove a card from the player's hand.
     *
     * @param card The card to be removed.
     */
    void removeCard(Card card) {
        this.hand.remove(card);
    }

    /**
     * Sort the player's hand.
     */
    void sortHand() {
        this.hand.sort();
    }

    /**
     * Returns the cards the player can play, given the faction
     * played by the leader.
     *
     * @param faction The faction played by the leader (null if the
     *                current player is the leader).
     * @return The cards the player can play.
     * @see Hand#playableCards(Faction)
     */
    protected Hand playableCards(Faction faction) {
        return this.hand.playableCards(faction);
    }
    //endregion

    //region FOLLOWERS MANAGEMENT

    /**
     * Give a follower to the player.
     *
     * @param card The followed to be given.
     */
    void addFollower(Card card) {
        this.followers.push(card);
    }

    /**
     * Transfers the player's follower to their
     * hand.
     * <p>
     * This method is meant to only be called at
     * the beginning of phase 2
     */
    protected void followersToHand() {
        this.hand.clear();

        this.hand.addAll(this.followers);

        this.sortHand();
    }
    //endregion

    //region SCORE MANAGEMENT

    /**
     * Adds a card to the player's score stack.
     *
     * @param card The card to be added to the score.
     */
    void addToScore(Card card) {
        this.scoreStack.push(card);
    }

    /**
     * Returns the number of cards of a faction the
     * player has won (i.e. is in its score stack).
     *
     * @param faction The faction we want to count the cards of.
     * @return The number of cards of the faction in the player's
     * score stack.
     * @see ScoreStack#getNbCardsFaction(Faction)
     */
    int nbCardsFaction(Faction faction) {
        return this.scoreStack.getNbCardsFaction(faction);
    }

    /**
     * Returns the value of the strongest card of
     * the given faction in the player's score stack.
     *
     * @param faction The faction we want to get the max value of.
     * @return the value of the strongest card of the faction
     * in the player's score stack. -1 if the player has no card of this faction.
     * @see ScoreStack#maxValueFaction(Faction)
     */
    int maxValueFaction(Faction faction) {
        return this.scoreStack.maxValueFaction(faction);
    }
    //endregion

    //region OVERRIDES
    @Override
    public String toString() {
        return "Hand : " + this.hand + "\nScore : " + this.scoreStack + "\nFollowers : " + this.followers;
    }
    //endregion

    //region GETTERS

    /**
     * Returns the player's hand.
     *
     * @return The player's hand.
     */
    public Hand getCards() {
        return this.hand;
    }

    /**
     * Returns the player's score stack.
     *
     * @return The player's score stack.
     */
    public ScoreStack getScoreStack() {
        return this.scoreStack;
    }

    /**
     * Returns the player's followers.
     *
     * @return The player's followers.
     */
    public Stack<Card> getFollowers() {
        return this.followers;
    }

    /**
     * Returns the players ID (0 or 1).
     *
     * @return The player's ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns true if the player is an AI.
     * <p>
     * This method is meant to be overridden by
     * the AI class.
     * <p>
     * This method, like every other empty method
     * here, exists in this class so that the model
     * can stay independent from the AI (by not having
     * to import it and to cast the player down to it).
     *
     * @return true if the player is an AI.
     */
    public boolean isAI() {
        return false;
    }
    //endregion

    //region RESET

    /**
     * Reset the player by clearing their hand, their followers
     * and their score stack.
     */
    public void reset() {
        this.hand.clear();
        this.scoreStack.clear();
        this.followers.clear();
    }
    //endregion

    /**
     * Show a card to the player.
     * <p>
     * This method is meant to be overridden by the
     * AI class.
     * This method is called when a hidden card is revealed
     * to the player (so, when the opponent plays it, or
     * when it is drawn from the deck).
     * <p>
     * This method, like every other empty method
     * here, exists in this class so that the model
     * can stay independent from the AI (by not having
     * to import it and to cast the player down to it).
     *
     * @param card The card we want to show to the player
     */
    public void showCard(Card card) {
    }

    /**
     * Shows the flipped card to the player.
     *
     * <p>
     * This method is meant to be overridden by the
     * AI class.
     * This method is called when the flipped card is revealed
     * to the player (so, when it is drawn from the deck, after
     * a trick ends in the first phase).
     * <p>
     * This method, like every other empty method
     * here, exists in this class so that the model
     * can stay independent from the AI (by not having
     * to import it and to cast the player down to it).
     *
     * @param card The flipped card.
     */
    public void showFlippedCard(Card card) {
    }

    /**
     * Describes the action taken by a player
     * at the beginning of their turn.
     * <p>
     * Since this method exists to automatize an action,
     * it is obviously meant to be overridden by the AI
     * class.
     * <p>
     * This method, like every other empty method
     * here, exists in this class so that the model
     * can stay independent from the AI (by not having
     * to import it and to cast the player down to it).
     */
    public void action() {
    }

    /**
     * Initialize the AI at the start of the game
     * <p>
     * This method is meant to be overridden by the
     * AI class.
     * <p>
     * This method, like every other empty method
     * here, exists in this class so that the model
     * can stay independent from the AI (by not having
     * to import it and to cast the player down to it).
     */
    public void init() {
    }

    /**
     * Returns a copy of the player.
     *
     * @return a copy of the player.
     */
    Player copy() {
        Player player = new Player(this.id);

        player.hand.addAll(this.hand);
        this.scoreStack.forEach(player.scoreStack::push);
        player.followers.addAll(this.followers);

        return player;
    }
}
