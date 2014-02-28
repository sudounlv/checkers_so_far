package edu.unlv.sudo.checkers.model.exception;

import edu.unlv.sudo.checkers.model.Team;

/**
 * This represents an exception when a player tries to move out of turn.
 */
public class OutOfTurnException extends RuntimeException {

    private Team attemptedToMove;
    private Team turn;

    public OutOfTurnException(final Team turn, final Team attemptedToMove) {
        this.turn = turn;
        this.attemptedToMove = attemptedToMove;
    }

    public Team getTurn() {
        return turn;
    }

    public Team getAttemptedToMove() {
        return attemptedToMove;
    }
}
