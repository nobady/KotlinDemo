package com.afsw.kotlindemo.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by tengfei.lv on 2017/6/12.
 */
class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    companion object{
        fun createViewHolder(itemView : View):BaseViewHolder = BaseViewHolder(itemView)

        fun createViewHolder(context: Context,parent : ViewGroup,latoutID:Int):BaseViewHolder{
            val view = LayoutInflater.from(context).inflate(latoutID, parent, false)
            return BaseViewHolder(view)
        }

    }

    fun <T:View> getView(id:Int):T{
        return itemView.findViewById(id) as T
    }
}