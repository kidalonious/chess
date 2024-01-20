package chess;

import java.util.ArrayList;
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
        HashSet<ChessMove> possibleMoves = new HashSet<>();
        ChessPiece currPiece = board.getPiece(myPosition);

        switch (currPiece.getPieceType()) {
            case QUEEN:
                possibleMoves = (HashSet<ChessMove>) queenMoves(board, myPosition, possibleMoves);
                break;
            case BISHOP:
                possibleMoves = (HashSet<ChessMove>) bishopMoves(board, myPosition, possibleMoves);
                break;
            case KNIGHT:
                possibleMoves = (HashSet<ChessMove>) knightMoves(board, myPosition, possibleMoves);
                break;
            case ROOK:
                possibleMoves = (HashSet<ChessMove>) rookMoves(board, myPosition, possibleMoves);
                break;
            case PAWN:
                possibleMoves = (HashSet<ChessMove>) pawnMoves(board, myPosition, possibleMoves, currPiece);
                break;
            case KING:
                possibleMoves = (HashSet<ChessMove>) kingMoves(board, myPosition, possibleMoves);
                break;
        }
        return possibleMoves;
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() + 1;
        int col = myPosition.getColumn() + 1;
        while (row <= 8 && col <= 8) {
            //Diagonally up + right, row:addition col:addition
            if (addMove(row, col, possibleMoves, myPosition, board)) {
                addMove(row, col, possibleMoves, myPosition, board);
                row++;
                col++;
            } else break;

        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() - 1;
        while (row > 0 && col > 0) {
            //Diagonally down + left, row:subtraction col:subtraction
            if (addMove(row, col, possibleMoves, myPosition, board)) {
                addMove(row, col, possibleMoves, myPosition, board);
                row--;
                col--;
            } else break;
        }
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() + 1;
        while (row > 0 && col <= 8) {
            //Diagonally down + right, row:subtraction col:addition
            if (addMove(row, col, possibleMoves, myPosition, board)) {
                addMove(row, col, possibleMoves, myPosition, board);
                row--;
                col++;
            } else break;
        }
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() -1;
        while (row <= 8 && col > 0) {
            //Diagonally up + left, row:addition col:subtraction
            if (addMove(row, col, possibleMoves, myPosition, board)) {
                addMove(row, col, possibleMoves, myPosition, board);
                row++;
                col--;
            } else break;
        }
        return possibleMoves;
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        //up, row:addition
        int row = myPosition.getRow() + 1;
        while (row <= 8) {
            if (addMove(row, myPosition.getColumn(), possibleMoves, myPosition, board)) {
                addMove(row, myPosition.getColumn(), possibleMoves, myPosition, board);
                row++;
            } else break;
        }
        //down, row:subtraction
        row = myPosition.getRow() - 1;
        while (row > 0) {
            if (addMove(row, myPosition.getColumn(), possibleMoves, myPosition, board)) {
                addMove(row, myPosition.getColumn(), possibleMoves, myPosition, board);
                row--;
            } else break;
        }
        //left, col:subtraction
        int col = myPosition.getColumn() - 1;
        while (col > 0) {
            if (addMove(myPosition.getRow(), col, possibleMoves, myPosition, board)) {
                addMove(myPosition.getRow(), col, possibleMoves, myPosition, board);
                col--;
            } else break;
        }
        //right, col:addition
        col = myPosition.getColumn() + 1;
        while (col <= 8) {
            if (addMove(myPosition.getRow(), col, possibleMoves, myPosition, board)) {
                addMove(myPosition.getRow(), col, possibleMoves, myPosition, board);
                col++;
            } else break;
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
        //Knight's theoretically have 8 possible total moves on an empty board from the center
        //Finding in a loop will likely not work either because moves aren't dependent on a path like other pieces
        //up 2, right 1
        int row = myPosition.getRow() + 2;
        int col = myPosition.getColumn() + 1;
        if (row <= 8 && col <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: up 1, right 2
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() + 2;
        if (row <= 8 && col <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: up 2, left 1
        row = myPosition.getRow() + 2;
        col = myPosition.getColumn() - 1;
        if (row <= 8 && col > 0 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: up 1, left 2
        row = myPosition.getRow() + 1;
        col = myPosition.getColumn() - 2;
        if (row <= 8 && col > 0 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 2, left 1
        row = myPosition.getRow() - 2;
        col = myPosition.getColumn() - 1;
        if (row > 0 && col > 0 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 1, left 2
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() - 2;
        if (row > 0 && col > 0 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 2, right 1
        row = myPosition.getRow() - 2;
        col = myPosition.getColumn() + 1;
        if (row > 0 && col <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        //reset, check: down 1, right 2
        row = myPosition.getRow() - 1;
        col = myPosition.getColumn() + 2;
        if (row > 0 && col <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        return possibleMoves;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves) {
        int row = myPosition.getRow() - 1;
        int col = myPosition.getColumn();
        if (row > 0 && addMove(row, col, possibleMoves, myPosition, board)) {
                addMove(row, col, possibleMoves, myPosition, board);
        }
        col = myPosition.getColumn() - 1;
        if (row > 0 && col > 0 && addMove(row,col, possibleMoves,myPosition,board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        col = myPosition.getColumn() + 1;
        if (row > 0 && col <= 8 && addMove(row,col, possibleMoves,myPosition,board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        row = myPosition.getRow();
        if (col <= 8 && addMove(row,col,possibleMoves,myPosition,board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        col = myPosition.getColumn() - 1;
        if (col > 0 && addMove(row,col,possibleMoves,myPosition,board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        row = myPosition.getRow() + 1;
        if (col > 0 && row <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        col = myPosition.getColumn();
        if (row <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row,col,possibleMoves,myPosition,board);
        }
        col = myPosition.getColumn()+ 1;
        if (row <= 8 && col <= 8 && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        return possibleMoves;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> possibleMoves, ChessPiece currPiece) {
        //will only change row. No need to check for capture? Check for team color to see if it should be subtraction or addition?
        int col = myPosition.getColumn();
        int row;
        switch (currPiece.getTeamColor()) {
            case WHITE:
                row = myPosition.getRow() + 1;
                if (myPosition.getRow() == 2 && addMove(row, col, possibleMoves, myPosition, board)) {
                    ChessPosition nextSquare = new ChessPosition(row + 1, col);
                    if (board.getPiece(nextSquare) == null && addMove(row, col, possibleMoves, myPosition, board)) {
                        addMove(row + 1, col, possibleMoves, myPosition, board);
                    }
                }
                break;
            case BLACK:
                row = myPosition.getRow() - 1;
                if (myPosition.getRow() == 7 && addMove(row, col, possibleMoves, myPosition, board)) {
                    ChessPosition nextSquare = new ChessPosition(row - 1, col);
                    if (board.getPiece(nextSquare) == null && addMove(row, col, possibleMoves, myPosition, board)) {
                        addMove(row - 1, col, possibleMoves, myPosition, board);
                    }
                }
                break;
            default:
                row = myPosition.getRow();
        }

        ChessPosition nextSquare = new ChessPosition(row, col);
        if (checkPromotion(possibleMoves, myPosition, nextSquare, currPiece)) {
            checkPromotion(possibleMoves, myPosition, nextSquare, currPiece);
        } else if (board.getPiece(nextSquare) == null && addMove(row, col, possibleMoves, myPosition, board)) {
            addMove(row, col, possibleMoves, myPosition, board);
        }
        ChessPosition[] captureSquares = new ChessPosition[2];
        captureSquares[0] = new ChessPosition(row, col -1);
        captureSquares[1] = new ChessPosition(row, col +1);
        for (ChessPosition square : captureSquares) {
            if (board.getPiece(square) != null) {
                if (checkPromotion(possibleMoves, myPosition, square, currPiece)) {
                    checkPromotion(possibleMoves, myPosition, square, currPiece);
                }
                else addMove(square.getRow(), square.getColumn(), possibleMoves, myPosition, board);
            }
        }
        return possibleMoves;
    }

    public boolean checkPromotion(Collection<ChessMove> possibleMoves,ChessPosition myPosition, ChessPosition square, ChessPiece currPiece) {
        if (square.getRow() == 8 && currPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            checkPromotionHelper(possibleMoves, myPosition, square);
            return true;
        } else if (square.getRow() == 1 && currPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            checkPromotionHelper(possibleMoves, myPosition, square);
            return true;
        }
        return false;
    }

    private void checkPromotionHelper(Collection<ChessMove> possibleMoves, ChessPosition myPosition, ChessPosition square) {
        ChessMove promotionKnight = new ChessMove(myPosition, square, PieceType.KNIGHT);
        ChessMove promotionBishop = new ChessMove(myPosition, square, PieceType.BISHOP);
        ChessMove promotionRook = new ChessMove(myPosition, square, PieceType.ROOK);
        ChessMove promotionQueen = new ChessMove(myPosition, square, PieceType.QUEEN);
        possibleMoves.add(promotionKnight);
        possibleMoves.add(promotionBishop);
        possibleMoves.add(promotionQueen);
        possibleMoves.add(promotionRook);
    }

    boolean addMove(int row, int col, Collection<ChessMove> possibleMoves, ChessPosition myPosition, ChessBoard board) {
        ChessPosition checkSquare = new ChessPosition(row, col);
        if (board.getPiece(checkSquare) == null) {
            ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
            //System.out.println(possibleMove.toStringForPersonalTests());
            possibleMoves.add(possibleMove);
            return true;
        }
        else {
            ChessPiece currPiece = new ChessPiece(board.getPiece(myPosition).getTeamColor(), board.getPiece(myPosition).getPieceType());
            ChessPiece otherPiece = new ChessPiece(board.getPiece(checkSquare).getTeamColor(), board.getPiece(checkSquare).getPieceType());
            if (!checkCollision(currPiece, otherPiece)) {
                ChessMove possibleMove = new ChessMove(myPosition, checkSquare, null);
                possibleMoves.add(possibleMove);
            }
            return false;
        }
    }

    boolean checkCollision(ChessPiece currPiece, ChessPiece otherPiece) {
        //This returns true if the two pieces are the same color, false if they are different
        //false means the piece can be captured and the move should be added to possibleMoves
        return currPiece.getTeamColor() == otherPiece.getTeamColor();
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
