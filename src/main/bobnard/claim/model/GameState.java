package bobnard.claim.model;

/**
 * Represents the current game's state.
 */
public enum GameState {
    WAITING_PLAYER_INITIALISATION,
    READY_TO_START,
    WAITING_LEADER_ACTION,
    WAITING_FOLLOW_ACTION,
    TRICK_FINISHED,
    SIMULATED_DRAWN_CARD,
    FIRST_PHASE_FINISHED,
    SECOND_PHASE_FINISHED,
    GAME_FINISHED
}
