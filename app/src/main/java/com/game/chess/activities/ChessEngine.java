package com.game.chess.activities;

import com.game.chess.utils.ChessBoard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotlin.Pair;


public class ChessEngine {
    private ChessBoard board;
    private boolean whiteTurn;

    public ChessEngine() {
        this.board = new ChessBoard();
        this.whiteTurn = true;
    }

    public boolean makeMove(String from, String to) {
        if (!board.isValidMove(from, to, whiteTurn)) {
            System.out.println("Invalid move!");
            return false;
        }
        board.movePiece(from, to);
        whiteTurn = !whiteTurn;
        return true;
    }

//    public void printBoard() {
//        board.display();
//    }

    public String[][] getBoardState() {
        return board.getBoardArray();
    }

    public Set<Pair<Integer, Integer>> getValidMoves(int row, int col) {
        Set<Pair<Integer, Integer>> moves = new HashSet<>();
        String position = (char) ('a' + col) + String.valueOf(8 - row);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String target = (char) ('a' + j) + String.valueOf(8 - i);
                if (board.isValidMove(position, target, whiteTurn)) {
                    moves.add(new Pair<>(i, j));
                }
            }
        }
        return moves;
    }



//    public static void main(String[] args) {
//        ChessEngine game = new ChessEngine();
//        game.printBoard();
//    }
}
