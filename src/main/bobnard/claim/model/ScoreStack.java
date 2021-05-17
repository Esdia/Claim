package bobnard.claim.model;

import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Stream;

public class ScoreStack extends Stack<Card> {
    private final HashMap<Faction, Integer> occ;

    ScoreStack() {
        super();

        this.occ = new HashMap<>();

        for (Faction faction: Faction.values()) {
            this.occ.put(faction, 0);
        }
    }

    //region UTILS
    private Stream<Card> getCards(Faction faction) {
        return this.stream().filter(c -> c.faction == faction);
    }

    int maxValueFaction(Faction faction) {
        Optional<Card> maxCard = this.getCards(faction).max(Card::compareTo);

        if (maxCard.isPresent()) {
            return maxCard.get().value;
        } else {
            throw new IllegalStateException();
        }
    }
    //endregion

    //region GETTERS
    public int getNbCardsFaction(Faction faction) {
        return this.occ.get(faction);
    }
    //endregion

    //region OVERRIDES
    @Override
    public Card push(Card card) {
        super.push(card);
        this.occ.computeIfPresent(card.faction, (k, v) -> v + 1);
        return card;
    }

    @Override
    public Card pop() {
        Card card = super.pop();
        this.occ.computeIfPresent(card.faction, (k, v) -> v - 1);
        return card;
    }
    //endregion
}
