package edu.unlv.sudo.checkers.model;

import java.util.Iterator;
import java.util.List;

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
        this(id, new Board(), turn);
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

    public void move(final Piece piece, final List<Location> moves) {
        if (piece.getTeam() != turn) {
            throw new OutOfTurnException(turn, piece.getTeam());
        }

        for (Location location : moves) {
            if (!Rules.getValidMoves(piece, board).contains(location)) {
                throw new InvalidMoveException(piece, location);
            }

            final Iterator<Piece> iterator = board.getPieces().iterator();
            while (iterator.hasNext()) {
                final Piece jumped = iterator.next();
                if (jumped.getLocation().isBetween(piece.getLocation(), location)) {
                    iterator.remove();
                }
            }

            piece.setLocation(location);

            if (piece.getTeam() == Team.RED) {
                if (piece.getLocation().getY() == board.getSpacesPerSide() - 1) {
                    piece.makeKing();
                }
            } else if (piece.getTeam() == Team.BLACK) {
                if (piece.getLocation().getY() == 0) {
                    piece.makeKing();
                }
            }
        }

        if (turn == Team.RED) {
            turn = Team.BLACK;
        } else if (turn == Team.BLACK) {
            turn = Team.RED;
        }
    }
}
