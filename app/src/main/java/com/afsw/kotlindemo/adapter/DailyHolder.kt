package com.afsw.kotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.bean.DailyForecastEntity
import com.afsw.kotlindemo.utils.Constants

/**
 * Created by tengfei.lv on 2017/6/15.
 */
class DailyHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

    val rootView = itemView.findViewById(R.id.ll_daily) as LinearLayout
    val inflater:LayoutInflater = LayoutInflater.from(rootView.context)
    val titleTv = itemView.findViewById(R.id.guide_title) as TextView

    fun onBind(datas:MutableList<DailyForecastEntity>){
        rootView.removeAllViews()

        titleTv.text = "未来天气"

        for ((_, temp_range, weather, week) in datas) {
            val view = inflater.inflate(R.layout.item_daily_forecast, null)
            val weekTv = view.findViewById(R.id.date_week) as TextView
            val statusTv = view.findViewById(R.id.weather_status_daily) as TextView
            val iconIv = view.findViewById(R.id.weather_icon_daily) as ImageView
            val tempTv = view.findViewById(R.id.temp_daily) as TextView

            weekTv.text = week
            statusTv.text = weather
            tempTv.text = temp_range
            iconIv.setImageResource(Constants.getIconId(weather))

            rootView.addView(view)
        }
    }

}