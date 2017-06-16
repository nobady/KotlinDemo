package com.afsw.kotlindemo.adapter

import android.widget.ImageView
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.base.BaseAdapter
import com.afsw.kotlindemo.base.BaseViewHolder
import com.afsw.kotlindemo.bean.LifeIndexEntity

/**
 * Created by TENGFEI on 2017/6/15.
 */
class LifeAdapter(itemClickListener : OnItemClickListener) : BaseAdapter<LifeIndexEntity>() {

    companion object {
        val ICON_ID = intArrayOf(R.mipmap.sun_cure, R.mipmap.clothing, R.mipmap.health,
                                 R.mipmap.wash_car, R.mipmap.protection, R.mipmap.sport_mode,
                                 R.mipmap.shopping, R.mipmap.night_light)
    }

    val mItemClickListener:OnItemClickListener = itemClickListener

    init {
        addItemLayout(0, R.layout.item_life_index)
    }

    override fun converts(holder : BaseViewHolder?, data : LifeIndexEntity) {
        with(holder!!) {
            getView<ImageView>(R.id.life_index_icon).setImageResource(ICON_ID[layoutPosition])
            getView<TextView>(R.id.life_type).text = data.name
            getView<TextView>(R.id.life_level).text = data.level

            itemView.setOnClickListener { mItemClickListener.onClick(data.content,data.name) }
        }
    }

    interface OnItemClickListener{
        fun onClick(content:String,name:String)
    }
}