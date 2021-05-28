package bobnard.claim.model;

/**
 * This class represents a Card.
 * A card is represented by a Faction and a Value
 */
public class Card implements Comparable<Card> {
    public final Faction faction;
    public final int value;

    public final String name;

    /**
     * Creates a new card.
     *
     * @param faction The card's faction
     * @param value The card's value.
     *
     * @throws IllegalArgumentException if the card's value is invalid (out of [0;9], or [2;9] if the faction is Knights)
     */
    Card(Faction faction, int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException();
        }

        if (faction == Faction.KNIGHTS && value < 2) {
            throw new IllegalArgumentException();
        }

        this.name = faction.toString() + value;
        this.faction = faction;
        this.value = value;
    }

    /**
     * Returns the cards name.
     * name = faction + value (ex: DWARVES7)
     *
     * @return A printable string representing the card
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns true if the given object is equal to the card.
     *
     * @param o The object to be compared with the card.
     * @return true if the given object is equal to the card.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Card)) return false;

        Card c = (Card) o;

        return c.faction == this.faction && c.value == this.value;
    }

    /**
     * Compare two cards.
     *
     * Factions are compared first (according to the orders they are
     * declared in the Faction enum class).
     * If two cards have the same faction, their values are compared.
     * If two cards have the same faction and the same value, then they
     * are equals.
     *
     * @param o The card to be compared with this card.
     * @return 0 if x == y, a negative value if x < y, a positive value if x > y
     */
    @Override
    public int compareTo(Card o) {
        if (this.faction == o.faction) {
            return Integer.compare(this.value, o.value);
        } else {
            return this.faction.compareTo(o.faction);
        }
    }
}
