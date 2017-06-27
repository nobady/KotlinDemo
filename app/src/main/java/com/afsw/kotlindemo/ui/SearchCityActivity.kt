package com.afsw.kotlindemo.ui

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.adapter.HotCityAdapter
import com.afsw.kotlindemo.adapter.SearchCityAdapter
import com.afsw.kotlindemo.base.BaseMvpActivity
import com.afsw.kotlindemo.contract.SearchCityContract
import com.afsw.kotlindemo.db.CityDbEntity
import com.afsw.kotlindemo.weight.SideLetterBar
import kotlinx.android.synthetic.main.activity_search_city.*
import kotlinx.android.synthetic.main.item_city_select_header.*

class SearchCityActivity : BaseMvpActivity<SearchCityContract.View, SearchCityContract.Presenter>(),
    SideLetterBar.OnLetterChangedListener,SearchCityAdapter.OnItemClickListener,SearchCityContract.View{

    val hotCity = mutableListOf("北京", "上海", "广州", "深圳", "杭州", "南京", "武汉", "重庆")

    private lateinit var mAdapter:SearchCityAdapter

    var letterMap:Map<String,Int> = HashMap()

    override fun createPresenter() : SearchCityContract.Presenter = SearchCityContract.Presenter()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_city)

        /*设置recycler相关*/
        recycler.layoutManager = LinearLayoutManager(this)

        /*设置右边letterBar*/
        sideLetterBar.onLetterChanged = this
        sideLetterBar.overlay = overlayTv

        /*设置adapter*/
        mAdapter = SearchCityAdapter(this)
        mAdapter.setHeadView(createHeadView())
        recycler.adapter = mAdapter

        /*获取所有城市数据*/
        presenter.getAllCitys()
    }

    private fun createHeadView() : View {
        val view = LayoutInflater.from(this).inflate(R.layout.item_city_select_header, null,
                                                     false)
        tv_located_city.setOnClickListener {
            /*如果正在定位，点击不处理；如果定位失败，点击则重新定位；如果定位成功，点击返回到天气页面*/
        }

        recyclerView.layoutManager = GridLayoutManager(this,3)
        recyclerView.setHasFixedSize(true)

        val hotCityAdapter = HotCityAdapter(this)
        hotCityAdapter.datas = hotCity
        recyclerView.adapter = hotCityAdapter

        return view
    }

    override fun setCityDatas(allCitys : MutableList<CityDbEntity>) {
        val filterIndexed = allCitys.filterIndexed { index, cityDbEntity ->
            cityDbEntity.firstPinYin != null
        }
        

    }

    override fun onClick(position : Int, cityId : String) {
        /*点击item*/
    }

    override fun onLetterChanged(letter : String) {

    }
}
