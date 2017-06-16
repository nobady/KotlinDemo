package com.afsw.kotlindemo.net

import com.afsw.kotlindemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by tengfei.lv on 2017/6/9.
 */
object RetrofitClient {

    fun retrofit() : ApiStores {

        val builder = OkHttpClient.Builder()

        /*调试模式下，添加日志打印*/
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        builder.retryOnConnectionFailure(true).readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)

        val okHttpClient = builder.build()

        val retrofit = Retrofit.Builder().addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).addConverterFactory(
            GsonConverterFactory.create()).baseUrl(ApiStores.BASE_URL).client(okHttpClient).build()

        return retrofit.create(ApiStores::class.java)
    }
}