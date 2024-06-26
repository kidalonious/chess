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
    private TeamColor otherTeam = TeamColor.BLACK;
    public boolean isOver = false;
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
        if (team == TeamColor.WHITE) {
            this.otherTeam = TeamColor.BLACK;
        } else if (team == TeamColor.BLACK) {
            this.otherTeam = TeamColor.WHITE;
        }
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
        Collection<ChessMove> validMoves = new HashSet<>();
        if (!spaceOccupied(startPosition)) {
            return null;
        }
        Collection<ChessMove> pieceMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        for (ChessMove move : pieceMoves) {
            if (isValidMove(move)) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    public ChessBoard cloneBoard(ChessBoard board) {
        ChessBoard clonedBoard = new ChessBoard();
        for (int row = 1; row <=8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition square = new ChessPosition(row, col);
                clonedBoard.addPiece(square, board.getPiece(square));
            }
        }
        return clonedBoard;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if (moves.contains(move) && board.getPiece(move.getStartPosition()).getTeamColor() == currTeam) {
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                board.addPiece(move.getStartPosition(), null);
            } else {
                board.addPiece(move.getEndPosition(), new ChessPiece(currTeam, move.getPromotionPiece()));
                board.addPiece(move.getStartPosition(), null);
            }
            setTeamTurn(otherTeam);
        } else {
            throw new InvalidMoveException();
        }
    }
    public void endGame() {
        isOver = true;
    }

    public boolean isValidMove(ChessMove move) {
        ChessBoard oldBoard = board;
        ChessBoard clonedBoard = cloneBoard(board);
        ChessPiece currPiece = clonedBoard.getPiece(move.getStartPosition());
        clonedBoard.addPiece(move.getEndPosition(), currPiece);
        clonedBoard.addPiece(move.getStartPosition(), null);
        setBoard(clonedBoard);
        if (isInCheck(currPiece.getTeamColor())) {
            setBoard(oldBoard);
            return false;
        }
        setBoard(oldBoard);
        return true;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingSquare = findKing(teamColor);
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                if (spaceOccupied(checkSquare) && board.getPiece(checkSquare).getTeamColor() != teamColor) {
                    Collection<ChessMove> pieceMoves = board.getPiece(checkSquare).pieceMoves(board, checkSquare);
                    for (ChessMove move : pieceMoves) {
                        if (move.getEndPosition().equals(kingSquare)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ChessPosition findKing(TeamColor teamColor) {
        ChessPiece targetPiece = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                ChessPiece currPiece = board.getPiece(checkSquare);
                if (spaceOccupied(checkSquare) && currPiece.equals(targetPiece)) {
                    return checkSquare;
                }
            }
        }
        return new ChessPosition(0,0);
    }

    public boolean spaceOccupied(ChessPosition square) {
        return board.getPiece(square) != null;
    }
    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                if (board.getPiece(checkSquare) != null && board.getPiece(checkSquare).getTeamColor() == teamColor && validMoves(checkSquare).isEmpty() && isInCheck(teamColor)) {
                    endGame();
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition checkSquare = new ChessPosition(row, col);
                if (board.getPiece(checkSquare) != null && board.getPiece(checkSquare).getTeamColor() == teamColor && validMoves(checkSquare).isEmpty()) {
                    endGame();
                    return true;
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
        return this.board;
    }
}
