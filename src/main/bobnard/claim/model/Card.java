package bobnard.claim.model;

import bobnard.claim.UI.*;

public class Card implements Comparable<Card> {
    public final Faction faction;
    public final int value;
    public String name;
    
    public CardUI myGui;

    public Card(Faction faction, int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException();
        }

        if (faction == Faction.KNIGHTS && value < 2) {
            throw new IllegalArgumentException();
        }

        this.name = faction.toString() + value;
        this.faction = faction;
        this.value = value;
        myGui = new CardUI(this);
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

    @Override
    public int compareTo(Card o) {
        if (this.faction == o.faction) {
            return Integer.compare(this.value, o.value);
        } else {
            return this.faction.compareTo(o.faction);
        }
    }
}
