package com.game.chess.utils;

public class ChessBoard {
    private Piece[][] board;
    private boolean[] canCastle;

    public ChessBoard() {
        board = new Piece[8][8];
        canCastle = new boolean[]{true, true, true, true}; // King-side & Queen-side for both players
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Piece("P", false);
            board[6][i] = new Piece("P", true);
        }
        board[0][0] = board[0][7] = new Piece("R", false);
        board[7][0] = board[7][7] = new Piece("R", true);

        board[0][1] = board[0][6] = new Piece("N", false);
        board[7][1] = board[7][6] = new Piece("N", true);

        board[0][2] = board[0][5] = new Piece("B", false);
        board[7][2] = board[7][5] = new Piece("B", true);

        board[0][3] = new Piece("Q", false);
        board[0][4] = new Piece("K", false);
        board[7][3] = new Piece("Q", true);
        board[7][4] = new Piece("K", true);
    }

    public String[][] getBoardArray() {
        String[][] boardState = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    boardState[i][j] = "";
                } else {
                    boardState[i][j] = board[i][j].symbol;
                }
            }
        }
        return boardState;
    }


    public boolean isValidMove(String from, String to, boolean whiteTurn) {
        int fromX = from.charAt(0) - 'a';
        int fromY = 8 - (from.charAt(1) - '0');
        int toX = to.charAt(0) - 'a';
        int toY = 8 - (to.charAt(1) - '0');

        Piece piece = board[fromY][fromX];
        if (piece == null || piece.isWhite != whiteTurn) {
            return false;
        }

        return piece.isValidMove(fromX, fromY, toX, toY, board, this);
    }

    public void movePiece(String from, String to) {
        int fromX = from.charAt(0) - 'a';
        int fromY = 8 - (from.charAt(1) - '0');
        int toX = to.charAt(0) - 'a';
        int toY = 8 - (to.charAt(1) - '0');

        board[toY][toX] = board[fromY][fromX];
        board[fromY][fromX] = null;
    }

    public void display() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".");
                } else {
                    System.out.print(board[i][j].symbol);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}


