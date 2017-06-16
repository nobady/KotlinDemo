package com.afsw.kotlindemo.adapter

import android.widget.ImageView
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.base.BaseAdapter
import com.afsw.kotlindemo.base.BaseViewHolder
import com.afsw.kotlindemo.bean.HoursForecastEntity
import com.afsw.kotlindemo.utils.Constants

/**
 * Created by tengfei.lv on 2017/6/13.
 */
class HoursForecastAdapter():BaseAdapter<HoursForecastEntity>() {

    init {
        addItemLayout(0, R.layout.item_hour_forecast)
    }

    override fun converts(holder : BaseViewHolder?, data : HoursForecastEntity) {
        with(holder!!){
            getView<TextView>(R.id.hour).text = data.time.substring(11,16)
            getView<ImageView>(R.id.hour_icon).setImageResource(Constants.getIconId(data.weather))
            getView<TextView>(R.id.hour_temp).text = data.temp
        }
    }
}