package bobnard.claim.model;

import java.util.ArrayList;

public class Hand extends ArrayList<Card> {
    public void sort() {
        this.sort(Card::compareTo);
    }
}
