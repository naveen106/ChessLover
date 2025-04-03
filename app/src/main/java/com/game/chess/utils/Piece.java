package com.game.chess.utils;

class Piece {
    String symbol;
    boolean isWhite;

    public Piece(String symbol, boolean isWhite) {
        this.symbol = symbol;
        this.isWhite = isWhite;
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Piece[][] board, ChessBoard chessBoard) {
        switch (symbol) {
            case "P": return validatePawnMove(fromX, fromY, toX, toY, board);
            case "R": return validateRookMove(fromX, fromY, toX, toY, board);
            case "N": return validateKnightMove(fromX, fromY, toX, toY);
            case "B": return validateBishopMove(fromX, fromY, toX, toY, board);
            case "Q": return validateQueenMove(fromX, fromY, toX, toY, board);
            case "K": return validateKingMove(fromX, fromY, toX, toY, chessBoard);
            default: return false;
        }
    }

    private boolean validateKingMove(int fromX, int fromY, int toX, int toY, ChessBoard chessBoard) {
        if (Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1) {
            return true;
        }
        return false;
    }

    private boolean validatePawnMove(int fromX, int fromY, int toX, int toY, Piece[][] board) {
        int direction = isWhite ? -1 : 1;
        if (fromX == toX && fromY + direction == toY && board[toY][toX] == null) return true;
        if (fromX == toX && (fromY == 6 || fromY == 1) && fromY + 2 * direction == toY && board[toY][toX] == null) return true;
        if (Math.abs(fromX - toX) == 1 && fromY + direction == toY && board[toY][toX] != null) return true;
        return false;
    }

    private boolean validateRookMove(int fromX, int fromY, int toX, int toY, Piece[][] board) {
        return fromX == toX || fromY == toY;
    }

    private boolean validateKnightMove(int fromX, int fromY, int toX, int toY) {
        int dx = Math.abs(fromX - toX);
        int dy = Math.abs(fromY - toY);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    private boolean validateBishopMove(int fromX, int fromY, int toX, int toY, Piece[][] board) {
        return Math.abs(fromX - toX) == Math.abs(fromY - toY);
    }

    private boolean validateQueenMove(int fromX, int fromY, int toX, int toY, Piece[][] board) {
        return validateRookMove(fromX, fromY, toX, toY, board) || validateBishopMove(fromX, fromY, toX, toY, board);
    }
}