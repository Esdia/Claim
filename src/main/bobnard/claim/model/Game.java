package bobnard.claim.model;

import java.util.Stack;

/**
 * Represents the game
 */
public class Game {
    private GameState state;

    private Phase phase;
    private final Player[] players;

    private boolean isDone;
    private int winnerID;

    private boolean isSimulator;

    /**
     * Creates a new game.
     * <p>
     * Before the game is ready to start, it is necessary
     * to give it a list of players.
     *
     * @see #setPlayers(Player[])
     */
    public Game() {
        players = new Player[2];

        this.isDone = false;
        this.isSimulator = false;

        this.setState(GameState.WAITING_PLAYER_INITIALISATION);
    }

    /**
     * This constructor is used to make copies of the game.
     *
     * @param players A copy of the game's players
     * @param phase   A copy of the game's phase.
     * @param state   The game's current state.
     */
    private Game(Player[] players, Phase phase, boolean isDone, int winnerID, GameState state) {
        this.players = players;
        this.phase = phase;
        this.isDone = isDone;
        this.winnerID = winnerID;
        this.setState(state);
    }

    /**
     * Sets the game's players.
     * <p>
     * The player list must contains two non-null players.
     *
     * @param players The game's players.
     */
    public void setPlayers(Player[] players) {
        this.players[0] = players[0];
        this.players[1] = players[1];
        this.setState(GameState.READY_TO_START);
    }

    /**
     * Configure the game as a simulator.
     * <p>
     * A simulator game is meant to be used by the AIs to calculate
     * their next moves, and will behave a bit differently.
     * For example, it wont check whether or not the player is allowed
     * to play a card it is trying to play.
     */
    public void setSimulator() {
        this.isSimulator = true;
        this.phase.setSimulator();
    }

    //region GAME MANAGEMENT

    /**
     * Switches the current player.
     * <p>
     * 1 -> 0 and 0 -> 1.
     */
    public void changePlayer() {
        this.phase.changePlayer();
    }

    /**
     * Returns true if the game is finished.
     * <p>
     * The game is finished once the second phase is finished.
     *
     * @return true if the game is finished.
     */
    public boolean isDone() {
        return this.isDone;
    }

    private void endGame() {
        this.isDone = true;

        int factionsPlayerZero = 0;

        int n0, n1;
        int nMax0, nMax1;

        for (Faction faction : Faction.values()) {
            n0 = players[0].nbCardsFaction(faction);
            n1 = players[1].nbCardsFaction(faction);

            if (n0 > n1) {
                factionsPlayerZero++;
            } else if (n0 == n1) {
                nMax0 = players[0].maxValueFaction(faction);
                nMax1 = players[1].maxValueFaction(faction);

                if (nMax0 > nMax1) {
                    factionsPlayerZero++;
                }
            }
        }

        this.winnerID = factionsPlayerZero >= 3 ? 1 : 2;

        System.out.println("Player " + winnerID + " won the game!");
    }

    /**
     * Resets the game.
     * <p>
     * This method resets the players, but the game still needs
     * to be manually started before it can be played again.
     *
     * @see #start()
     */
    public void reset() {
        this.isDone = false;
        for (Player player : players) {
            player.reset();
        }
    }

    /**
     * Starts the game's first phase.
     */
    public void start() {
        if (this.state != GameState.READY_TO_START) {
            throw new IllegalStateException();
        }
        this.startPhaseOne();

        for (Player player : players) {
            player.init(); /* Used for initializing the AI */
        }

        this.setState(GameState.STARTED_PHASE_ONE);
    }

    /**
     * Returns true if the game is currently waiting for a player
     * to play a card. The player can be a human or an AI.
     *
     * @return true if the game is waiting for a player to play a card.
     * @see #isWaitingHumanAction()
     */
    public boolean isWaitingAction() {
        return (this.state == GameState.WAITING_LEADER_ACTION || this.state == GameState.WAITING_FOLLOW_ACTION);
    }

    /**
     * Returns true if the game is currently waiting for a human player
     * to play a card.
     *
     * @return true if the game is waiting for a human player to play a card.
     * @see #isWaitingAction()
     */
    public boolean isWaitingHumanAction() {
        return this.isWaitingAction() && !this.isCurrentPlayerAI();
    }

    private void setState(GameState state) {
        // System.out.println("state = " + state);
        this.state = state;
    }

