package bobnard.claim.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    static Hand randomHand() {
        Random random = new Random();

        Hand hand = new Hand();

        for (int i = 0; i < 50; i++) {
            hand.add(new Card(
                    Faction.values()[random.nextInt(5)],
                    random.nextInt(8) + 2
            ));
        }

        return hand;
    }

    @Test
    void testFollowerToHand() {
        Player player = new Player();

        Hand hand = randomHand();
        hand.forEach(player::addCard);

        Hand followers = randomHand();
        followers.forEach(player::addFollower);

        player.followersToHand();
        followers.sort(Card::compareTo);

        assertEquals(followers, player.getCards());
    }
}