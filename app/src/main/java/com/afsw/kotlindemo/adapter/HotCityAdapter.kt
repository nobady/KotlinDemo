package com.afsw.kotlindemo.adapter

import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.base.BaseAdapter
import com.afsw.kotlindemo.base.BaseViewHolder

/**
 * Created by tengfei.lv on 2017/6/27.
 */
class HotCityAdapter(onItemClickListener: SearchCityAdapter.OnItemClickListener):BaseAdapter<String>() {

    var mOnItemClickListener: SearchCityAdapter.OnItemClickListener

    init {
        addItemLayout(0, R.layout.item_hot_city)
        mOnItemClickListener = onItemClickListener
    }

    override fun converts(holder : BaseViewHolder?, data : String) {

        holder?.getView<TextView>(R.id.tv_hot_city_name)?.text = data

        holder?.getView<TextView>(R.id.tv_hot_city_name)?.setOnClickListener {
            mOnItemClickListener.onClick(holder.layoutPosition,data)
        }
    }
}