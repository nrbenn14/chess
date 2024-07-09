package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor turn;
    private ChessBoard board = new ChessBoard();

    public ChessGame() {
        turn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> validMoves = new HashSet<>();
        ChessPiece movePiece = board.getPiece(startPosition);
        HashSet<ChessMove> potentialMoves = (HashSet<ChessMove>) movePiece.pieceMoves(board, startPosition);

        for (ChessMove moves : potentialMoves) {
            ChessGame nextBoard = new ChessGame();

            nextBoard.editBoard(board);

            nextBoard.board.addPiece(moves.getEndPosition(), movePiece);
            nextBoard.board.addPiece(moves.getStartPosition(), null);
            if (!nextBoard.isInCheck(movePiece.getTeamColor())) {
                validMoves.add(moves);
            }
        }

        return validMoves;
    }

    public void editBoard(ChessBoard currBoard) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition currPosition = new ChessPosition(i, j);
                if (currBoard.getPiece(currPosition) != null) {
                    this.board.addPiece(currPosition, currBoard.getPiece(currPosition));
                }
                else {
                    this.board.addPiece(currPosition, null);
                }
            }
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece movePiece = board.getPiece(move.getStartPosition());

        if (movePiece.getTeamColor() != turn) {
            throw new InvalidMoveException();
        }

        HashSet<ChessMove> movesList = (HashSet<ChessMove>) validMoves(move.getStartPosition());
        boolean legal = false;

        for (ChessMove moves : movesList) {
            if (move.equals(moves)) {

                legal = true;

                if (move.getPromotionPiece() != null) {
                    ChessPiece pawnPromote = new ChessPiece(turn, move.getPromotionPiece());
                    board.addPiece(move.getEndPosition(), pawnPromote);
                }

                else {
                    board.addPiece(move.getEndPosition(), new ChessPiece(turn, movePiece.getPieceType()));
                }

                board.addPiece(move.getStartPosition(), null);

                if (getTeamTurn() == TeamColor.BLACK) {
                    setTeamTurn(TeamColor.WHITE);
                }
                else {
                    setTeamTurn(TeamColor.BLACK);
                }

                break;
            }
        }

        if (!legal) {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                if ((board.getPiece(position) != null) && (board.getPiece(position).getTeamColor() != teamColor)) {
                    HashSet<ChessMove> potentialMoves = (HashSet<ChessMove>) board.getPiece(position).pieceMoves(board, position);

                    for (ChessMove moves : potentialMoves) {
                        if (board.getPiece(moves.getEndPosition()) != null) {
                            if (board.getPiece(moves.getEndPosition()).equals(new ChessPiece(teamColor, ChessPiece.PieceType.KING))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (isInCheck(teamColor) && isInStalemate(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPosition position = new ChessPosition(i,j);
                if ((board.getPiece(position) != null)) {
                    if (board.getPiece(position).getTeamColor() == teamColor) {
                        HashSet<ChessMove> potentialMoves = (HashSet<ChessMove>) validMoves(position);

                        return potentialMoves.isEmpty();
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return turn == chessGame.turn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "turn=" + turn +
                ", board=" + board +
                '}';
    }
}
