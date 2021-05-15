package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {
    boolean checkContainsAll(List<Card> l1, List<Card> l2) {
        for (Card c: l2) {
            if (!l1.remove(c)) {
                return false;
            }
        }

        return true;
    }

    @Test
    void playableCards1() {
        HashMap<Faction, ArrayList<Card>> cards = new HashMap<>();

        cards.put(Faction.GOBLINS, new ArrayList<>());
        cards.put(Faction.KNIGHTS, new ArrayList<>());
        cards.put(Faction.DWARVES, new ArrayList<>());
        cards.put(Faction.UNDEADS, new ArrayList<>());
        cards.put(Faction.DOPPELGANGERS, new ArrayList<>());

        Random random = new Random();

        for (int i = 0; i < 25; i++) {
            for (Faction faction: Faction.values()) {
                cards.get(faction).add(new Card(faction, random.nextInt(8) + 2));
            }
        }

        Player player = new Player(0); //TODO

        for (Faction faction: Faction.values()) {
            cards.get(faction).forEach(player::addCard);
        }

        ArrayList<Card> playableCards;
        for (Faction faction: Faction.values()) {
            playableCards = player.playableCards(faction);

            assertTrue(this.checkContainsAll(playableCards, cards.get(faction)));

            if (faction != Faction.DOPPELGANGERS) {
                assertTrue(this.checkContainsAll(playableCards, cards.get(Faction.DOPPELGANGERS)));
            }

            assertTrue(playableCards.isEmpty());
        }
    }

    @Test
    void playableCards2() {
        Player player = new Player(0); //TODO

        Card kni = new Card(Faction.KNIGHTS, 5);
        Card und = new Card(Faction.UNDEADS, 5);
        Card dop = new Card(Faction.DOPPELGANGERS, 5);

        ArrayList<Card> cards;

        // Make sure you can play with the right faction
        player.addCard(und);
        cards = player.playableCards(Faction.UNDEADS);
        assertEquals(cards, player.getCards());

        // Make sure you can also play the doppelganger
        player.addCard(dop);
        cards = player.playableCards(Faction.UNDEADS);
        assertEquals(cards, player.getCards());

        // Make sure you can only play the doppelganger
        cards = player.playableCards(Faction.DOPPELGANGERS);
        assertEquals(1, cards.size());
        assertTrue(cards.contains(dop));

        // Make sure you can play everything
        cards = player.playableCards(Faction.DWARVES);
        assertEquals(cards, player.getCards());

        player.addCard(kni);
        cards = player.playableCards(Faction.DWARVES);
        assertEquals(cards, player.getCards());
    }
}