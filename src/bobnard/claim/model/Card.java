package bobnard.claim.model;

public class Card {
    public final Faction faction;
    public final int value;

    public Card(Faction faction, int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException();
        }

        if (faction == Faction.KNIGHTS && value < 2) {
            throw new IllegalArgumentException();
        }

        this.faction = faction;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.faction.name() + " " + this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Card)) return false;

        Card c = (Card) o;

        return c.faction == this.faction && c.value == this.value;
    }
}
