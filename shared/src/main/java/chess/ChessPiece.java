package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {

        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece currPiece = board.getPiece(myPosition);
        //PieceType currPieceType = currPiece.getPieceType();

        switch (currPiece.getPieceType()) {
            case QUEEN:
                possibleMoves = (ArrayList<ChessMove>) queenMoves(board, myPosition, possibleMoves);
                break;
            case BISHOP:
                possibleMoves = (ArrayList<ChessMove>) bishopMoves(board, myPosition, possibleMoves);
                break;
            case KNIGHT:
                break;
            case ROOK:
                possibleMoves = (ArrayList<ChessMove>) rookMoves(board, myPosition, possibleMoves);
                break;
            case PAWN:
                break;
            case KING:
                break;
            default:
                break;
        }
        return possibleMoves;
    }

    Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() + 1;
        int col = myPosition.getColumn() + 1;
        while (row <= 8 && col <= 8) {
            //Diagonally up + right, row:addition col:addition

            addMove(row, col, possibleMoves, myPosition, board);
            row++;
            col++;
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() - 1;
        while (row > 0 && col > 0) {
            //Diagonally down + left, row:subtraction col:subtraction
            addMove(row, col, possibleMoves, myPosition, board);
            row--;
            col--;
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() + 1;
        while (row <= 8 && col > 0) {
            //Diagonally down + right, row:addition col:subtraction
            addMove(row, col, possibleMoves, myPosition, board);
            row--;
            col++;
        }
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() + 1;
        while (row > 0 && col <= 8) {
            //Diagonally up + left, row:addition col:subtraction
            addMove(row, col, possibleMoves, myPosition, board);
            row++;
            col--;
        }
        return possibleMoves;
    }

    Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        //up, row:addition
        int row = myPosition.getRow() + 1;
        while (row <= 8) {
            addMove(row, myPosition.getColumn(), possibleMoves, myPosition, board);
            row++;
        }
        //down, row:subtraction
        row = myPosition.getRow() - 1;
        while (row > 0) {
            addMove(row, myPosition.getColumn(), possibleMoves, myPosition, board);
            row--;
        }
        //left, col:subtraction
        int col = myPosition.getColumn() - 1;
        while (col > 0) {
            addMove(myPosition.getRow(), col, possibleMoves, myPosition, board);
            col--;
        }
        //right, col:addition
        col = myPosition.getColumn() - 1;
        while (col <= 8) {
            addMove(myPosition.getRow(), col, possibleMoves, myPosition, board);
            col++;
        }
        return possibleMoves;
    }

    Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        Collection<ChessMove> diagonalMoves = bishopMoves(board, myPosition, possibleMoves);
        Collection<ChessMove> lineMoves = rookMoves(board, myPosition, possibleMoves);
        possibleMoves.addAll(diagonalMoves);
        possibleMoves.addAll(lineMoves);
        return possibleMoves;
    }
    void addMove(int row, int col, Collection<ChessMove> possibleMoves, ChessPosition myPosition, ChessBoard board) {
        ChessPosition checkSquare = new ChessPosition(row, col);
        if (board.getPiece(checkSquare).getPieceType() == null) {
            ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
            possibleMoves.add(possibleMove);
        }
    }

}
