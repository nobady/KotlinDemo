package com.afsw.kotlindemo.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.afsw.kotlindemo.R
import java.util.*

/**
 * Created by tengfei.lv on 2017/6/12.
 */
class MainPageAdapter(private val context : Context,
                      manager : FragmentManager) : FragmentPagerAdapter(manager) {
    private val mFragmentList = ArrayList<Fragment>()
    private val tabIconIds = intArrayOf(R.drawable.tab_city_drawable,
                                        R.drawable.tab_weather_drawable,
                                        R.drawable.tab_user_drawable)

    override fun getItem(position : Int) : Fragment {
        return mFragmentList[position]
    }

    override fun getCount() : Int {
        return mFragmentList.size
    }

    fun addFrag(fragment : Fragment) {
        mFragmentList.add(fragment)
    }

    fun getTabView(position : Int, parent : ViewGroup) : View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.tab_view, parent, false)
        val img = view.findViewById(R.id.tab_icon) as ImageView
        img.setImageResource(tabIconIds[position])
        return view
    }
}