package com.afsw.kotlindemo.adapter

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.base.BaseAdapter
import com.afsw.kotlindemo.base.BaseViewHolder
import com.afsw.kotlindemo.bean.SelectCityBean
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.PreferenceUtil
import com.afsw.kotlindemo.utils.UIUtil

/**
 * Created by tengfei.lv on 2017/6/12.
 */
class CityAdapter() : BaseAdapter<SelectCityBean>() {
    /*标记是否需要删除*/
    var isDelete : Boolean = false

    init {
        addItemLayout(1, R.layout.item_followed_city)
        addItemLayout(2, R.layout.item_add_city)
    }

    override fun converts(holder : BaseViewHolder?, data : SelectCityBean) {

        when (getItemViewType(holder!!.layoutPosition)) {
            1 -> {
                with(holder) {
                    getView<ImageView>(R.id.image).setBackgroundResource(data.backgroundId)
                    getView<TextView>(R.id.city_temp).text = data.temp
                    getView<TextView>(R.id.city_name).text = data.cityName
                    getView<TextView>(R.id.city_status).text = data.weatherStatus
                    val drawable = UIUtil.getDrawable(itemView.context,
                                                      Constants.getIconId(data.weatherStatus))
                    drawable.bounds = Rect(0, 0, UIUtil.dipToPx(itemView.context,
                                                                R.dimen.common_icon_size_small),
                                           UIUtil.dipToPx(itemView.context,
                                                          R.dimen.common_icon_size_small))
                    getView<TextView>(R.id.city_status).setCompoundDrawables(drawable, null, null,
                                                                             null)


                    /*显示删除按钮和背景*/
                    getView<ImageView>(
                        R.id.delete).visibility = if (isDelete) View.VISIBLE else View.GONE
                    getView<View>(
                        R.id.hover).visibility = if (isDelete) View.VISIBLE else View.GONE
                    /*显示当前展示天气的城市的标记*/
                    getView<ImageView>(
                        R.id.checked).visibility = if (data.cityName == PreferenceUtil.get().getString(
                        Constants.CURR_CITY)) View.VISIBLE else View.GONE

                    /*给itemview设置长按事件*/
                    getView<RelativeLayout>(R.id.content).setOnLongClickListener {
                        isDelete = !isDelete
                        notifyItemChanged(layoutPosition)
                        true
                    }

                    getView<ImageView>(R.id.delete).setOnClickListener {
                        /*如果删除的是当前正在显示的城市，那么就切换到第一个城市*/
                        /*如果只有一个城市了，就不能删除*/
                        datas!!.removeAt(layoutPosition)
                    }

                    getView<RelativeLayout>(R.id.content).setOnClickListener {
                        if (!isDelete) {
                            /*如果没有处于删除状态，那么就显示当前卡片对应城市的天气*/
                        } else {
                            /*如果处于删除状态，那么就回复到正常状态*/
                            isDelete = false
                            notifyDataSetChanged()
                        }
                    }

                }
            }

            2 -> {
                holder.getView<ImageView>(R.id.image).setOnClickListener {
                    /*跳转到城市列表页面*/
                }
            }
        }
    }

    override fun getItemViewType(position : Int) : Int {
        if (datas != null && position == datas!!.size - 1) return 2
        else return 1
    }

}