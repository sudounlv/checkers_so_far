package edu.unlv.sudo.checkers.model.exception;

/**
 * This exception represents an invalid board.
 */
public class InvalidBoardException extends RuntimeException {

    private int spacesPerSide;

    public InvalidBoardException(final int spacesPerSide) {
        this.spacesPerSide = spacesPerSide;
    }

    public int getSpacesPerSide() {
        return spacesPerSide;
    }
}
