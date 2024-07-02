package com.example.tictactoe

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var tvStatus: TextView
    private lateinit var tvXWins: TextView
    private lateinit var tvOWins: TextView
    private lateinit var btnReset: Button
    private var player = true
    private var boardStatus = Array(3) { IntArray(3) }
    private var xWins = 0
    private var oWins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)
        tvXWins = findViewById(R.id.tvXWins)
        tvOWins = findViewById(R.id.tvOWins)
        btnReset = findViewById(R.id.btnReset)
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        buttons = Array(3) { r ->
            Array(3) { c ->
                val button = gridLayout.getChildAt(r * 3 + c) as Button
                button.setOnClickListener { onClick(button, r, c) }
                button
            }
        }

        initializeBoardStatus()

        btnReset.setOnClickListener {
            initializeBoardStatus()
        }
    }

    private fun initializeBoardStatus() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardStatus[i][j] = -1
                buttons[i][j].isEnabled = true
                buttons[i][j].text = ""
            }
        }
        player = true
        tvStatus.text = "Player X's Turn"
    }

    private fun onClick(button: Button, row: Int, col: Int) {
        if (player) {
            button.text = "X"
            boardStatus[row][col] = 1
        } else {
            button.text = "O"
            boardStatus[row][col] = 0
        }
        button.isEnabled = false
        player = !player
        tvStatus.text = if (player) "Player X's Turn" else "Player O's Turn"
        checkWinner()
    }

    private fun checkWinner() {
        // Check rows
        for (i in 0..2) {
            if (boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][1] == boardStatus[i][2]) {
                if (boardStatus[i][0] != -1) {
                    updateStatus(boardStatus[i][0])
                    return
                }
            }
        }

        // Check columns
        for (i in 0..2) {
            if (boardStatus[0][i] == boardStatus[1][i] && boardStatus[1][i] == boardStatus[2][i]) {
                if (boardStatus[0][i] != -1) {
                    updateStatus(boardStatus[0][i])
                    return
                }
            }
        }

        // Check diagonals
        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2]) {
            if (boardStatus[0][0] != -1) {
                updateStatus(boardStatus[0][0])
                return
            }
        }

        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][0]) {
            if (boardStatus[0][2] != -1) {
                updateStatus(boardStatus[0][2])
                return
            }
        }

        // Check for draw
        var empty = false
        for (i in 0..2) {
            for (j in 0..2) {
                if (boardStatus[i][j] == -1) {
                    empty = true
                    break
                }
            }
        }
        if (!empty) {
            tvStatus.text = "Game Draw"
            resetBoardAfterDelay()
        }
    }

    private fun updateStatus(winner: Int) {
        tvStatus.text = if (winner == 1) {
            xWins++
            tvXWins.text = "Player X Wins: $xWins"
            "Player X Wins"
        } else {
            oWins++
            tvOWins.text = "Player O Wins: $oWins"
            "Player O Wins"
        }
        disableButtons()
        resetBoardAfterDelay()
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetBoardAfterDelay() {
        Handler().postDelayed({
            initializeBoardStatus()
        }, 2000) // 2-second delay before resetting the board
    }
}
