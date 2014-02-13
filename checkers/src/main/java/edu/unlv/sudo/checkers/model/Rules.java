package edu.unlv.sudo.checkers.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the rules utilities for the checkers game.
 */
public class Rules {

    public static Set<Location> getValidMoves(final Piece piece, final Board board) {
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

        if (isSpaceOnBoard(moveNW, board) && board.getPieceAtLocation(moveNW) == null) {
            validMoves.add(moveNW);
        }
        if (isSpaceOnBoard(moveNE, board) && board.getPieceAtLocation(moveNE) == null) {
            validMoves.add(moveNE);
        }
        if (isSpaceOnBoard(moveSW, board) && board.getPieceAtLocation(moveSW) == null && piece.isKing()) {
            validMoves.add(moveSW);
        }
        if (isSpaceOnBoard(moveSE, board) && board.getPieceAtLocation(moveSE) == null && piece.isKing()) {
            validMoves.add(moveSE);
        }

        if (isSpaceOnBoard(jumpNW, board) && board.getPieceAtLocation(jumpNW) == null && board.getPieceAtLocation(moveNW) != null) {
            validMoves.add(jumpNW);
        }
        if (isSpaceOnBoard(jumpNE, board) && board.getPieceAtLocation(jumpNE) == null && board.getPieceAtLocation(moveNE) != null) {
            validMoves.add(jumpNE);
        }
        if (isSpaceOnBoard(jumpSW, board) && board.getPieceAtLocation(jumpSW) == null && board.getPieceAtLocation(moveSW) != null && piece.isKing()) {
            validMoves.add(jumpSW);
        }
        if (isSpaceOnBoard(jumpSE, board) && board.getPieceAtLocation(jumpSE) == null && board.getPieceAtLocation(moveSE) != null && piece.isKing()) {
            validMoves.add(jumpSE);
        }

        return validMoves;
    }

    public static boolean isSpaceOnBoard(final Location location, final Board board) {
        return location.getX() >= 0 && location.getX() < board.getSpacesPerSide()
                && location.getY() >= 0 && location.getY() < board.getSpacesPerSide();
    }

    public static boolean isValidMove(final Piece piece, final List<Location> previousMoves, final Location move, final Board board) {
        final Piece theoreticalPiece;

        if (previousMoves.size() > 0) {
            theoreticalPiece = new Piece(piece.getTeam(), previousMoves.get(previousMoves.size() - 1));
        } else {
            theoreticalPiece = piece;
        }

        return getValidMoves(theoreticalPiece, board).contains(move);
    }
}
