package bobnard.claim.model;

import java.io.Serializable;

/**
 * Represents the current game's state.
 */
public enum GameState implements Serializable {
    WAITING_PLAYER_INITIALISATION,
    READY_TO_START,
    STARTED_PHASE_ONE,
    WAITING_LEADER_ACTION,
    WAITING_FOLLOW_ACTION,
    TRICK_FINISHED,
    FIRST_PHASE_FINISHED,
    SECOND_PHASE_FINISHED,
    GAME_FINISHED
}
