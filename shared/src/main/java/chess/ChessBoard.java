package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn()- 1] = piece;
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
        //making pieces
        ChessPiece blackRooks = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece blackKnights = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece blackBishops = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece blackQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece whiteRooks = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece whiteKnights = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece whiteBishops = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece whiteQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);

        int blackRow = 7;
        int whiteRow = 2;
        for (int i = 1; i <= 8; i ++) {
            ChessPiece whitePawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            ChessPiece blackPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            this.addPiece(new ChessPosition(whiteRow, i), whitePawn);
            this.addPiece(new ChessPosition(blackRow, i), blackPawn);
            if (i == 1 || i == 8) {
                this.addPiece(new ChessPosition(whiteRow - 1, i), whiteRooks);
                this.addPiece(new ChessPosition(blackRow + 1, i), blackRooks);
            }
            if (i == 2 || i == 7) {
                this.addPiece(new ChessPosition(whiteRow - 1, i), whiteKnights);
                this.addPiece(new ChessPosition(blackRow + 1, i), blackKnights);
            }
            if (i == 3 || i == 6) {
                this.addPiece(new ChessPosition(whiteRow - 1, i), whiteBishops);
                this.addPiece(new ChessPosition(blackRow + 1, i), blackBishops);
            }
            if (i == 4) {
                this.addPiece(new ChessPosition(whiteRow - 1, i), whiteQueen);
                this.addPiece(new ChessPosition(blackRow + 1, i), blackQueen);
            }
            if (i == 5) {
                this.addPiece(new ChessPosition(whiteRow - 1, i), whiteKing);
                this.addPiece(new ChessPosition(blackRow + 1, i), blackKing);
            }

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard board)) return false;
        return Arrays.deepEquals(squares, board.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
