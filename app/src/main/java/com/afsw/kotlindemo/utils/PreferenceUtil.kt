package com.afsw.kotlindemo.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

/**
 * Created by tengfei.lv on 2017/6/14.
 */
class PreferenceUtil private constructor(){

    private object Holder{
        var instance = PreferenceUtil()
    }

    companion object{
        fun get():PreferenceUtil{
            return Holder.instance
        }

         var preferences:SharedPreferences? = null
         var editor:SharedPreferences.Editor? = null

        fun init(context: Context,name:String,mode:Int){
            preferences = context.getSharedPreferences(name, mode)
            editor = preferences!!.edit()
        }
    }


    fun getBoolean(key : String, defaultVal : Boolean) : Boolean {
        return preferences!!.getBoolean(key, defaultVal)
    }

    fun getBoolean(key : String) : Boolean {
        return preferences!!.getBoolean(key, false)
    }

    fun getString(key : String, defaultVal : String) : String {
        return preferences!!.getString(key, defaultVal)
    }

    fun getString(key : String) : String? {
        return preferences!!.getString(key, null)
    }

    fun getInt(key : String, defaultVal : Int) : Int {
        return preferences!!.getInt(key, defaultVal)
    }

    fun getInt(key : String) : Int {
        return preferences!!.getInt(key, 0)
    }

    fun getFloat(key : String, defaultVal : Float) : Float {
        return preferences!!.getFloat(key, defaultVal)
    }

    fun getFloat(key : String) : Float {
        return preferences!!.getFloat(key, 0f)
    }

    fun getLong(key : String, defaultVal : Long) : Long {
        return preferences!!.getLong(key, defaultVal)
    }

    fun getLong(key : String) : Long {
        return preferences!!.getLong(key, 0L)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) fun getStringSet(key : String,
                                                               defaultVal : Set<String>) : Set<String> {
        return preferences!!.getStringSet(key, defaultVal)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) fun getStringSet(key : String) : Set<String> {
        return preferences!!.getStringSet(key, null)
    }

    fun getAll() : Map<String, *> {
        return preferences!!.all
    }

    fun exists(key : String) : Boolean {
        return preferences!!.contains(key)
    }

    fun putString(key : String, value : String) : PreferenceUtil {
        editor!!.putString(key, value)
        return this
    }

    fun putInt(key : String, value : Int) : PreferenceUtil {
        editor!!.putInt(key, value)
        return this
    }

    fun putFloat(key : String, value : Float) : PreferenceUtil {
        editor!!.putFloat(key, value)
        return this
    }

    fun putLong(key : String, value : Long) : PreferenceUtil {
        editor!!.putLong(key, value)
        return this
    }

    fun putBoolean(key : String, value : Boolean) : PreferenceUtil {
        editor!!.putBoolean(key, value)
        return this
    }

    fun apply() {
        editor!!.apply()
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) fun putStringSet(key : String,
                                                               value : Set<String>) : PreferenceUtil {
        editor!!.putStringSet(key, value)
        return this
    }

    fun putObject(key : String, `object` : Any) {
        val baos = ByteArrayOutputStream()
        var out : ObjectOutputStream? = null
        try {
            out = ObjectOutputStream(baos)
            out.writeObject(`object`)
            val objectVal = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            editor!!.putString(key, objectVal)
        } catch (e : IOException) {
            e.printStackTrace()
        } finally {
            try {
                baos.close()
                if (out != null) {
                    out.close()
                }
            } catch (e : IOException) {
                e.printStackTrace()
            }

        }
    }

//    fun <T> getObject(key : String, clazz : Class<T>) : T? {
//        if (preferences!!.contains(key)) {
//            val objectVal = preferences!!.getString(key, null)
//            val buffer = Base64.decode(objectVal, Base64.DEFAULT)
//            val bais = ByteArrayInputStream(buffer)
//            var ois : ObjectInputStream? = null
//            try {
//                ois = ObjectInputStream(bais)
//                val t = ois.readObject() as T
//                return t
//            } catch (e : StreamCorruptedException) {
//                e.printStackTrace()
//            } catch (e : IOException) {
//                e.printStackTrace()
//            } catch (e : ClassNotFoundException) {
//                e.printStackTrace()
//            } finally {
//                try {
//                    bais.close()
//                    if (ois != null) {
//                        ois.close()
//                    }
//                } catch (e : IOException) {
//                    e.printStackTrace()
//                }
//
//            }
//        }
//        return null
//    }

    fun remove(key : String) : PreferenceUtil {
        editor!!.remove(key)
        return this
    }

    fun removeAll() : PreferenceUtil {
        editor!!.clear()
        return this
    }

}