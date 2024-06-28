package chess.pieceMove;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class Rook extends ChessMoveUtils {

    public static Collection<ChessMove> rookMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();


        upCheck(board, position, color, currRow, currCol, potentialMoves);
        downCheck(board, position, color, currRow, currCol, potentialMoves);
        rightCheck(board, position, color, currRow, currCol, potentialMoves);
        leftCheck(board, position, color, currRow, currCol, potentialMoves);

        return potentialMoves;
    }
}
