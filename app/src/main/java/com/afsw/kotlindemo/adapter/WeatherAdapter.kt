package com.afsw.kotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.bean.WeatherBean

/**
 * Created by tengfei.lv on 2017/6/14.
 */
class WeatherAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var weatherBean:WeatherBean? = null

    fun setBean(weather : WeatherBean){
        weatherBean = weather
    }

    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(viewType, parent, false)
        when(viewType){
            R.layout.item_daily_parent-> return DailyHolder(view)
            R.layout.item_aqi-> return AqiHolder(view)
            R.layout.item_life-> return LifeHolder(view)
        }
        return DailyHolder(view)
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder?, position : Int) {

        weatherBean?:let { return }

        if (holder is DailyHolder){
            holder.onBind(weatherBean!!.dailyForecast)
        }else if (holder is AqiHolder){
            holder.updateItem(weatherBean!!.aqi)
        }else if (holder is LifeHolder){
            holder.updataItem(weatherBean!!.lifeIndex)
        }
    }

    override fun getItemCount() : Int  = 3

    override fun getItemViewType(position : Int) : Int {
        when(position){
            0-> return R.layout.item_daily_parent
            1-> return R.layout.item_aqi
            2-> return R.layout.item_life
        }
        return super.getItemViewType(position)
    }
}