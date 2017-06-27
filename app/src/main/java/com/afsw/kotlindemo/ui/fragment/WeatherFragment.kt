package com.afsw.kotlindemo.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.adapter.WeatherAdapter
import com.afsw.kotlindemo.base.BaseFragment
import com.afsw.kotlindemo.bean.WeatherBean

/**
 * Created by tengfei.lv on 2017/6/13.
 */
class WeatherFragment: BaseFragment() {

    lateinit var adapter:WeatherAdapter
    lateinit var recyclerView:RecyclerView
    var mWeatherBean:WeatherBean? = null

    companion object{
        fun newInstance():WeatherFragment{
            return WeatherFragment()
        }
    }

    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?,
                              savedInstanceState : Bundle?) : View? {
        val view = inflater!!.inflate(R.layout.common_recyclerview, container, false)
        recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setBackgroundResource(R.color.dark_background)
        adapter = WeatherAdapter()
        mWeatherBean?.let { adapter.setBean(mWeatherBean!!) }
        recyclerView.adapter = adapter
        return view
    }

    fun onMoreInfo(weatherBean : WeatherBean?){
        if (!isAdded||!isVisible){
            mWeatherBean = weatherBean
            return
        }

        weatherBean?.let {
            adapter.setBean(it)
            adapter.notifyDataSetChanged()
        }
    }

}