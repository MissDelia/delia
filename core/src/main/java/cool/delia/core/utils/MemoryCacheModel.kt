/*
 * 2016-2021 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.utils

/**
 * 定义缓存模型的操作
 * 共有方法为3个，分别是set（增、改）、get（查）和remove（删）
 * @author xiong'MissDelia'zhengkun
 * @since V1.3.0
 * 2021/4/21 15:30
 */
interface MemoryCacheModel {

    fun <T> setValue(key: String, def: T)

    fun <T> getValue(key: String, def: T) : T

    fun hasKey(key: String) : Boolean

    fun removeKey(key: String)
}