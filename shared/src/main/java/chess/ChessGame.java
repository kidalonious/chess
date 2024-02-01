package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board = new ChessBoard();
    private TeamColor currTeam = TeamColor.WHITE;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.currTeam = team;
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
        if (this.board.getPiece(startPosition) == null) {
            return null;
        }
        return this.board.getPiece(startPosition).pieceMoves(board, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if (!moves.contains(move)) {
            throw new InvalidMoveException();
        }

        if (currTeam == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        if (currTeam == TeamColor.BLACK) {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingSquare = findKing(teamColor);
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                if (isEnemyPiece(getTeamTurn(), checkSquare)) {
                    board.getPiece(checkSquare).pieceMoves(board, checkSquare);

                }
            }
        }
        return false;
    }

    public ChessPosition findKing(TeamColor teamColor) throws RuntimeException {
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                if (Objects.equals(board.getPiece(checkSquare), new ChessPiece(teamColor, ChessPiece.PieceType.KING))) {
                    return checkSquare;
                }
            }
        }
        throw new RuntimeException();
    }

    public boolean isEnemyPiece(TeamColor teamColor, ChessPosition startPosition) {
        if (board.getPiece(startPosition) != null) { return board.getPiece(startPosition).getTeamColor() != teamColor;}
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && validMoves(TODO) == null;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
        return this.board;
    }
}
