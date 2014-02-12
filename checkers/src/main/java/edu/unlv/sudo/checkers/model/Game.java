package edu.unlv.sudo.checkers.model;

import java.util.HashSet;
import java.util.Set;

import edu.unlv.sudo.checkers.model.exception.InvalidMoveException;
import edu.unlv.sudo.checkers.model.exception.OutOfTurnException;

/**
 * This class represents a game of checkers.
 */
public class Game {

    private String id;
    private Board board;
    private Team turn;

    public Game(final String id, final Team turn) {
        this(id, new Board(8), turn);
    }

    public Game(final String id, final Board board, final Team turn) {
        this.id = id;
        this.board = board;
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public Team getTurn() {
        return turn;
    }

    public String getId() {
        return id;
    }

    public void move(final Piece piece, final Location location) {
        if (piece.getTeam() != turn) {
            throw new OutOfTurnException(turn, piece.getTeam());
        }

        if (!getValidMoves(piece).contains(location)) {
            throw new InvalidMoveException(piece, location);
        }

        for (Piece jumped : board.getPieces()) {
            if (jumped.getLocation().isBetween(piece.getLocation(), location)) {
                board.removePiece(jumped);
            }
        }

        piece.setLocation(location);
    }

    public Set<Location> getValidMoves(final Piece piece) {
        return new HashSet<>();
    }
}
