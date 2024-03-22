package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawnBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";
    private static String currentChar = EMPTY;
    private static String currentColor = null;


    public static void main(String[] args) {
        PrintStream output = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        output.print(ERASE_SCREEN);

        chess.ChessBoard board = new ChessBoard();
        board.resetBoard();
        drawSquares(output, board);

        output.print(SET_BG_COLOR_BLACK);
        output.print(SET_TEXT_COLOR_WHITE);

        drawBoardReversed(output, board);
        
        output.print(SET_BG_COLOR_DARK_GREY);
        output.print(RESET_TEXT_COLOR);
    }
    private static void headers(PrintStream output) {
        setBlack(output);

        String[] headers = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
        for (int boardCol = 0; boardCol <= BOARD_SIZE_IN_SQUARES; ++boardCol) {
            output.print(SET_BG_COLOR_BLACK);
            output.print(SET_TEXT_COLOR_GREEN);

            output.print(headers[boardCol]);
        }
        output.println();
    }


    private static void drawSquares(PrintStream output, ChessBoard board)
    {

        headers(output);

        String[] rowHeaders = {" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};

        //for each row
        for (int rowInBoard = 1; rowInBoard <= BOARD_SIZE_IN_SQUARES; ++rowInBoard)
        {
            //print the column with the row names
            output.print(SET_BG_COLOR_BLACK);
            output.print(SET_TEXT_COLOR_BLUE);
            output.print(rowHeaders[rowInBoard]);

            for (int squareInRow = 1; squareInRow <= BOARD_SIZE_IN_SQUARES; squareInRow += 1) {
                //set the char to print here
                setCurrentChar(board, rowInBoard, squareInRow);
                if((squareInRow + rowInBoard) % 2 == 0) {
                    output.print(SET_BG_COLOR_WHITE);
                    output.print(SET_TEXT_COLOR_BLACK);
                    output.print(currentColor);
                    output.print(currentChar);
                }
                else
                {
                    output.print(SET_BG_COLOR_BLACK);
                    output.print(currentColor);
                    output.print(currentChar);
                }
            }

            //print the column with the row names
            output.print(SET_BG_COLOR_BLACK);
            output.print(SET_TEXT_COLOR_BLUE);
            output.print(rowHeaders[rowInBoard]);
            setBlack(output);
            output.println();

        }

        headers(output);
        output.println();
    }

    private static void drawBoardReversed(PrintStream output, ChessBoard board)
    {

        headers(output);

        String[] rowHeaders = {"   ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 "};

        //for each row
        for (int rowInBoard = 8; rowInBoard >= 1;  --rowInBoard)
        {
            //print the column with the row names
            output.print(SET_BG_COLOR_BLACK);
            output.print(SET_TEXT_COLOR_BLUE);
            output.print(rowHeaders[rowInBoard]);

            for (int squareInRow = 1; squareInRow <= BOARD_SIZE_IN_SQUARES; squareInRow += 1) {
                //set the char to print here
                setCurrentChar(board, rowInBoard, squareInRow);
                if((squareInRow + rowInBoard) % 2 == 0) {
                    output.print(SET_BG_COLOR_WHITE);
                    output.print(SET_TEXT_COLOR_BLACK);
                    output.print(currentColor);
                    output.print(currentChar);
                }
                else
                {
                    output.print(SET_BG_COLOR_BLACK);
                    output.print(currentColor);
                    output.print(currentChar);
                }
            }

            //print the column with the row names
            output.print(SET_BG_COLOR_BLACK);
            output.print(SET_TEXT_COLOR_BLUE);
            output.print(rowHeaders[rowInBoard]);
            setBlack(output);
            output.println();

        }

        headers(output);
        output.println();
    }

    private static void setCurrentChar(ChessBoard board, int rowInBoard, int squareInRow) {
        ChessPiece piece = board.getPiece(new ChessPosition(rowInBoard, squareInRow));

        if (piece == null) {
            currentChar = EMPTY;
            currentColor = SET_TEXT_COLOR_BLACK;
            return;
        }

        switch (piece.getPieceType()) {
            case KING:
                currentChar = " K ";
                break;
            case QUEEN:
                currentChar = " Q ";
                break;
            case PAWN:
                currentChar = " P ";
                break;
            case ROOK:
                currentChar = " R ";
                break;
            case KNIGHT:
                currentChar = " N ";
                break;
            case BISHOP:
                currentChar = " B ";
                break;
            default:
                currentChar = "Unknown";
                break;
        }

        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            currentColor = SET_TEXT_COLOR_RED;
        } else {
            currentColor = SET_TEXT_COLOR_BLUE;
        }
    }


    private static void setBlack(PrintStream output) {
        output.print(SET_BG_COLOR_BLACK);
        output.print(SET_TEXT_COLOR_BLACK);
    }
}
