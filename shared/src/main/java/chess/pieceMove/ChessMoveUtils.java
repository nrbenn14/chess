package chess.pieceMove;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;


import java.util.HashSet;

public class ChessMoveUtils {

    // First check for an enemy
    public static boolean checkEnemy(ChessBoard board, ChessPosition targetPosition, ChessGame.TeamColor color) {
        return (board.getPiece(targetPosition) != null) && (board.getPiece(targetPosition).getTeamColor() != color);
    }

    public static void addPotentialMove(ChessBoard board, ChessPosition targetPosition, ChessMove nextMove,
                                 ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        if (checkEnemy(board, targetPosition, color)) {
            potentialMoves.add(nextMove);
        }

        else if (board.getPiece(targetPosition) == null) {
            potentialMoves.add(nextMove);
        }
    }

    static void upRightCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                             int currRow, int currCol, HashSet<ChessMove> possibleMoves) {
        int nextRow = currRow++;
        int nextCol = currCol--;

        // make sure we don't break out of the board
        while (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(position, nextPosition, null);

            // is there a piece?
            if (board.getPiece(nextPosition) != null) {
                // if so, is it an enemy piece? otherwise break
                if (board.getPiece(nextPosition).getTeamColor() != color) {
                    possibleMoves.add(nextMove);
                }
                break;
            }

            // nothing there, possible move
            else {
                possibleMoves.add(nextMove);
            }

            // incr for next check
            nextRow++;
            nextCol++;
        }
    }
    static void upLeftCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                            int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}
    static void downRightCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                               int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}
    static void downLeftCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                              int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}
    static void upCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                        int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}
    static void downCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                          int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}
    static void rightCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                           int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}
    static void leftCheck(ChessBoard board, ChessPosition position, ChessGame.TeamColor color,
                          int currRow, int currCol, HashSet<ChessMoveUtils> possibleMoves) {}

}
