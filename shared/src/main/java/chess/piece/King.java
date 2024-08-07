package chess.piece;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class King extends PieceMoveUtils {
     public static HashSet<ChessMove> kingMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
         int currRow = position.getRow();
         int currCol = position.getColumn();
         HashSet<ChessMove> potentialMoves = new HashSet<>();

         kingUp(board, position, currRow, currCol, color, potentialMoves);
         kingDown(board, position, currRow, currCol, color, potentialMoves);
         kingLeft(board, position, currRow, currCol, color, potentialMoves);
         kingRight(board, position, currRow, currCol, color, potentialMoves);
         kingUpRight(board, position, currRow, currCol, color, potentialMoves);
         kingUpLeft(board, position, currRow, currCol, color, potentialMoves);
         kingDownRight(board, position, currRow, currCol, color, potentialMoves);
         kingDownLeft(board, position, currRow, currCol, color, potentialMoves);

         return potentialMoves;
     }

    private static void kingDownLeft(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                     ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextCol;
        int nextRow;

        nextRow = currRow - 1;
        nextCol = currCol - 1;
        if (nextRow > 0 && nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void kingDownRight(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                      ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextCol;
        int nextRow;

        nextRow = currRow - 1;
        nextCol = currCol + 1;
        if (nextRow > 0 && nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void kingUpLeft(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                   ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextCol;
        int nextRow;

        nextRow = currRow + 1;
        nextCol = currCol - 1;
        if (nextRow < 9 && nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void kingUpRight(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                    ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextCol;
        int nextRow;

        nextRow = currRow + 1;
        nextCol = currCol + 1;
        if (nextRow < 9 && nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, nextCol);
        }
    }

    private static void kingRight(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                  ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextCol;

        nextCol = currCol + 1;

        if (nextCol < 9) {
            makeKingKnightMove(board, position, color, potentialMoves, currRow, nextCol);
        }
    }

    private static void kingLeft(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                 ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextCol;

        nextCol = currCol - 1;

        if (nextCol > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, currRow, nextCol);
        }
    }

    private static void kingDown(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                 ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        int nextRow;

        nextRow = currRow - 1;

        if (nextRow > 0) {
            makeKingKnightMove(board, position, color, potentialMoves, nextRow, currCol);
        }
    }

    private static void kingUp(ChessBoard board, ChessPosition position, int currRow, int currCol,
                               ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
         int nextRow;

         nextRow = currRow + 1;

         if (nextRow < 9) {
             makeKingKnightMove(board, position, color, potentialMoves, nextRow, currCol);
         }
    }
}
