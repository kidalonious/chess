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
        PieceType currPieceType = currPiece.getPieceType();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        switch (currPieceType) {
            case KING:
            {
            }

            case BISHOP:
                while (row < 8 && col < 8) {
                    //Diagonally up + right, row:addition col:addition
                    row++;
                    col++;
                    ChessPosition checkSquare = new ChessPosition(row, col);
                    if (board.getPiece(checkSquare).getPieceType() == null) {
                        ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
                        possibleMoves.add(possibleMove);
                    }
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
                while (row >= 0 && col >= 0) {
                    //Diagonally down + left, row:subtraction col:subtraction
                    row--;
                    col--;
                    ChessPosition checkSquare = new ChessPosition(row, col);
                    if (board.getPiece(checkSquare).getPieceType() == null) {
                        ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
                        possibleMoves.add(possibleMove);
                    }
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
                while (row < 8 && col >= 0) {
                    //Diagonally down + right, row:addition col:subtraction
                    row++;
                    col--;
                    ChessPosition checkSquare = new ChessPosition(row, col);
                    if (board.getPiece(checkSquare).getPieceType() == null) {
                        ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
                        possibleMoves.add(possibleMove);
                    }
                }
                row = myPosition.getRow();
                col = myPosition.getColumn();
                while (row >= 0 && col < 8) {
                    //Diagonally up + left, row:subtraction col:addition
                    row--;
                    col++;
                    ChessPosition checkSquare = new ChessPosition(row, col);
                    if (board.getPiece(checkSquare).getPieceType() == null) {
                        ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
                        possibleMoves.add(possibleMove);
                    }
                }
                break;
        }
        return possibleMoves;
    }

}
