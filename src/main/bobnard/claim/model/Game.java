package bobnard.claim.model;

import bobnard.claim.AI.AI;
import bobnard.claim.AI.AIMinimax;
import bobnard.claim.AI.AIRandom;
import bobnard.claim.AI.Difficulty;
import bobnard.claim.UI.Audio;

public class Game {
    GameState state;

    private Phase phase;
    private final Player[] players;

    private boolean isDone;
    private int winnerID;

    public Game(boolean vsAI) {
        players = new Player[2];
        players[1] = new Player(1);
        if(vsAI) players[0] = new AIMinimax(this, 0, Difficulty.EASY);
        else players[0] = new Player(0);

        this.isDone = false;

        this.setState(GameState.WAITING_LEADER_ACTION);

        this.startPhaseOne();
    }

    private Game(Player[] players, Phase phase, boolean isDone, int winnerID, GameState state) {
        this.players = players;
        this.phase = phase;
        this.isDone = isDone;
        this.winnerID = winnerID;
        this.setState(state);
    }

    //region GAME MANAGEMENT
    public void changePlayer() {
        this.phase.changePlayer();
    }

    public boolean isDone() {
        return this.isDone;
    }

    private void endGame() {
        this.isDone = true;

        int factionsPlayerZero = 0;

        int n0, n1;
        int nMax0, nMax1;

        for (Faction faction: Faction.values()) {
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

    public void reset() {
        this.isDone = false;
        for (Player player: players) {
            player.reset();
        }
        Audio.getBGM().stop();
        this.startPhaseOne();
    }

    public boolean isWaitingAction() {
        return (this.state == GameState.WAITING_LEADER_ACTION || this.state == GameState.WAITING_FOLLOW_ACTION);
    }

    public boolean isWaitingHumanAction() {
        return this.isWaitingAction() && !this.isCurrentPlayerAI();
    }

    private void setState(GameState state) {
        System.out.println("state = " + state);
        this.state = state;
    }

    public void nextStep() {
        switch (this.state) {
            case WAITING_LEADER_ACTION, WAITING_FOLLOW_ACTION -> {
                /*
                * Here, the method playCard (called by AI.action,
                * or when a player clicks on a card) changes the state
                * for us.
                */
                if (this.isCurrentPlayerAI()) {
                    ((AI) this.getCurrentPlayer()).action();
                }
            }
            case TRICK_FINISHED -> {
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
                this.setState(GameState.WAITING_LEADER_ACTION);
            }
        }
    }

    public GameState getState() {
        return this.state;
    }
    //endregion

    //region PHASE MANAGEMENT
    int getPhaseNum() {
        if (this.phase instanceof PhaseOne) {
            return 1;
        } else {
            return 2;
        }
    }

    private void startPhaseOne() {
        Audio.playBGM(1);
        System.out.println("Beginning phase 1");
        this.phase = new PhaseOne(this.players);
    }

    private void startPhaseTwo() {
        if (this.getPhaseNum() != 1) {
            throw new IllegalStateException();
        }
        Audio.getBGM().stop();
        Audio.playBGM(2);
        System.out.println("Beginning phase 2");
        this.phase = new PhaseTwo(players);
    }
    //endregion

    //region TRICK MANAGEMENT
    public boolean isLegalMove(Card card) {
        return this.phase.isLegalMove(card);
    }

    public void playCard(Card card) {
        if (!this.isLegalMove(card)) {
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

    public boolean trickReady() {
        return this.phase.trickReady();
    }

    public Faction getPlayedFaction() {
        return this.phase.getPlayedFaction();
    }

    public void endTrick() {
        this.phase.endTrick();
    }
    //endregion

    //region AI
    public boolean isCurrentPlayerAI() {
        return this.getCurrentPlayer() instanceof AI;
    }

    public void simulatePlay(Card card) {
        this.playCard(card);
        while (!this.isWaitingAction()) {
            this.nextStep();
        }
    }
    //endregion

    //region GETTERS
    public int getCurrentPlayerID() {
        return this.phase.getCurrentPlayer();
    }

    public Player getCurrentPlayer() {
        return this.getPlayer(this.getCurrentPlayerID());
    }

    public Player getPlayer(int id) {
        return this.players[id];
    }

    public Hand getCards(int playerID) {
        return this.players[playerID].getCards();
    }

    public Card[] getPlayedCards() {
        return this.phase.getPlayedCards();
    }

    public Card getFlippedCard() {
        if (this.phase instanceof PhaseOne) {
            return ((PhaseOne) this.phase).getFlippedCard();
        } else {
            return null;
        }
    }

    public Hand getPlayableCards(int player, Faction faction) {
        return this.players[player].playableCards(faction);
    }

    public Hand getPlayableCards(int player) {
        return this.getPlayableCards(player, this.getPlayedFaction());
    }

    public Hand getPlayableCards() {
        return this.getPlayableCards(this.getCurrentPlayerID());
    }

    public int getWinnerID() {
        if (!this.isDone()) {
            throw new IllegalStateException();
        }
        return this.winnerID;
    }
    
    public int getTrickWinnerID() {
         return this.phase.getTrickWinnerID();
    }
    
    public boolean getLegalCard(Card card) {
    	return getPlayableCards(getCurrentPlayerID(), getPlayedFaction()).contains(card);
    }
    //endregion

    //region DEBUG PRINT
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

    public Game copy() {
        Player[] players = new Player[] {
                this.players[0].copy(),
                this.players[1].copy()
        };

        return new Game(
                players,
                this.phase.copy(players),
                this.isDone,
                this.winnerID,
                this.getState()
        );
    }
}
