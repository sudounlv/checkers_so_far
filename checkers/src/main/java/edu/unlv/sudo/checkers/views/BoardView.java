package edu.unlv.sudo.checkers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import edu.unlv.sudo.checkers.model.Board;
import edu.unlv.sudo.checkers.model.Piece;
import edu.unlv.sudo.checkers.model.Team;

/**
 * This view represents the checkers board.
 */
public class BoardView extends View {

    private static final int BOARD_COLOR_LIGHT = Color.LTGRAY;
    private static final int BOARD_COLOR_DARK = Color.DKGRAY;
    private static final int PIECE_COLOR_RED = Color.RED;
    private static final int PIECE_COLOR_BLACK = Color.BLACK;

    private Board board;

    public BoardView(final Context context, final Board board) {
        super(context);

        this.board = board;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        drawBoard(board, canvas);
        drawPieces(board, canvas);
    }

    private void drawBoard(final Board board, final Canvas canvas) {

        final int boardWidth = Math.min(canvas.getHeight(), canvas.getWidth());
        final float squareWidth = (float) boardWidth / board.getSpacesPerSide();

        boolean lightSquare = true;
        for (int xPos = 0; xPos < board.getSpacesPerSide(); xPos++) {
            lightSquare = !lightSquare;
            for (int yPos = 0; yPos < board.getSpacesPerSide(); yPos++) {
                lightSquare = !lightSquare;

                final Paint paint = new Paint();
                paint.setColor(lightSquare ? BOARD_COLOR_LIGHT : BOARD_COLOR_DARK);

                final float left = xPos * squareWidth;
                final float right = (xPos + 1) * squareWidth - 1;
                final float top = yPos * squareWidth;
                final float bottom = (yPos + 1) * squareWidth - 1;

                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    private void drawPieces(final Board board, final Canvas canvas) {

        final int boardWidth = Math.min(canvas.getHeight(), canvas.getWidth());
        final float squareWidth = (float) boardWidth / board.getSpacesPerSide();
        final float pieceRadius = squareWidth * .75F / 2;

        for (Piece piece : board.getPieces()) {
            final Paint paint = new Paint();
            paint.setColor(piece.getTeam() == Team.RED ? PIECE_COLOR_RED : PIECE_COLOR_BLACK);

            final int x = piece.getLocation().getX();
            final int y = piece.getLocation().getY();

            final float left = x * squareWidth + (squareWidth / 2);
            final float top = y * squareWidth + (squareWidth / 2);

            canvas.drawCircle(left, top, pieceRadius, paint);
        }

    }
}
