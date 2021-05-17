package bobnard.claim.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

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

    private Stream<Card> getCards(Faction faction) {
        return this.stream().filter(c -> c.faction == faction);
    }

    public int nbCardsFaction(Faction faction) {
        return (int) this.getCards(faction).count();
    }

    public int maxValueFaction(Faction faction) {
        Optional<Card> maxCard = this.getCards(faction).max(Card::compareTo);

        if (maxCard.isPresent()) {
            return maxCard.get().value;
        } else {
            throw new IllegalStateException();
        }
    }
}
