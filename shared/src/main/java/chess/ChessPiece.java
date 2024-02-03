package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
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
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece currPiece = board.getPiece(myPosition);
        Collection<ChessMove> possibleMoves = new HashSet<>();

        switch (currPiece.getPieceType()) {
            case KING:
                possibleMoves = kingMoves(board, myPosition, currPiece, possibleMoves);
                break;
            case QUEEN:
                possibleMoves = bishopMoves(board, myPosition, currPiece, possibleMoves);
                possibleMoves.addAll(rookMoves(board, myPosition, currPiece, possibleMoves));
                break;
            case BISHOP:
                possibleMoves = bishopMoves(board, myPosition, currPiece, possibleMoves);
                break;
            case KNIGHT:
                possibleMoves = knightMoves(board, myPosition, currPiece, possibleMoves);
                break;
            case ROOK:
                possibleMoves = rookMoves(board, myPosition, currPiece, possibleMoves);
                break;
            case PAWN:
                possibleMoves = pawnMoves(board, myPosition, currPiece, possibleMoves);
                break;
        }
        return possibleMoves;
    }

    void addMove(Collection<ChessMove> possibleMoves, int row, int col, ChessPosition myPosition, PieceType promotionPiece) {
        possibleMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), promotionPiece));
    }

    boolean checkCollision(ChessBoard board, ChessPiece currPiece, int row, int col, Collection<ChessMove> possibleMoves, ChessPosition myPosition) {
        ChessPosition checkSquare = new ChessPosition(row, col);
        if (board.getPiece(checkSquare) != null) {
            checkCapture(board, currPiece, checkSquare, possibleMoves, myPosition);
            return false;
        }
        return true;
    }
    boolean checkPawnCollision(ChessBoard board, int row, int col) {
        ChessPosition checkSquare = new ChessPosition(row, col);
        return board.getPiece(checkSquare) == null;
    }

    void checkCapture(ChessBoard board, ChessPiece currPiece, ChessPosition checkSquare, Collection<ChessMove> possibleMoves, ChessPosition myPosition) {
        if (board.getPiece(checkSquare).getTeamColor() != currPiece.getTeamColor()) {
            addMove(possibleMoves, checkSquare.getRow(), checkSquare.getColumn(), myPosition, null);
        }
    }

    Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, ChessPiece currPiece, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() + 1;
        int col = myPosition.getColumn() + 1;
        while (row <= 8 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            row++;
            col++;
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() + 1;
        while (row > 0 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            row--;
            col++;
        }
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() - 1;
        while (row <= 8 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            row++;
            col--;
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() - 1;
        while (row > 0 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            row--;
            col--;
        }
        return possibleMoves;
    }

    Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, ChessPiece currPiece, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() + 1;
        int col = myPosition.getColumn();
        while (row <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            row++;
        }
        row = myPosition.getRow() - 1;
        while (row > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            row--;
        }
        row = myPosition.getRow();
        col = myPosition.getColumn() + 1;
        while (col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            col++;
        }
        col = myPosition.getColumn() - 1;
        while (col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
            col--;
        }
        return possibleMoves;
    }
    Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, ChessPiece currPiece, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() + 2;
        int col = myPosition.getColumn() + 1;
        if (row <= 8 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() + 2;
        if (row <= 8 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() + 2;
        if (row > 0 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() -2 ;
        col = myPosition.getColumn() + 1;
        if (row > 0 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() - 2;
        if (row > 0 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() - 2;
        col = myPosition.getColumn() - 1;
        if (row > 0 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() - 2;
        if (row <= 8 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row = myPosition.getRow() + 2;
        col = myPosition.getColumn() - 1;
        if (row <= 8 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }

        return possibleMoves;
    }
    Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, ChessPiece currPiece, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() - 1;
        int col = myPosition.getColumn() - 1;
        if (row > 0 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        col++;
        if (row > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        col++;
        if (row > 0 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row++;
        if (col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row++;
        if (row <= 8 && col <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        col--;
        if (row <= 8 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        col--;
        if (row <= 8 && col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        row--;
        if (col > 0 && checkCollision(board, currPiece, row, col, possibleMoves, myPosition)) {
            addMove(possibleMoves, row, col, myPosition, null);
        }
        return possibleMoves;
    }
    Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, ChessPiece currPiece, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        switch (currPiece.getTeamColor()) {
            case WHITE:
                row = myPosition.getRow() + 1;
                if (myPosition.getRow() == 2 && checkPawnCollision(board, row, col) && checkPawnCollision(board, row + 1, col)) {
                    addMove(possibleMoves, row + 1, col, myPosition, null);
                }
                break;
            case BLACK:
                row = myPosition.getRow() - 1;
                if (myPosition.getRow() == 7 && checkPawnCollision(board, row, col) && checkPawnCollision(board, row - 1, col)) {
                    addMove(possibleMoves, row - 1, col, myPosition, null);
                }
                break;
        }
        if (row != 8 && row != 1) {
            if (checkPawnCollision(board, row, col)) {
                addMove(possibleMoves, row, col, myPosition, null);
            }
        } else {
            if (checkPawnCollision(board, row, col)) {
                promotionMoves(possibleMoves, row, col, myPosition);
            }
            if (!checkPawnCollision(board, row, col + 1)) {
                promotionMoves(possibleMoves, row, col + 1, myPosition);
            }
            if (!checkPawnCollision(board, row, col - 1)) {
                promotionMoves(possibleMoves, row, col - 1, myPosition);
            }
            return possibleMoves;
        }
        checkCollision(board, currPiece, row, col + 1, possibleMoves, myPosition);
        checkCollision(board, currPiece, row, col - 1, possibleMoves, myPosition);
        return possibleMoves;
    }

    void promotionMoves(Collection<ChessMove> possibleMoves, int row, int col, ChessPosition myPosition) {
        possibleMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.BISHOP));
        possibleMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.KNIGHT));
        possibleMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.ROOK));
        possibleMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), PieceType.QUEEN));
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