    /**
     * Advances to the game's next step.
     * <p><br>
     * For example :
     * <br>
     * If the trick just ended, the next step is to clean it up
     * and wait for a new action.
     * <br>
     * If the first phase just ended, the next step is to start
     * the second one.
     */
    public void nextStep() {
        switch (this.state) {
            case READY_TO_START -> this.start();
            /* This state exists to let the UI start the audio */
            case STARTED_PHASE_ONE -> this.setState(GameState.WAITING_LEADER_ACTION);
            case WAITING_LEADER_ACTION, WAITING_FOLLOW_ACTION -> {
                /*
                 * Here, the method playCard (called by AI.action,
                 * or when a player clicks on a card) changes the state
                 * for us.
                 */
                if (this.isCurrentPlayerAI()) {
                    this.getCurrentPlayer().action();
                }
            }
            case TRICK_FINISHED, SIMULATED_DRAWN_CARD -> {
                this.endTrick();
                if (this.phase.isDone()) {
                    if (this.getPhaseNum() == 1) {
                        this.setState(GameState.FIRST_PHASE_FINISHED);
                    } else {
                        this.setState(GameState.SECOND_PHASE_FINISHED);
                    }
                } else {
                    this.setState(GameState.WAITING_LEADER_ACTION);
                }
            }
            case FIRST_PHASE_FINISHED -> {
                this.startPhaseTwo();
                this.setState(GameState.WAITING_LEADER_ACTION);
            }
            case SECOND_PHASE_FINISHED -> {
                this.endGame();
                this.setState(GameState.GAME_FINISHED);
            }
            case GAME_FINISHED -> {
                this.reset();
                this.setState(GameState.READY_TO_START);
            }
        }
    }

    /**
     * Returns the game's current state
     *
     * @return the game's current state
     */
    public GameState getState() {
        return this.state;
    }
    //endregion

    //region PHASE MANAGEMENT

    /**
     * Returns the game's current phase's number (1 or 2)
     *
     * @return the game's current phase's number (1 or 2)
     */
    public int getPhaseNum() {
        return this.phase.getPhaseNum();
    }

    private void startPhaseOne() {
        System.out.println("Beginning phase 1");
        this.phase = new PhaseOne(this.players);
    }

    private void startPhaseTwo() {
        if (this.getPhaseNum() != 1) {
            throw new IllegalStateException();
        }
        System.out.println("Beginning phase 2");
        this.phase = new PhaseTwo(players);
        if (this.isSimulator) {
            this.phase.setSimulator();
        }
    }
    //endregion

    //region TRICK MANAGEMENT

    /**
     * Checks if the current player is allowed to play the given
     * card.
     *
     * @param card The card the current player is trying to play.
     * @return true if the current player is allowed to play the card.
     * @see Phase#isLegalMove(Card)
     */
    public boolean isLegalMove(Card card) {
        return this.phase.isLegalMove(card);
    }

    /**
     * Plays a card.
     * <p>
     * This method plays a card and changes the game state accordingly.
     * It will fail silently if the move is illegal.
     *
     * @param card the played card.
     * @see Phase#playCard(Card)
     */
    public void playCard(Card card) {
        if (!this.isSimulator && !this.isLegalMove(card)) {
            return;
        }

        this.phase.playCard(card);

        this.setState(switch (this.state) {
            case WAITING_LEADER_ACTION -> GameState.WAITING_FOLLOW_ACTION;
            case WAITING_FOLLOW_ACTION -> GameState.TRICK_FINISHED;
            default -> throw new IllegalStateException();
        });

        if (this.state == GameState.WAITING_FOLLOW_ACTION) {
            this.changePlayer();
        }
    }

    /**
     * Returns true if the current trick is ready (i.e. both
     * players played a card).
     *
     * @return true if the trick is ready.
     * @see Phase#trickReady()
     * @see Trick#isReady()
     */
    public boolean trickReady() {
        return this.phase.trickReady();
    }

    /**
     * Returns the faction of the card played by the leader.
     *
     * @return The faction played by the leader. Null if
     * the leader has not played yet.
     * @see Phase#getPlayedFaction()
     * @see Trick#getFaction()
     */
    public Faction getPlayedFaction() {
        return this.phase.getPlayedFaction();
    }

    /**
     * Ends the trick.
     * <p>
     * This method manages the followings:
     * - Sets the next trick's leader to the current trick's winner.
     * - Do something with the played cards (depending on the phase)
     * - Starts a new trick
     *
     * @see Phase#endTrick()
     */
    public void endTrick() {
        this.phase.endTrick();
    }
    //endregion

    //region AI

    /**
     * Returns true if the current player is an AI.
     *
     * @return true if the current player is an AI.
     */
    public boolean isCurrentPlayerAI() {
        return this.getCurrentPlayer().isAI();
    }

    /**
     * Simulates a move.
     * <p>
     * This method is meant to be used by the AIs when
     * they are calculating their next moves.
     *
     * @param card The played card.
     * @throws IllegalStateException is the game is not a simulator
     */
    public void simulatePlay(Card card) {
        if (!this.isSimulator) {
            throw new IllegalStateException();
        }

        if (this.state == GameState.TRICK_FINISHED && this.getPhaseNum() == 1) {
            ((PhaseOne) phase).setDrawnCard(card);
            this.setState(GameState.SIMULATED_DRAWN_CARD);
        } else if (this.state == GameState.SIMULATED_DRAWN_CARD && this.getPhaseNum() == 1) {
            this.nextStep();
            ((PhaseOne) phase).setFlippedCard(card);
        } else {
            this.playCard(card);
        }

        if (
                (this.state == GameState.TRICK_FINISHED || this.state == GameState.SIMULATED_DRAWN_CARD)
                        && getPhaseNum() == 1 && !phase.isDone()
        ) return;

        while (!this.isWaitingAction()) {
            this.nextStep();
        }
    }
    //endregion

