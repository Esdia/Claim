package bobnard.claim.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class ScoreStack extends Stack<Card> {
    private final HashMap<Faction, Integer> occ;

    public ScoreStack() {
        super();

        this.occ = new HashMap<>();

        Arrays.asList(
                Faction.values()
        ).forEach(f -> this.occ.put(f, 0));
    }

    @Override
    public Card push(Card card) {
        super.push(card);
        this.occ.put(card.faction, this.occ.get(card.faction) + 1);
        return card;
    }

    @Override
    public Card pop() {
        Card card = super.pop();
        this.occ.put(card.faction, this.occ.get(card.faction) - 1);
        return card;
    }

    public boolean wonFaction(Faction faction) {
        int max = switch (faction) {
            case KNIGHTS -> 8;
            case GOBLINS -> 14;
            default -> 10;
        };

        if (this.occ.get(faction) > max/2) {
            return true;
        } else {
            // If there is a tie, the player with the 9 wins
            return this.contains(new Card(faction, 9));
        }
    }
}
