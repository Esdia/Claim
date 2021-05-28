package bobnard.claim.model;

/**
 * Represents a trick.
 */
class Trick {
    private Card c1; /* Card played by the leader */
    private Card c2; /* Card played by the other player */

    private final int leader;

    /**
     * Creates a new trick.
     *
     * @param leader The trick's leader (0 or 1).
     */
    Trick(int leader) {
        this.c1 = null;
        this.c2 = null;

        this.leader = leader;
    }

    //region UTILS

    /**
     * Returns true if the trick is ready (i.e. both
     * players played a card).
     *
     * @return true if the trick is ready.
     */
    boolean isReady() {
        return this.c1 != null && this.c2 != null;
    }

    /**
     * Add a card to the trick.
     *
     * @param card   The played card.
     * @param leader True if the player is the trick's leader.
     */
    void addCard(Card card, boolean leader) {
        if (leader) {
            this.c1 = card;
        } else {
            this.c2 = card;
        }
    }
    //endregion

    //region GETTERS

    /**
     * Returns the ID of the trick's leader.
     *
     * @return The ID of the trick's leader.
     * @throws IllegalStateException if the trick is not ready.
     */
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

    /**
     * Returns the card played by the leader.
     *
     * @return The card played by the leader.
     */
    Card getC1() {
        return c1;
    }

    /**
     * Returns the card played by the following player.
     *
     * @return The card played by the following player.
     */
    Card getC2() {
        return c2;
    }

    /**
     * Returns the faction of the card played by the leader.
     *
     * @return The faction played by the leader. Null if
     * the leader has not played yet.
     */
    Faction getFaction() {
        return this.c1 == null ? null : c1.faction;
    }
    //endregion

    /**
     * Returns a copy of the trick.
     *
     * @return a copy of the trick.
     */
    Trick copy() {
        Trick trick = new Trick(this.leader);
        trick.addCard(this.getC1(), true);
        trick.addCard(this.getC2(), false);
        return trick;
    }
}
