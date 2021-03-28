package com.example.movieapp.ui.details.person

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import com.example.movieapp.R


class BackgroundRoundedView(
    context: Context,
    attributeSet: AttributeSet? = null
):View(context, attributeSet) {

    private val cornerRadius:Int
    private val roundedBottom:Boolean
    private val backgroundColor:Int

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.BackgroundRoundedView,
            0, 0).apply {

            try {
                roundedBottom = getBoolean(R.styleable.BackgroundRoundedView_roundedBottom, true)
                cornerRadius = getDimensionPixelSize(R.styleable.BackgroundRoundedView_cornerRadius, 20)
                backgroundColor = getColor(R.styleable.BackgroundRoundedView_backgroundColor, Color.RED)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val point = Point()
        val windowManager = context.getSystemService(WindowManager::class.java)
        windowManager.defaultDisplay.getSize(point)

        val desireWidth = point.x
        val desireHeight = point.y
        setMeasuredDimension(
            resolveSize(desireWidth, widthMeasureSpec), resolveSize(
                desireHeight,
                heightMeasureSpec
            )
        )
    }

    override fun onDraw(canvas: Canvas?) {
        val top = 0f
        val bottom = if (roundedBottom) height.toFloat() - cornerRadius else height.toFloat()
        val left = 0f
        val leftB = if (roundedBottom) left + cornerRadius else left
        val right = width.toFloat()

        paint.color = backgroundColor
        path.apply {
            moveTo(left + cornerRadius, top + cornerRadius)
            lineTo(right - cornerRadius, top + cornerRadius)
            arcTo(right - cornerRadius *2, top - cornerRadius, right, top + cornerRadius, 90f, -90f, false)
            lineTo(right, bottom)
            if (roundedBottom){
                arcTo(right - cornerRadius *2, bottom - cornerRadius *2, right, bottom, 0f, 90f, false)
            }
            lineTo(leftB, bottom)
            if (roundedBottom){
                arcTo(left, bottom, left + cornerRadius *2, bottom + cornerRadius *2, 270f, -90f, false)
            }
            lineTo(left, top + cornerRadius * 2)
            arcTo(left, top + cornerRadius, left + cornerRadius * 2, top + cornerRadius * 3, 180f, 90f, false)
            close()
        }
        canvas?.drawPath(path, paint)
    }

}