/*
 * 2016-2021 ©JMX Consumer Finance 版权所有
 */
package cool.delia.core.base.repository

import cool.delia.core.net.RestApiHolder
import cool.delia.core.utils.LogUtil
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.RequestBody

/**
 * 扩展Repository类，适配协程
 *
 * @author xiong'MissDelia'zhengkun
 *
 * 2021/12/6 14:40
 */
class RepositoryKtx {

    companion object {

        private val TAG = RepositoryKtx::class.java.simpleName

        @JvmName(name = "createConnect")
        suspend fun IBaseRepository.getJsonFromNetworkWithCoroutine(url: String, params: RequestBody): JsonObject? {
            try {
                val post = GlobalScope.async(start = CoroutineStart.LAZY) {
                    RestApiHolder.coroutineService.post(url, params)
                }
                post.start()
                return post.await()
            } catch (e: Exception) {
                LogUtil.getInstance().e(TAG, e.message)
            }
            return null
        }

        @JvmName(name = "createConnect")
        suspend fun IBaseRepository.getJsonFromNetworkWithCoroutine(url: String, params: Map<String, Any>): JsonObject? {
            try {
                val post = GlobalScope.async(start = CoroutineStart.LAZY) {
                    RestApiHolder.coroutineService.post(url, params)
                }
                post.start()
                return post.await()
            } catch (e: Exception) {
                LogUtil.getInstance().e(TAG, e.message)
            }
            return null
        }
    }
}