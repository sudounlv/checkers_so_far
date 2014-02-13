package edu.unlv.sudo.checkers.model;

import java.util.HashSet;
import java.util.List;
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

    public void move(final Piece piece, final List<Location> moves) {
        if (piece.getTeam() != turn) {
            throw new OutOfTurnException(turn, piece.getTeam());
        }

        for (Location location : moves) {
            if (!getValidMoves(piece).contains(location)) {
                throw new InvalidMoveException(piece, location);
            }

            for (Piece jumped : board.getPieces()) {
                if (jumped.getLocation().isBetween(piece.getLocation(), location)) {
                    board.removePiece(jumped);
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
    }

    public Set<Location> getValidMoves(final Piece piece) {
        final Set<Location> validMoves = new HashSet<>();

        int moveY = 0;
        int jumpY = 0;

        //check for single moves
        if (piece.getTeam() == Team.RED) {
            moveY = 1;
            jumpY = 2;
        } else if (piece.getTeam() == Team.BLACK) {
            moveY = -1;
            jumpY = -2;
        }

        final int startX = piece.getLocation().getX();
        final int startY = piece.getLocation().getY();

        final Location moveNW = new Location(startX - 1, startY + moveY);
        final Location moveNE = new Location(startX + 1, startY + moveY);
        final Location moveSW = new Location(startX - 1, startY - moveY);
        final Location moveSE = new Location(startX + 1, startY - moveY);

        final Location jumpNW = new Location(startX - 2, startY + jumpY);
        final Location jumpNE = new Location(startX + 2, startY + jumpY);
        final Location jumpSW = new Location(startX - 2, startY - jumpY);
        final Location jumpSE = new Location(startX + 2, startY - jumpY);

        if (isSpaceOnBoard(moveNW) && board.getPieceAtLocation(moveNW) == null) {
            validMoves.add(moveNW);
        }
        if (isSpaceOnBoard(moveNE) && board.getPieceAtLocation(moveNE) == null) {
            validMoves.add(moveNE);
        }
        if (isSpaceOnBoard(moveSW) && board.getPieceAtLocation(moveSW) == null) {
            validMoves.add(moveSW);
        }
        if (isSpaceOnBoard(moveSE) && board.getPieceAtLocation(moveSE) == null) {
            validMoves.add(moveSE);
        }

        if (isSpaceOnBoard(jumpNW) && board.getPieceAtLocation(jumpNW) == null && board.getPieceAtLocation(moveNW) != null) {
            validMoves.add(jumpNW);
        }
        if (isSpaceOnBoard(jumpNE) && board.getPieceAtLocation(jumpNE) == null && board.getPieceAtLocation(moveNE) != null) {
            validMoves.add(jumpNE);
        }
        if (isSpaceOnBoard(jumpSW) && board.getPieceAtLocation(jumpSW) == null && board.getPieceAtLocation(moveSW) != null) {
            validMoves.add(jumpSW);
        }
        if (isSpaceOnBoard(jumpSE) && board.getPieceAtLocation(jumpSE) == null && board.getPieceAtLocation(moveSE) != null) {
            validMoves.add(jumpSE);
        }

        return validMoves;
    }

    public boolean isSpaceOnBoard(final Location location) {
        return location.getX() >= 0 && location.getX() < board.getSpacesPerSide()
                && location.getY() >= 0 && location.getY() < board.getSpacesPerSide();
    }

    public boolean isValidMove(final Piece piece, final List<Location> previousMoves, final Location move) {
        final Piece theoreticalPiece = new Piece(piece.getTeam(), previousMoves.get(previousMoves.size() - 1));
        return getValidMoves(theoreticalPiece).contains(move);
    }
}
