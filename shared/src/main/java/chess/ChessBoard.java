package chess;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
private ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.squares = new ChessPiece[8][8];
        int col = 1;
        //add white pawns
        while (col <= 8) {
            ChessPosition addPosition = new ChessPosition(2, col);
            ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            this.addPiece(addPosition, whitePawn);
            col++;
        }
        col--;
        //add black pawns
        while (col > 0) {
            ChessPosition addPosition = new ChessPosition(7, col);
            ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            this.addPiece(addPosition, whitePawn);
            col--;
        }
        int whiteRow = 1;
        int blackRow = 8;
        //add pieces
        ChessPiece whiteBishops = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece blackBishops = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece whiteRooks = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece blackRooks = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece whiteKnights = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece blackKnights = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);

        int i = 1;
        int j = 8;
        for (int k : new int[]{i, j}) {
            this.addPiece(new ChessPosition(whiteRow, k), whiteRooks);
        }
        for (int k : new int[]{i, j}) {
            this.addPiece(new ChessPosition(blackRow, k), blackRooks);
        }
        i++;
        j--;
        for (int k : new int[]{i, j}) {
            this.addPiece(new ChessPosition(whiteRow, k), whiteKnights);
        }
        for (int k : new int[]{i, j}) {
            this.addPiece(new ChessPosition(blackRow, k), blackKnights);
        }
        i++;
        j--;
        for (int k : new int[]{i, j}) {
            this.addPiece(new ChessPosition(whiteRow, k), whiteBishops);
        }
        for (int k : new int[]{i, j}) {
            this.addPiece(new ChessPosition(blackRow, k), blackBishops);
        }
        i++;
        j--;
        this.addPiece(new ChessPosition(whiteRow, j), whiteKing);
        this.addPiece(new ChessPosition(blackRow, j), blackKing);
        this.addPiece(new ChessPosition(whiteRow, i), whiteQueen);
        this.addPiece(new ChessPosition(blackRow, i), blackQueen);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
