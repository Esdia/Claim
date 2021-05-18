package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandTest {

    @Test
    void sort() {
        Random random = new Random();

        ArrayList<Card> cards = new ArrayList<>();
        Hand hand = new Hand();

        Card c;
        for (int i = 0; i < 50; i++) {
            c = new Card(
                    Faction.values()[random.nextInt(5)],
                    random.nextInt(8)+2
            );
            cards.add(c);
        }

        hand.addAll(cards);

        cards.sort(Card::compareTo);
        hand.sort();

        assertEquals(cards, hand);
    }

    ArrayList<Card> toArrayList(Stream<Card> cards) {
        return cards.collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    void playableCards1() {
        Random random = new Random();
        Hand fullHand = new Hand();

        for (int i = 0; i < 25; i++) {
            for (Faction faction: Faction.values()) {
                fullHand.add(new Card(faction, random.nextInt(8) + 2));
            }
        }

        ArrayList<Card> playableCards;
        ArrayList<Card> expected;
        for (Faction faction: Faction.values()) {
            playableCards = fullHand.playableCards(faction);

            expected = toArrayList(fullHand.getCards(faction));
            assertTrue(playableCards.containsAll(expected));
            playableCards.removeAll(expected);

            if (faction != Faction.DOPPELGANGERS) {
                expected = toArrayList(fullHand.getCards(Faction.DOPPELGANGERS));
                assertTrue(playableCards.containsAll(expected));
                playableCards.removeAll(expected);
            }

            assertTrue(playableCards.isEmpty());
        }
    }

    @Test
    void playableCards2() {
        Player player = new Player();

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