package chess.piece;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;


import java.util.HashSet;

public class PieceMoveUtils {

    private static boolean makeMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color, HashSet<ChessMove> possibleMoves, int nextRow, int nextCol) {
        ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
        ChessMove nextMove = new ChessMove(position, nextPosition, null);

        // is there a piece?
        if (board.getPiece(nextPosition) != null) {
            // if so, is it an enemy piece? otherwise break
            if (board.getPiece(nextPosition).getTeamColor() != color) {
                possibleMoves.add(nextMove);
            }
            return true;
        }

        // nothing there, possible move
        else {
            possibleMoves.add(nextMove);
        }
        return false;
    }

    public static boolean checkEnemy(ChessBoard board, ChessPosition targetPosition, ChessGame.TeamColor color) {
        return (board.getPiece(targetPosition) != null) && (board.getPiece(targetPosition).getTeamColor() != color);
    }

    public static void addPotentialMove(ChessBoard board, ChessPosition targetPosition, ChessMove nextMove,
                                 ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        // First check for an enemy
        if (checkEnemy(board, targetPosition, color)) {
            potentialMoves.add(nextMove);
        }

        else if (board.getPiece(targetPosition) == null) {
            potentialMoves.add(nextMove);
        }
    }

    static void upRightCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                             int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow + 1;
        int nextCol = currCol + 1;

        // make sure we don't break out of the board
        while (nextRow < 9 && nextCol < 9) {
            if (makeMove(board, position, color, possibleMoves, nextRow, nextCol)) break;

            // incr for next check
            nextRow++;
            nextCol++;
        }
    }
    static void upLeftCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                            int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow + 1;
        int nextCol = currCol - 1;

        // make sure we don't break out of the board
        while (nextRow < 9 && nextCol > 0) {
            if (makeMove(board, position, color, possibleMoves, nextRow, nextCol)) break;

            // incr for next check
            nextRow++;
            nextCol--;
        }
    }

    static void downRightCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                               int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow - 1;
        int nextCol = currCol + 1;

        // make sure we don't break out of the board
        while (nextRow > 0 && nextCol < 9) {
            if (makeMove(board, position, color, possibleMoves, nextRow, nextCol)) break;

            // incr for next check
            nextRow--;
            nextCol++;
        }
    }
    static void downLeftCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                              int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow - 1;
        int nextCol = currCol - 1;

        // make sure we don't break out of the board
        while (nextRow > 0 && nextCol > 0) {
            if (makeMove(board, position, color, possibleMoves, nextRow, nextCol)) break;

            // incr for next check
            nextRow--;
            nextCol--;
        }
    }
    static void upCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                        int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow + 1;

        // make sure we don't break out of the board
        while (nextRow < 9) {
            if (makeMove(board, position, color, possibleMoves, nextRow, currCol)) break;

            // incr for next check
            nextRow++;
        }
    }
    static void downCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                          int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow - 1;

        // make sure we don't break out of the board
        while (nextRow > 0) {
            if (makeMove(board, position, color, possibleMoves, nextRow, currCol)) break;

            // incr for next check
            nextRow--;
        }
    }
    static void rightCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                           int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextCol = currCol + 1;

        // make sure we don't break out of the board
        while (nextCol < 9) {
            if (makeMove(board, position, color, possibleMoves, currRow, nextCol)) break;

            // incr for next check
            nextCol++;
        }
    }
    static void leftCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                          int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextCol = currCol - 1;

        // make sure we don't break out of the board
        while (nextCol > 0) {
            if (makeMove(board, position, color, possibleMoves, currRow, nextCol)) break;

            // incr for next check
            nextCol--;
        }
    }

}
