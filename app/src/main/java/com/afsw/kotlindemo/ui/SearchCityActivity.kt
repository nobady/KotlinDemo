package com.afsw.kotlindemo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.afsw.kotlindemo.R
import com.afsw.kotlindemo.adapter.HotCityAdapter
import com.afsw.kotlindemo.adapter.SearchCityAdapter
import com.afsw.kotlindemo.base.BaseMvpActivity
import com.afsw.kotlindemo.contract.SearchCityContract
import com.afsw.kotlindemo.db.CityDbEntity
import com.afsw.kotlindemo.utils.Constants
import com.afsw.kotlindemo.utils.PreferenceUtil
import com.afsw.kotlindemo.weight.SideLetterBar
import kotlinx.android.synthetic.main.activity_search_city.*

class SearchCityActivity : BaseMvpActivity<SearchCityContract.View, SearchCityContract.Presenter>(), SideLetterBar.OnLetterChangedListener, SearchCityAdapter.OnItemClickListener, SearchCityContract.View {

    val hotCity = mutableListOf("北京", "上海", "广州", "深圳", "杭州", "南京", "武汉", "重庆")

    private lateinit var mAdapter : SearchCityAdapter

    var letterMap : MutableMap<String, Int> = HashMap()

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
        val view = LayoutInflater.from(this).inflate(R.layout.item_city_select_header, null)
        view.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                      ViewGroup.LayoutParams.WRAP_CONTENT)

        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        val locationTv = view.findViewById(R.id.tv_located_city) as TextView

        val locationName = PreferenceUtil.get().getString(Constants.LOCATION_NAME)
        /*如果sp里面的定位名称不为null，就直接显示*/
        locationName?.let {
            locationTv.text = it
        }

        locationName?:let {
            locationTv.text = getString(R.string.located_failed)
            view.findViewById(R.id.tv_located_city).setOnClickListener {
                /*如果正在定位，点击不处理；如果定位失败，点击则重新定位；如果定位成功，点击返回到天气页面*/
                locationTv.text = getString(R.string.locating)
            }
        }

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.setHasFixedSize(true)

        val hotCityAdapter = HotCityAdapter(this)
        hotCityAdapter.datas = hotCity
        recyclerView.adapter = hotCityAdapter

        return view
    }

    override fun setCityDatas(allCitys : MutableList<CityDbEntity>) {
        /*找出firstPinYin不为null的数据，并保存在map中*/
        allCitys.mapIndexedNotNull { index, cityDbEntity ->
            cityDbEntity.firstPinYin?.let { letterMap.put(it, index) }
        }

        mAdapter.datas = allCitys
        mAdapter.notifyDataSetChanged()

    }

    override fun onClick(position : Int, cityId : String) {
        /*点击item，拿到城市id，返回到主页面*/
        val intent = Intent()
        intent.putExtra(Constants.SELECT_CITY_ID,cityId)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun onLetterChanged(letter : String) {
        letterMap[letter]?.let {
            (recycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it, 0)
        }

    }
}
