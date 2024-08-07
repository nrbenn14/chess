package chess.piece;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Pawn extends PieceMoveUtils {
    public static Collection<ChessMove> pawnMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();

        whitePawnMove(board, position, currRow, currCol, color, potentialMoves);
        blackPawnMove(board, position, currRow, currCol, color, potentialMoves);

        return potentialMoves;
    }

    private static void whitePawnMove(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                      ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        if (color == (ChessGame.TeamColor.WHITE)) {
            int nextRow = currRow + 1;
            int takeLeft = currCol - 1;
            int takeRight = currCol + 1;
            int firstMove = currRow + 2;

            ChessPosition forwardOne = new ChessPosition(nextRow, currCol);
            ChessPosition forwardTwo = new ChessPosition(firstMove, currCol);
            ChessPosition captureRight = new ChessPosition(nextRow, takeRight);
            ChessPosition captureLeft = new ChessPosition(nextRow, takeLeft);

            moveWhite(board, position, currRow, color, potentialMoves, nextRow, forwardOne, forwardTwo, takeRight, captureRight, takeLeft, captureLeft);
        }
    }

    private static void moveWhite(ChessBoard board, ChessPosition position, int currRow, ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves,
                                  int nextRow, ChessPosition forwardOne, ChessPosition forwardTwo, int takeRight, ChessPosition captureRight,
                                  int takeLeft, ChessPosition captureLeft) {
        if (nextRow < 9) {
            // is there a piece?
            if (board.getPiece(forwardOne) == null) {
                // can move into promotion?
                if (nextRow == 8) {
                    promotePiece(position, potentialMoves, forwardOne);
                }
                // first move of pawn?
                else if (currRow == 2 && board.getPiece(forwardTwo) == null) {
                    potentialMoves.add(new ChessMove(position, forwardOne, null));
                    potentialMoves.add(new ChessMove(position, forwardTwo, null));
                }
                // normal front move
                else {
                    potentialMoves.add(new ChessMove(position, forwardOne, null));
                }
            }

            // check for capture right
            if (takeRight < 9) {
                // is there an enemy piece?
                if (board.getPiece(captureRight) != null && board.getPiece(captureRight).getTeamColor() != color) {
                    // capture into promotion?
                    if (nextRow == 8) {
                        promotePiece(position, potentialMoves, captureRight);
                    }
                    // normal capture
                    else {
                        potentialMoves.add(new ChessMove(position, captureRight, null));
                    }
                }
            }

            // check for capture left
            if (takeLeft >= 1) {
                // is there an enemy piece?
                if (board.getPiece(captureLeft) != null && board.getPiece(captureLeft).getTeamColor() != color) {
                    // capture into promotion?
                    if (nextRow == 8) {
                        promotePiece(position, potentialMoves, captureLeft);
                    }
                    // normal capture
                    else {
                        potentialMoves.add(new ChessMove(position, captureLeft, null));
                    }
                }
            }
        }
    }

    private static void blackPawnMove(ChessBoard board, ChessPosition position, int currRow, int currCol,
                                      ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        if (color == (ChessGame.TeamColor.BLACK)) {
            int nextRow = currRow - 1;
            // does the orientation of R/L matter?
            // newsflash - it does
            int takeLeft = currCol + 1;
            int takeRight = currCol - 1;
            int firstMove = currRow - 2;

            ChessPosition forwardOne = new ChessPosition(nextRow, currCol);
            ChessPosition forwardTwo = new ChessPosition(firstMove, currCol);
            ChessPosition captureRight = new ChessPosition(nextRow, takeRight);
            ChessPosition captureLeft = new ChessPosition(nextRow, takeLeft);

            moveBlack(board, position, currRow, color, potentialMoves, nextRow, forwardOne, forwardTwo, takeRight, captureRight, takeLeft, captureLeft);
        }
    }

    private static void moveBlack(ChessBoard board, ChessPosition position, int currRow, ChessGame.TeamColor color,
                                  HashSet<ChessMove> potentialMoves, int nextRow, ChessPosition forwardOne,
                                  ChessPosition forwardTwo, int takeRight, ChessPosition captureRight, int takeLeft, ChessPosition captureLeft) {
        if (nextRow > 0) {
            // is there a piece?
            if (board.getPiece(forwardOne) == null) {
                // can move into promotion?
                if (nextRow == 1) {
                    promotePiece(position, potentialMoves, forwardOne);
                }
                // first move of pawn?
                else if (currRow == 7 && board.getPiece(forwardTwo) == null) {
                    potentialMoves.add(new ChessMove(position, forwardOne, null));
                    potentialMoves.add(new ChessMove(position, forwardTwo, null));
                }
                // normal front move
                else {
                    potentialMoves.add(new ChessMove(position, forwardOne, null));
                }
            }

            // check for capture left
            if (takeRight >= 1) {
                // is there an enemy piece?
                if (board.getPiece(captureRight) != null && board.getPiece(captureRight).getTeamColor() != color) {
                    // capture into promotion?
                    if (nextRow == 1) {
                        promotePiece(position, potentialMoves, captureRight);
                    }
                    // normal capture
                    else {
                        potentialMoves.add(new ChessMove(position, captureRight, null));
                    }
                }
            }

            // check for capture left
            if (takeLeft < 9) {
                // is there an enemy piece?
                if (board.getPiece(captureLeft) != null  && board.getPiece(captureLeft).getTeamColor() != color) {
                    // capture into promotion
                    if (nextRow == 1) {
                        promotePiece(position, potentialMoves, captureLeft);
                    }
                    // normal capture
                    else {
                        potentialMoves.add(new ChessMove(position, captureLeft, null));
                    }
                }
            }
        }
    }

    private static void promotePiece(ChessPosition position, HashSet<ChessMove> potentialMoves, ChessPosition forwardOne) {
        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.BISHOP));
        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.KNIGHT));
        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.ROOK));
        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.QUEEN));
    }
}
