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
        board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        board.addPiece(move.getStartPosition(), null);

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
        ChessPosition kingSquare = findPiece(teamColor, ChessPiece.PieceType.KING);
        Collection <ChessPosition> enemySquares = enemyPiecePositions(teamColor);
        for (ChessPosition square : enemySquares) {
            Collection <ChessMove> piecesMoves = board.getPiece(square).pieceMoves(board, square);
            for (ChessMove move : piecesMoves) {
                if (move.getEndPosition() == kingSquare) {
                    return true;
                }
            }
        }
        return false;
    }

    public Collection <ChessPosition> enemyPiecePositions(TeamColor teamColor) {
        Collection <ChessPosition> enemyPositions = new HashSet<>();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                if (isEnemyPiece(teamColor, checkSquare)) {
                    enemyPositions.add(checkSquare);
                }
            }
        }
        return enemyPositions;
    }

    public ChessPosition findPiece(TeamColor teamColor, ChessPiece.PieceType pieceType) throws RuntimeException {
        ChessPiece targetPiece = new ChessPiece(teamColor, pieceType);
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                ChessPiece currPiece = board.getPiece(checkSquare);
                if (currPiece != null && currPiece.equals(targetPiece)) {
                    return checkSquare;
                }
            }
        }
        throw new RuntimeException("Could not find that piece on the given board");
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
        throw new RuntimeException("Not implemented yet");
        //return (isInCheck(teamColor) && validMoves(TODO) == null);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented yet");
        //return (!isInCheck(teamColor) && validMoves(TODO) == null);
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
