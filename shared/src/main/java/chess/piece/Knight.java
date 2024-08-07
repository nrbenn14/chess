package chess.piece;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class Knight extends PieceMoveUtils {
    public static Collection<ChessMove> knightMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();


        knightUpRight(board, position, currRow, currCol, color, potentialMoves);
        knightUpLeft(board, position, currRow, currCol, color, potentialMoves);
        knightDownRight(board, position, currRow, currCol, color, potentialMoves);
        knightDownLeft(board, position, currRow, currCol, color, potentialMoves);
        knightRightUp(board, position, currRow, currCol, color, potentialMoves);
        knightRightDown(board, position, currRow, currCol, color, potentialMoves);
        knightLeftUp(board, position, currRow, currCol, color, potentialMoves);
        knightLeftDown(board, position, currRow, currCol, color, potentialMoves);

        return potentialMoves;
    }

    private static void knightLeftDown(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                       ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow - 1;
        int nextCol = currCol - 2;

        if (nextRow > 0 && nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightLeftUp(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                     ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow + 1;
        int nextCol = currCol - 2;

        if (nextRow < 9 && nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightRightDown(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                        ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow - 1;
        int nextCol = currCol + 2;

        if (nextRow > 0 && nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightRightUp(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                      ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow + 1;
        int nextCol = currCol + 2;

        if (nextRow < 9 && nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightDownLeft(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                       ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow - 2;
        int nextCol = currCol - 1;

        if (nextRow > 0 && nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightDownRight(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                        ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow - 2;
        int nextCol = currCol + 1;

        if (nextRow > 0 && nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightUpLeft(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                     ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        // up and left
        int nextRow = currRow + 2;
        int nextCol = currCol - 1;

        if (nextRow < 9 && nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void knightUpRight(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                      ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow = currRow + 2;
        int nextCol = currCol + 1;

        if (nextRow < 9 && nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }
}
