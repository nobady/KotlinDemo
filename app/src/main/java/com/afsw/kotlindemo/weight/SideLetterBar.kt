package com.afsw.kotlindemo.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.afsw.kotlindemo.R

/**
 * Created by tengfei.lv on 2017/6/26.
 */
class SideLetterBar(context : Context, attributeSet : AttributeSet?) : View(context, attributeSet) {

    companion object {
        val INITIAL = arrayOf("定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                              "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                              "Z")
    }
    constructor(context : Context):this(context,null)

    interface OnLetterChangedListener{
        fun onLetterChanged(letter:String)
    }

    private var mPaint:Paint = Paint()
    var overlay:TextView? = null

    var onLetterChanged:OnLetterChangedListener? = null

    init {
        mPaint.textSize = resources.getDimension(R.dimen.text_size_small)
        mPaint.color = resources.getColor(R.color.colorAccent)
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas : Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.TRANSPARENT)
        /*获取每个文字的高度*/
        val preHeight = height/ INITIAL.size

        for (letter in INITIAL) {
            val x0 = width / 2 - mPaint.measureText(letter) / 2
            val y0 = preHeight * (INITIAL.indexOf(letter) + 1).toFloat()

            canvas?.drawText(letter,x0,y0,mPaint)
        }
    }

    override fun dispatchTouchEvent(event : MotionEvent?) : Boolean {

        when(event?.action){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE-> {
                /*根据点击的位置获取点击的字母，因为是强转为int，所以有可能不是很准确*/
                var index = (event.y * INITIAL.size / height).toInt()
                Log.e("SideLetterBar TAG","$index")
                if (index>=2&&index< INITIAL.size){
                    overlay?.let {
                        overlay?.visibility = VISIBLE
                        overlay?.text = INITIAL[index]

                        onLetterChanged?.let {
                            onLetterChanged?.onLetterChanged(INITIAL[index])
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP->
                overlay?.let {
                    overlay!!.visibility = View.GONE
                }
        }

        return true
    }

}