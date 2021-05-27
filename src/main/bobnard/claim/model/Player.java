package bobnard.claim.model;

import java.util.Stack;

public class Player {
    private final int id;

    private final Hand hand;
    private final ScoreStack scoreStack;
    private final Stack<Card> followers;

    public Player(int id) {
        this.id = id;
        this.hand = new Hand();
        this.scoreStack = new ScoreStack();
        this.followers = new Stack<>();
    }

    //region HAND MANAGEMENT
    boolean hasCards() {
        return !this.hand.isEmpty();
    }

    void addCard(Card card) {
        this.hand.add(card);
    }

    void removeCard(Card card) {
        this.hand.remove(card);
    }

    void sortHand() {
        this.hand.sort();
    }

    /*
     * Return the cards the player can play, given the faction played
     * by the leader
     */
    protected Hand playableCards(Faction faction) {
        return this.hand.playableCards(faction);
    }
    //endregion

    //region FOLLOWERS MANAGEMENT
    void addFollower(Card card) {
        this.followers.push(card);
    }

    void followersToHand() {
        this.hand.clear();

        this.hand.addAll(this.followers);

        this.sortHand();
    }
    //endregion

    //region SCORE MANAGEMENT
    void addToScore(Card card) {
        this.scoreStack.push(card);
    }

    int nbCardsFaction(Faction faction) {
        return this.scoreStack.getNbCardsFaction(faction);
    }

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
    public Hand getCards() {
        return this.hand;
    }

    public ScoreStack getScoreStack() {
        return this.scoreStack;
    }

    public int getId() {
        return this.id;
    }

    public boolean isAI() {
        return false;
    }
    //endregion

    //region RESET
    public void reset() {
        this.hand.clear();
        this.scoreStack.clear();
        this.followers.clear();
    }
    //endregion

    /*
    * Meant to be overridden.
    * These methods only exists in this class so that
    * the model can be independent from the AI package
    */
    public void showCard(Card card) {}
    public void action() {}

    Player copy() {
        Player player = new Player(this.id);

        player.hand.addAll(this.hand);
        player.scoreStack.addAll(this.scoreStack);
        player.followers.addAll(this.followers);

        return player;
    }
}
