package chess;

import java.util.*;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition endPosition;
    private final ChessPosition startPosition;
    private final ChessPiece.PieceType promotionPiece;


    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    public String toStringForPersonalTests() {
        int startRank = startPosition.getRow();
        int startFile = startPosition.getColumn();

        int endRank = endPosition.getRow();
        int endFile = endPosition.getColumn();

        char[] files = {'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        StringBuilder returnString = new StringBuilder();
        returnString.append(files[startFile]);
        returnString.append(startRank);
        returnString.append(files[endFile]);
        returnString.append(endRank);

        return returnString.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;
        return Objects.equals(getEndPosition(), chessMove.getEndPosition()) && Objects.equals(getStartPosition(), chessMove.getStartPosition()) && getPromotionPiece() == chessMove.getPromotionPiece();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEndPosition(), getStartPosition(), getPromotionPiece());
    }
}
