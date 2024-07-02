package com.example.tictactoe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class LineView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = android.graphics.Color.RED
        strokeWidth = 10f
    }
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var endX: Float = 0f
    private var endY: Float = 0f
    private var drawLine = false

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (drawLine) {
            canvas.drawLine(startX, startY, endX, endY, paint)
        }
    }

    fun drawWinningLine(startX: Float, startY: Float, endX: Float, endY: Float) {
        this.startX = startX
        this.startY = startY
        this.endX = endX
        this.endY = endY
        this.drawLine = true
        invalidate()
    }

    fun clearLine() {
        this.drawLine = false
        invalidate()
    }
}
