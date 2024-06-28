package chess.pieceMove;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class Knight extends ChessMoveUtils {
    public static Collection<ChessMove> knightMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();

        // up and right
        int nextRow = currRow + 2;
        int nextCol = currCol + 1;

        if (nextRow < 9 && nextCol < 9) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(position, nextPosition, null);
            addPotentialMove(board, nextPosition, nextMove, color, potentialMoves);
        }

        // up and left
        nextRow = currRow + 2;
        nextCol = currCol - 1;

        if (nextRow < 9 && nextCol > 0) {
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
            ChessMove nextMove = new ChessMove(position, nextPosition, null);
            addPotentialMove(board, nextPosition, nextMove, color, potentialMoves);
        }

        // down and right
        nextRow = currRow - 2;
        nextCol =
    }
}
