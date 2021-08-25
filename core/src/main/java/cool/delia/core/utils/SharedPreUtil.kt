/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.utils

import android.annotation.SuppressLint
import android.content.Context
import cool.delia.core.CoreApplication
import cool.delia.core.exception.CacheInitialException
import com.tencent.mmkv.MMKV

/**
 * MMKV的封装实现
 * @author xiong'MissDelia'zhengkun
 * @since V1.3.0
 * 2021/4/21 15:28
 */
object SharedPreUtil: MemoryCacheModel {

    /**
     * 全局持久缓存实例
     */
    private val cache = MMKV.defaultMMKV()

    /**
     * 从旧的Shared模型迁移数据
     */
    fun moveOldMemoryModel(memoryKey: String) {
        val oldShared = CoreApplication.getApplication().getSharedPreferences(memoryKey, Context.MODE_PRIVATE)
        if (cache != null) {
            cache.importFromSharedPreferences(oldShared)
            oldShared.edit().clear().apply()
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun <T> setValue(key: String, def: T) {
        if (cache == null) {
            throw CacheInitialException("Fail to initialize SharedPreferences" +
                    ", SharedPreUtil is unavailable!")
        }
        val tempValue: String = def.toString()
        cache.edit().putString(key, tempValue)
    }

    override fun <T> getValue(key: String, def: T): T {
        if (cache == null) {
            throw CacheInitialException("Fail to initialize SharedPreferences" +
                    ", SharedPreUtil is unavailable!")
        }
        try {
            val tempDef = def.toString()
            val tempValue: String? = cache.getString(key, tempDef)
            val clz = def!!::class.java
            val constructor = clz.getConstructor(String::class.java)
            return constructor.newInstance(tempValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return def
    }

    override fun hasKey(key: String): Boolean {
        if (cache == null) {
            throw CacheInitialException("Fail to initialize SharedPreferences" +
                    ", SharedPreUtil is unavailable!")
        }
        return cache.containsKey(key)
    }

    @SuppressLint("CommitPrefEdits")
    override fun removeKey(key: String) {
        if (cache == null) {
            throw CacheInitialException("Fail to initialize SharedPreferences" +
                    ", SharedPreUtil is unavailable!")
        }
        cache.edit().remove(key)
    }
}