    //region GETTERS

    /**
     * Returns the current player's ID.
     *
     * @return the current player's ID.
     * @see Phase#getCurrentPlayer()
     */
    public int getCurrentPlayerID() {
        return this.phase.getCurrentPlayer();
    }

    /**
     * Returns the current player.
     *
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return this.getPlayer(this.getCurrentPlayerID());
    }

    /**
     * Returns the player with the given ID.
     *
     * @param id the player's ID.
     * @return the player with the given ID.
     */
    public Player getPlayer(int id) {
        return this.players[id];
    }

    /**
     * Returns the cards of the player with the given ID.
     *
     * @param playerID the player's ID.
     * @return the cards of the player with the given ID.
     */
    public Hand getCards(int playerID) {
        return this.players[playerID].getCards();
    }
    
    
    public Stack<Card> getFollowers(int playerID){
    	return this.players[playerID].getFollowers();
    }

    /**
     * Returns a list of the cards played in the current trick.
     * <p>
     * The list will always have a size of two, and its first
     * element will always be the card played by the leader.
     * Some elements may be null if no card has been played.
     *
     * @return a list of the cards played in the current trick.
     * @see Phase#getPlayedCards()
     */
    public Card[] getPlayedCards() {
        return this.phase.getPlayedCards();
    }

    /**
     * Returns the currently flipped card.
     *
     * @return the currently flipped card. Null if on phase two.
     * @see PhaseOne#getFlippedCard()
     */
    public Card getFlippedCard() {
        if (this.getPhaseNum() == 1) {
            return ((PhaseOne) this.phase).getFlippedCard();
        } else {
            return null;
        }
    }

    /**
     * Returns the cards the given player can play, according to the
     * given faction.
     *
     * @param player  The player's whose playable cards we want to get.
     * @param faction The faction determining the legal cards.
     * @return The player's playable cards
     */
    public Hand getPlayableCards(int player, Faction faction) {
        return this.players[player].playableCards(faction);
    }

    /**
     * Returns the cards the given player can play, according to the
     * faction played by the leader.
     *
     * @param player The player's whose playable cards we want to get.
     * @return The player's playable cards
     */
    public Hand getPlayableCards(int player) {
        return this.getPlayableCards(player, this.getPlayedFaction());
    }

    /**
     * Returns the cards the current player can play, according to the
     * faction played by the leader.
     *
     * @return The current player's playable cards
     */
    public Hand getPlayableCards() {
        return this.getPlayableCards(this.getCurrentPlayerID());
    }

    /**
     * Returns the ID of the game's winner.
     *
     * @return the ID of the game's winner.
     * @throws IllegalStateException if the game is not done.
     */
    public int getWinnerID() {
        if (!this.isDone()) {
            throw new IllegalStateException();
        }
        return this.winnerID;
    }

    /**
     * Returns the winner of the current trick.
     *
     * @return The winner of the current trick.
     * @throws IllegalStateException if the trick is not ready.
     * @see Phase#getTrickWinnerID()
     * @see Trick#getWinner()
     */
    public int getTrickWinnerID() {
        return this.phase.getTrickWinnerID();
    }

    /**
     * Returns true if the given card is currently playable.
     *
     * @param card The card we want to check.
     * @return true if the card is currently playable.
     */
    public boolean getLegalCard(Card card) {
        return getPlayableCards(getCurrentPlayerID(), getPlayedFaction()).contains(card);
    }
    //endregion

    //region DEBUG PRINT

    /**
     * Prints some debug infos.
     */
    public void printDebugInfo() {
        Card[] playedCards = this.getPlayedCards();

        System.out.println("------ DEBUG ------");
        if (this.getFlippedCard() != null) {
            System.out.println("flipped card : " + this.getFlippedCard());
        }
        System.out.println("Player 0 :");
        System.out.println(this.players[0]);
        System.out.println("Played card : " + playedCards[0]);
        System.out.println("\nPlayer 1 :");
        System.out.println(this.players[1]);
        System.out.println("Played card : " + playedCards[1]);
        System.out.println("---- END DEBUG ----");
    }
    //endregion

    /**
     * Creates a copy of the game.
     *
     * @return a copy of the game.
     */
    public Game copy() {
        Player[] players = new Player[]{
                this.players[0].copy(),
                this.players[1].copy()
        };

        Game copy = new Game(
                players,
                this.phase.copy(players),
                this.isDone,
                this.winnerID,
                this.getState()
        );

        if (this.isSimulator) {
            copy.setSimulator();
        }

        return copy;
    }
}
