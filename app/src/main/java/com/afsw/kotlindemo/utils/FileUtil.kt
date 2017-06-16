package com.afsw.kotlindemo.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by tengfei.lv on 2017/6/14.
 */
class FileUtil {

    companion object {
        fun get() : FileUtil {
            return Holder.instance
        }
    }

    private object Holder {
        var instance = FileUtil()
    }

    fun readAssetsFile(context : Context, fileName : String) : String {
        val inputStream = context.assets.open(fileName)
        val br = BufferedReader(InputStreamReader(inputStream))
        val readText = br.readText()
        br.close()
        inputStream.close()
        return readText
    }
}