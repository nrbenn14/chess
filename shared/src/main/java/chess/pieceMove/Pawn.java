package chess.pieceMove;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class Pawn extends ChessMoveUtils {
    public static Collection<ChessMove> pawnMove(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        int currRow = position.getRow();
        int currCol = position.getColumn();
        HashSet<ChessMove> potentialMoves = new HashSet<>();

        whitePawnMove(board, position, currRow, currCol, color, potentialMoves);
        blackPawnMove(board, position, currRow, currCol, color, potentialMoves);

        return potentialMoves;
    }

    private static void whitePawnMove(ChessBoard board, ChessPosition position, int currRow, int currCol, ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        if (color.equals(ChessGame.TeamColor.WHITE)) {
            int nextRow = currRow + 1;
            int takeLeft = currCol - 1;
            int takeRight = currCol + 1;
            int firstMove = currRow + 2;

            ChessPosition forwardOne = new ChessPosition(nextRow, currCol);
            ChessPosition forwardTwo = new ChessPosition(firstMove, currCol);
            ChessPosition captureRight = new ChessPosition(nextRow, takeRight);
            ChessPosition captureLeft = new ChessPosition(nextRow, takeLeft);

            if (nextRow < 9) {
                // is there a piece?
                if (board.getPiece(forwardOne) == null) {

                    // first move of pawn?
                    if (currRow == 2 && board.getPiece(forwardTwo) == null) {
                        potentialMoves.add(new ChessMove(position, forwardOne, null));
                        potentialMoves.add(new ChessMove(position, forwardTwo, null));
                    }
                    // can move into promotion?
                    else if (nextRow == 8) {
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.BISHOP));
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.KNIGHT));
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.ROOK));
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.QUEEN));
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

                        if (nextRow == 8) {
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.BISHOP));
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.ROOK));
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            potentialMoves.add(new ChessMove(position, captureRight, null));
                        }
                    }
                }

                // check for capture left
                if (takeRight >= 1) {
                    // is there an enemy piece?
                    if (board.getPiece(captureLeft) != null && board.getPiece(captureLeft).getTeamColor() != color) {

                        if (nextRow == 8) {
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.BISHOP));
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.ROOK));
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            potentialMoves.add(new ChessMove(position, captureLeft, null));
                        }
                    }
                }
            }
        }
    }

    private static void blackPawnMove(ChessBoard board, ChessPosition position, int currRow, int currCol, ChessGame.TeamColor color, HashSet<ChessMove> potentialMoves) {
        if (color.equals(ChessGame.TeamColor.WHITE)) {
            int nextRow = currRow - 1;
            int takeLeft = currCol + 1;
            int takeRight = currCol - 1;
            int firstMove = currRow - 2;

            ChessPosition forwardOne = new ChessPosition(nextRow, currCol);
            ChessPosition forwardTwo = new ChessPosition(firstMove, currCol);
            ChessPosition captureRight = new ChessPosition(nextRow, takeRight);
            ChessPosition captureLeft = new ChessPosition(nextRow, takeLeft);

            if (nextRow > 0) {
                // is there a piece?
                if (board.getPiece(forwardOne) == null) {

                    // first move of pawn?
                    if (currRow == 7 && board.getPiece(forwardTwo) == null) {
                        potentialMoves.add(new ChessMove(position, forwardOne, null));
                        potentialMoves.add(new ChessMove(position, forwardTwo, null));
                    }
                    // can move into promotion?
                    else if (nextRow == 1) {
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.BISHOP));
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.KNIGHT));
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.ROOK));
                        potentialMoves.add(new ChessMove(position, forwardOne, ChessPiece.PieceType.QUEEN));
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

                        if (nextRow == 1) {
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.BISHOP));
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.ROOK));
                            potentialMoves.add(new ChessMove(position, captureRight, ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            potentialMoves.add(new ChessMove(position, captureRight, null));
                        }
                    }
                }

                // check for capture left
                if (takeRight >= 1) {
                    // is there an enemy piece?
                    if (board.getPiece(captureLeft) != null && board.getPiece(captureLeft).getTeamColor() != color) {

                        if (nextRow == 1) {
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.BISHOP));
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.KNIGHT));
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.ROOK));
                            potentialMoves.add(new ChessMove(position, captureLeft, ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            potentialMoves.add(new ChessMove(position, captureLeft, null));
                        }
                    }
                }
            }
        }
    }


}
