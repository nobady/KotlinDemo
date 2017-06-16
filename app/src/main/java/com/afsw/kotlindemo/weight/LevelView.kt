package com.afsw.kotlindemo.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.utils.UIUtil

/**
 * Created by tengfei.lv on 2017/6/15.
 */
class LevelView(context:Context):View(context) {

    private val COlORS_ID = intArrayOf(R.color.green500, R.color.yellow500, R.color.orange500,
                                       R.color.red400, R.color.purple500, R.color.red900)
    private var AQI_LEVELS = intArrayOf(50, 100, 150, 200, 300, 500)

    /*画笔*/
    private var mPaint : Paint? = null
    /*左边圆弧*/
    private var mRectFLeft : RectF? = null
    /*右边圆弧*/
    private var mRectFRight : RectF? = null
    /*空心线*/
    private var mPaintStroke : Int = 0

    private var mPaddingTop : Int = 0
    /*控件宽度*/
    private var mWidth : Int = 0

    private var mTextSize : Int = 0
    private var mTextPaint : TextPaint? = null
    private var mCurrentValue : Int = 0

    fun setAQI_LEVELS(levels:IntArray){
        AQI_LEVELS = levels
    }


    constructor(context : Context, attrs : AttributeSet?):this(context)

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        init()
    }

    private fun init() {
        mWidth = measuredWidth

        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.isAntiAlias = true

        mTextPaint = TextPaint()
        mTextPaint!!.isAntiAlias = true
        mTextSize = UIUtil.dipToPx(context, 10.0f)
        mTextPaint!!.textSize = mTextSize.toFloat()

        mPaintStroke = UIUtil.dipToPx(context, 4.0f)
        mPaddingTop = mPaintStroke

        mRectFLeft = RectF(0f, (mPaddingTop - mPaintStroke / 2).toFloat(), mPaintStroke.toFloat(),
                           (mPaddingTop + mPaintStroke / 2).toFloat())

        mRectFRight = RectF((mWidth - mPaintStroke).toFloat(),
                            (mPaddingTop - mPaintStroke / 2).toFloat(), mWidth.toFloat(),
                            (mPaddingTop + mPaintStroke / 2).toFloat())
    }

    override fun onDraw(canvas : Canvas) {
        super.onDraw(canvas)

        mPaint!!.color = ContextCompat.getColor(context, COlORS_ID[0])

        canvas.drawArc(mRectFLeft!!, 90f, 180f, true, mPaint!!)

        mPaint!!.strokeWidth = mPaintStroke.toFloat()

        var startWidth = (mPaintStroke / 2).toFloat()

        val cx = startWidth + mCurrentValue * 1.5f

        var text = "0"

        canvas.drawText(text, 0f, (mPaddingTop + mPaintStroke + mTextSize).toFloat(), mTextPaint!!)

        for (i in COlORS_ID.indices) {

            mPaint!!.color = ContextCompat.getColor(context, COlORS_ID[i])

            canvas.drawLine(startWidth, mPaddingTop.toFloat(), AQI_LEVELS[i] * 1.5f,
                            mPaddingTop.toFloat(), mPaint!!)

            startWidth = AQI_LEVELS[i] * 1.5f

            text = AQI_LEVELS[i].toString() + ""
            if (i == COlORS_ID.size - 1) {
                text = "+"
            }
            canvas.drawText(text, startWidth - mTextSize / 2,
                            (mPaddingTop + mPaintStroke + mTextSize).toFloat(), mTextPaint!!)
        }

        mRectFRight!!.set(startWidth - mPaintStroke / 2, (mPaddingTop - mPaintStroke / 2).toFloat(),
                          startWidth + mPaintStroke / 2, (mPaddingTop + mPaintStroke / 2).toFloat())

        canvas.drawArc(mRectFRight!!, 270f, 180f, true, mPaint!!)

        mPaint!!.color = ContextCompat.getColor(context, R.color.colorAccent)
        canvas.drawCircle(cx, mPaddingTop.toFloat(), mPaintStroke.toFloat(), mPaint!!)
    }

    fun setCurrentValue(value : Int) {
        mCurrentValue = value
        invalidate()
    }

    fun getSectionColor() : Int {
        var color = ContextCompat.getColor(context, COlORS_ID[0])

        for (i in 0..COlORS_ID.size - 1 - 1) {
            if (AQI_LEVELS[i] < mCurrentValue && AQI_LEVELS[i + 1] > mCurrentValue) {
                color = ContextCompat.getColor(context, COlORS_ID[i])
                break
            }
        }
        return color
    }
}