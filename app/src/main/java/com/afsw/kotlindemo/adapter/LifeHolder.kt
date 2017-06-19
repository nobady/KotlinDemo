package com.afsw.kotlindemo.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.bean.LifeIndexEntity

/**
 * Created by TENGFEI on 2017/6/15.
 */
class LifeHolder(itemView:View):RecyclerView.ViewHolder(itemView),LifeAdapter.OnItemClickListener {

    override fun onClick(content : String, name : String) {
        adviceTv.text = content
        titleTv.text = name
        recyclerView.visibility = View.INVISIBLE
        adviceTv.visibility = View.VISIBLE
    }

    val recyclerView = itemView.findViewById(R.id.lifeRecyclerView) as RecyclerView
    val adviceTv = itemView.findViewById(R.id.life_advice) as TextView
    val titleTv = itemView.findViewById(R.id.guide_title) as TextView
    var adapter = LifeAdapter(this)

    init {
        titleTv.text = "生活指数"
        recyclerView.layoutManager = GridLayoutManager(itemView.context,4)
        recyclerView.adapter = adapter

        adviceTv.setOnClickListener {
            titleTv.text = "生活指数"
            recyclerView.visibility = View.VISIBLE
            adviceTv.visibility = View.INVISIBLE
        }
    }

    fun updataItem(lifeIndexList : MutableList<LifeIndexEntity>){
        adapter.datas = lifeIndexList
        adapter.notifyDataSetChanged()
    }
}