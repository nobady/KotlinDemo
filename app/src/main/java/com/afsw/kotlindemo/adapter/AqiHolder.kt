package com.afsw.kotlindemo.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.bean.AqiEntity
import com.afsw.kotlindemo.utils.UIUtil
import com.afsw.kotlindemo.weight.LevelView
import com.silencedut.expandablelayout.ExpandableLayout

/**
 * Created by tengfei.lv on 2017/6/15.
 */
class AqiHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        val AQI_LEVELS = intArrayOf(50, 100, 150, 200, 300, 500)
        val PM2_5_LEVEL = intArrayOf(35, 75, 115, 150, 250, 500)
        val PM10_LEVELS = intArrayOf(50, 150, 250, 350, 420, 600)
    }

    var expandableLayout : ExpandableLayout = itemView.findViewById(R.id.expandable_layout) as ExpandableLayout
    var aqiView : LevelView = itemView.findViewById(R.id.aqi_view) as LevelView
    var aqiValue : TextView = itemView.findViewById(R.id.aqi_value) as TextView
    var aqiQuality : TextView = itemView.findViewById(R.id.aqi_quality) as TextView
    var dateCase : LinearLayout = itemView.findViewById(R.id.date_case) as LinearLayout
    var expandIcon : ImageView = itemView.findViewById(R.id.expand_icon) as ImageView
    var baseInfo : LinearLayout = itemView.findViewById(R.id.base_info) as LinearLayout
    var pm2_5View : LevelView = itemView.findViewById(R.id.pm2_5_view) as LevelView
    var pm2_5Value : TextView = itemView.findViewById(R.id.pm2_5_value) as TextView
    var pm10View : LevelView = itemView.findViewById(R.id.pm10_view) as LevelView
    var pm10Value : TextView = itemView.findViewById(R.id.pm10_value) as TextView
    var aqiAdvice : TextView = itemView.findViewById(R.id.aqi_advice) as TextView
    var rank : TextView = itemView.findViewById(R.id.rank) as TextView
    var titleTv = itemView.findViewById(R.id.guide_title) as TextView

    /*图标展开和关闭的动画*/
    var iconDownAnimator:Animator
    var iconUpAnimator:Animator
    /*标记ExpandableLayout是否是展开状态*/
    var isExpand:Boolean = false

    init {
        aqiView.setAQI_LEVELS(AQI_LEVELS)
        pm2_5View.setAQI_LEVELS(PM2_5_LEVEL)
        pm10View.setAQI_LEVELS(PM10_LEVELS)

        titleTv.text = "空气质量指数(AQI)"

        iconDownAnimator = generateAnimator(expandIcon,0f,180f)
        iconUpAnimator = generateAnimator(expandIcon,180f,0f)

        expandableLayout.setOnExpandListener { isExpand = it }

        expandableLayout.setOnClickListener {
            if (!isExpand){
                iconDownAnimator.start()
            }else{
                iconUpAnimator.start()
            }
        }

    }

    fun updateItem(aqiEntity : AqiEntity) {

        upDataLevel(aqiView, aqiValue, aqiEntity.aqi.toInt())
        upDataLevel(pm2_5View, pm2_5Value, aqiEntity.pm25.toInt())
        upDataLevel(pm10View, pm10Value, aqiEntity.pm10.toInt())

        aqiAdvice.text = aqiEntity.advice
        aqiQuality.text = aqiEntity.quality

        val rankSpannable = SpannableString(aqiEntity.cityRank)
        rankSpannable.setSpan(
            ForegroundColorSpan(UIUtil.getColor(itemView.context, R.color.colorAccent)), 8, 11,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        rankSpannable.setSpan(RelativeSizeSpan(1.3f), 8, 11, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        rank.text = rankSpannable
    }

    private fun upDataLevel(levelView : LevelView, valueTv : TextView, value : Int) {
        levelView.setCurrentValue(value)
        valueTv.text = value.toString()
        valueTv.setTextColor(levelView.getSectionColor())
    }

    private fun generateAnimator(target:View,start:Float,end:Float):Animator{
        val animator = ObjectAnimator.ofFloat(target, "rotation", start, end)
        animator.duration = 300
        return animator
    }

}