package com.afsw.kotlindemo.adapter

import android.view.View
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.base.BaseAdapter
import com.afsw.kotlindemo.base.BaseViewHolder
import com.afsw.kotlindemo.db.CityDbEntity

/**
 * Created by tengfei.lv on 2017/6/27.
 */
class SearchCityAdapter(onItemClickListener:OnItemClickListener):BaseAdapter<CityDbEntity>() {

    var mOnItemClickListener:OnItemClickListener

    init {
        addItemLayout(0, R.layout.item_city)
        mOnItemClickListener = onItemClickListener
    }

    override fun converts(holder : BaseViewHolder?, data : CityDbEntity) {
        with(holder!!){

            data.firstPinYin?.let {
                holder.getView<TextView>(R.id.tv_item_city_letter).visibility = View.VISIBLE
                holder.getView<TextView>(R.id.tv_item_city_letter).text = it
            }
            data.firstPinYin?:let {
                holder.getView<TextView>(R.id.tv_item_city_letter).visibility = View.GONE
            }

            holder.getView<TextView>(R.id.tv_item_city_name).text = data.getCityName()
            holder.getView<TextView>(R.id.tv_item_city_name).setOnClickListener {
                mOnItemClickListener.onClick(layoutPosition,data.getCityId()!!)
            }
        }
    }

    interface OnItemClickListener{
        fun onClick(position:Int,cityId:String)
    }
}