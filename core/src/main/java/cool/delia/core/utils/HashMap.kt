/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.utils

import android.os.Bundle
import java.io.Serializable

/**
 * 扩展Map函数
 * @author xiong'MissDelia'zhengkun
 * 2021/10/29 16:40
 */
abstract class HashMap {

    /**
     * 将Map转Bundle方法
     * 目前只支持Int、Long、Float、Double、Char、String和Serializable七个类型，其它类型将不进行转换
     */
    fun MutableMap<String, Any>.toBundle(): Bundle {
        val bundle = Bundle()
        for (key in this.keys) {
            when (val obj = this[key]) {
                is Int -> {
                    bundle.putInt(key, obj)
                }
                is Long -> {
                    bundle.putLong(key, obj)
                }
                is Float -> {
                    bundle.putFloat(key, obj)
                }
                is Double -> {
                    bundle.putDouble(key, obj)
                }
                is Char -> {
                    bundle.putChar(key, obj)
                }
                is String -> {
                    bundle.putString(key, obj)
                }
                is Serializable -> {
                    bundle.putSerializable(key, obj)
                }
            }
        }
        return bundle
    }
}