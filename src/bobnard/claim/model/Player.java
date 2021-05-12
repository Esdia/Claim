package bobnard.claim.model;

import java.util.ArrayList;
import java.util.Stack;

public class Player {
    private final Hand hand;
    private final ScoreStack scoreStack;
    private final Stack<Card> followers;

    Player() {
        this.hand = new Hand();
        this.scoreStack = new ScoreStack();
        this.followers = new Stack<>();
    }

    void addToScore(Card card) {
        this.scoreStack.push(card);
    }

    void addFollower(Card card) {
        this.followers.push(card);
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

    boolean hasCards() {
        return !this.hand.isEmpty();
    }

    boolean wonFaction(Faction faction) {
        return this.scoreStack.wonFaction(faction);
    }

    void followersToHand() {
        this.hand.clear();

        this.hand.addAll(this.followers);

        this.hand.sort();
    }

    Hand getCards() {
        return this.hand;
    }

    /*
    * Return the cards the player can play, given the faction played
    * by the leader
    */
    ArrayList<Card> playableCards(Faction faction) {
        ArrayList<Card> cards = new ArrayList<>();
        boolean canFollow = false;

        for (Card c: this.hand) {
            if (c.faction == faction) {
                canFollow = true;
                cards.add(c);
            }
        }

        if (canFollow) {
            // We only need to add the doppelgangers, if they are not the asked faction
            if (faction != Faction.DOPPELGANGERS) {
                for (Card c : this.hand) {
                    if (c.faction == Faction.DOPPELGANGERS) {
                        cards.add(c);
                    }
                }
            }
        } else {
            // We can play anything
            cards.addAll(this.hand);
        }

        return cards;
    }

    @Override
    public String toString() {
        return "Hand : " + this.hand + "\nScore : " + this.scoreStack + "\nFollowers : " + this.followers;
    }
}
