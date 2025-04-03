package com.game.chess.activities;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.setContent;
import androidx.compose.foundation.background;
import androidx.compose.foundation.clickable;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.res.painterResource;
import androidx.compose.ui.unit.dp;
import androidx.compose.ui.unit.sp;
import com.game.chess.activities.ChessEngine
import com.game.chess.utils.ChessBoard

class ChessActivity : ComponentActivity() {
    private val chessBoard = ChessBoard()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessGameScreen(chessBoard)
        }
    }
}

@Composable
fun ChessGameScreen(chessBoard: ChessBoard) {
    var selectedSquare by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val boardState = remember { mutableStateListOf(*chessBoard.getBoardArray()) }
    var validMoves by remember { mutableStateOf(setOf<Pair<Int, Int>>()) }
        val chessEngine = ChessEngine();

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ChessBoard(boardState, selectedSquare, validMoves) { row, col ->
            if (selectedSquare == null) {
                selectedSquare = row to col
                validMoves = chessEngine.getValidMoves(row, col) // Fetch valid moves
            } else {
                val from = selectedSquare!!
                val to = row to col
                if (chessBoard.isValidMove("${'a' + from.second}${8 - from.first}", "${'a' + to.second}${8 - to.first}", true)) {
                    chessBoard.movePiece("${'a' + from.second}${8 - from.first}", "${'a' + to.second}${8 - to.first}")
                    boardState.clear()
                    boardState.addAll(chessBoard.getBoardArray())
                }
                selectedSquare = null
                validMoves = emptySet()
            }
        }
    }
}

@Composable
fun ChessBoard(boardState: List<Array<String>>, selectedSquare: Pair<Int, Int>?, validMoves: Set<Pair<Int, Int>>, onSquareClick: (Int, Int) -> Unit) {
    val boardSize = 8
    Column {
        for (row in 0 until boardSize) {
            Row {
                for (col in 0 until boardSize) {
                    ChessSquare(row, col, boardState[row][col], selectedSquare, validMoves, onSquareClick)
                }
            }
        }
    }
}

@Composable
fun ChessSquare(row: Int, col: Int, piece: String, selectedSquare: Pair<Int, Int>?, validMoves: Set<Pair<Int, Int>>, onSquareClick: (Int, Int) -> Unit) {
    val color = when {
        selectedSquare == row to col -> Color.Yellow.copy(alpha = 0.4f)
        validMoves.contains(row to col) -> Color.Green.copy(alpha = 0.4f)
        (row + col) % 2 == 0 -> Color.White
        else -> Color.Black
    }

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(color, shape = RoundedCornerShape(4.dp))
            .clickable { onSquareClick(row, col) },
        contentAlignment = Alignment.Center
    ) {
        if (piece.isNotEmpty()) {
//            val pieceIcon: Painter = painterResource(id = getPieceDrawable(piece))
//            Icon(painter = pieceIcon, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Unspecified)

            Text(text = getPieceSymbol(piece), fontSize = 30.sp, color = Color.Black)
        }
    }
}



fun getPieceSymbol(piece: String): String {
    return when (piece) {
        "P" -> "♙"  // White Pawn
        "p" -> "♟"  // Black Pawn
        "R" -> "♖"
        "r" -> "♜"
        "N" -> "♘"
        "n" -> "♞"
        "B" -> "♗"
        "b" -> "♝"
        "Q" -> "♕"
        "q" -> "♛"
        "K" -> "♔"
        "k" -> "♚"
        else -> ""
    }
}
