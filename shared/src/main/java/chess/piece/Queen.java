package chess.piece;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Queen extends PieceMoveUtils {

    public static Collection<ChessMove> queenMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();

        upRightCheck(board, position, color, currRow, currCol, potentialMoves);
        upCheck(board, position, color, currRow, currCol, potentialMoves);
        upLeftCheck(board, position, color, currRow, currCol, potentialMoves);
        rightCheck(board, position, color, currRow, currCol, potentialMoves);
        downRightCheck(board, position, color, currRow, currCol, potentialMoves);
        downCheck(board, position, color, currRow, currCol, potentialMoves);
        downLeftCheck(board, position, color, currRow, currCol, potentialMoves);
        leftCheck(board, position, color, currRow, currCol, potentialMoves);

        return potentialMoves;
    }
}
