package chess.piece;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class Bishop extends PieceMoveUtils {

    public static Collection<ChessMove> bishopMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();

        upRightCheck(board, position, color, currRow, currCol, potentialMoves);
        upLeftCheck(board, position, color, currRow, currCol, potentialMoves);
        downRightCheck(board, position, color, currRow, currCol, potentialMoves);
        downLeftCheck(board, position, color, currRow, currCol, potentialMoves);

        return potentialMoves;
    }
}
