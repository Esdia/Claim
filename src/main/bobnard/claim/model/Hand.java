package bobnard.claim.model;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Hand extends ArrayList<Card> {
    public void sort() {
        this.sort(Card::compareTo);
    }

    Stream<Card> getCards(Faction faction) {
        return this.stream().filter(c -> c.faction == faction);
    }

    /*
     * Return the cards the player can play, given the faction played
     * by the leader
     */
    Hand playableCards(Faction faction) {
        Hand cards = new Hand();

        if (faction == null) {
            cards.addAll(this);
            return cards;
        }

        getCards(faction).forEach(cards::add);

        if (cards.size() > 0) {
            if (faction != Faction.DOPPELGANGERS) {
                // We only need to add the doppelgangers, if they are not the asked faction
                getCards(Faction.DOPPELGANGERS).forEach(cards::add);
            }
        } else {
            // We can play anything
            cards.addAll(this);
        }

        return cards;
    }
}
