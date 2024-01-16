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
        if (currPiece == null) {
            return possibleMoves;
        }
        //PieceType currPieceType = currPiece.getPieceType();
        switch (currPiece.getPieceType()) {
            case QUEEN:
                possibleMoves = (ArrayList<ChessMove>) queenMoves(board, myPosition, possibleMoves);
                break;
            case BISHOP:
                possibleMoves = (ArrayList<ChessMove>) bishopMoves(board, myPosition, possibleMoves);
                break;
            case KNIGHT:
                possibleMoves = (ArrayList<ChessMove>) knightMoves(board, myPosition, possibleMoves);
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

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
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

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
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

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        Collection<ChessMove> diagonalMoves = bishopMoves(board, myPosition, possibleMoves);
        Collection<ChessMove> lineMoves = rookMoves(board, myPosition, possibleMoves);
        possibleMoves.addAll(diagonalMoves);
        possibleMoves.addAll(lineMoves);
        return possibleMoves;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        //Knight's theoretically have 8 possible moves on an empty board from the center
        //Finding in a loop will likely not work either because moves aren't dependent on a path like other pieces
        //up 2, right 1
        int row = myPosition.getRow() + 2;
        int col = myPosition.getColumn() + 1;
        if (row <= 8 && col <= 8) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: up 1, right 2
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() + 2;
        if (row <= 8 && col <= 8) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: up 2, left 1
        row = myPosition.getRow() + 2;
        col = myPosition.getColumn() - 1;
        if (row <= 8 && col > 0) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: up 1, left 2
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() - 2;
        if (row <= 8 && col > 0) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 2, left 1
        row = myPosition.getRow() - 2;
        col = myPosition.getColumn() - 1;
        if (row > 0 && col > 0) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 1, left 2
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() - 2;
        if (row > 8 && col > 8) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 2, right 1
        row = myPosition.getRow() - 2;
        col = myPosition.getColumn() + 1;
        if (row > 0 && col <= 8) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 1, right 2
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() + 2;
        if (row <= 8 && col <= 8) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        return possibleMoves;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        //will only change row. No need to check for capture? Check for team color to see if it should be subtraction or addition?
        if (myPosition.getRow() == 2) {
            addMove(myPosition.getRow() + 2, myPosition.getColumn(), possibleMoves, myPosition, board);
        }
        addMove(myPosition.getRow() + 1, myPosition.getColumn(), possibleMoves, myPosition, board);
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
