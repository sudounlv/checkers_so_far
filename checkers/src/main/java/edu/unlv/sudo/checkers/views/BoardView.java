package edu.unlv.sudo.checkers.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.unlv.sudo.checkers.model.Board;
import edu.unlv.sudo.checkers.model.Location;
import edu.unlv.sudo.checkers.model.Piece;
import edu.unlv.sudo.checkers.model.Team;

/**
 * This view represents the checkers board.
 */
public class BoardView extends View {

    private static final Logger LOGGER = Logger.getLogger(BoardView.class.getName());

    private static final int BOARD_COLOR_LIGHT = Color.LTGRAY;
    private static final int BOARD_COLOR_DARK = Color.DKGRAY;
    private static final int PIECE_COLOR_RED = Color.RED;
    private static final int PIECE_COLOR_BLACK = Color.BLACK;

    private Board board;

    private Canvas canvas;

    public BoardView(final Context context) {
        super(context);
        setOnTouchListener(new CheckersOnTouchListener(this));
    }

    public BoardView(final Context context, final AttributeSet attributes) {
        super(context, attributes);
        setOnTouchListener(new CheckersOnTouchListener(this));
    }

    public void setBoard(final Board board) {
        this.board = board;

        if (canvas != null) {
            drawBoard(board, canvas);
            drawPieces(board, canvas);
        }
    }

    public Board getBoard() {
        return board;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        this.canvas = canvas;
        drawBoard(board, canvas);
        drawPieces(board, canvas);
    }

    private void drawBoard(final Board board, final Canvas canvas) {

        if (board == null) {
            LOGGER.warning("Attempting to render board with no board set.");
            return;
        }

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

        if (board == null) {
            LOGGER.warning("Attempting to render board with no board set.");
            return;
        }

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

    private class CheckersOnTouchListener implements OnTouchListener {

        private BoardView boardView;

        private Piece selectedPiece;
        private List<Location> moves;

        public CheckersOnTouchListener(final BoardView boardView) {
            this.boardView = boardView;
        }

        @Override
        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            final Canvas canvas = boardView.getCanvas();
            final Board board = boardView.getBoard();

            final float x = motionEvent.getX();
            final float y = motionEvent.getY();

            final int boardWidth = Math.min(canvas.getHeight(), canvas.getWidth());
            final float squareWidth = (float) boardWidth / board.getSpacesPerSide();

            final Location location = new Location((int) Math.floor(x / squareWidth), (int) Math.floor(y / squareWidth));
            final Piece piece = board.getPieceAtLocation(location);

            if (piece != null) {
                selectedPiece = piece;
                moves = new ArrayList<>();
            } else if (selectedPiece != null && isValidMove(selectedPiece, moves, location)) {
                moves.add(location);
            }

            return true;
        }

        private boolean isValidMove(final Piece piece, final List<Location> previousMoves,
                                    final Location move) {
            //TODO: make this check for valid moves
            return true;
        }
    }
}
