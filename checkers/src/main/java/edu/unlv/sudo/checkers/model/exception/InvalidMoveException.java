package edu.unlv.sudo.checkers.model.exception;

import edu.unlv.sudo.checkers.model.Location;
import edu.unlv.sudo.checkers.model.Piece;

/**
 * This exception represents an invalid move.
 */
public class InvalidMoveException extends RuntimeException {

    private Piece piece;
    private Location location;

    public InvalidMoveException(final Piece piece, final Location location) {
        this.piece = piece;
        this.location = location;
    }

    public Piece getPiece() {
        return piece;
    }

    public Location location() {
        return location;
    }
}
