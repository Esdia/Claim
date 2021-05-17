package bobnard.claim.model;

class Trick {
    private Card c1; /* Card played by the leader */
    private Card c2; /* Card played by the other player */

    private final int leader;

    Trick(int leader) {
        this.c1 = null;
        this.c2 = null;

        this.leader = leader;
    }

    //region UTILS
    boolean isReady() {
        return this.c1 != null && this.c2 != null;
    }

    void addCard(Card card, boolean leader) {
        if (leader) {
            this.c1 = card;
        } else {
            this.c2 = card;
        }
    }
    //endregion

    //region GETTERS
    int getWinner() {
        if (!this.isReady()) {
            throw new IllegalStateException();
        }

        if (c1.faction == Faction.GOBLINS && c2.faction == Faction.KNIGHTS) {
            return 1 - leader;
        }

        // The other player could not follow
        if (c1.faction != c2.faction && c2.faction != Faction.DOPPELGANGERS) {
            return leader;
        }

        // Both players played the same faction (or doppelganger)
        return (c1.value >= c2.value) ? leader : 1 - leader;
    }

    Card getC1() {
        return c1;
    }

    Card getC2() {
        return c2;
    }

    Faction getFaction() {
        if (this.c1 == null) {
            throw new IllegalStateException();
        }

        return this.c1.faction;
    }
    //endregion
}
