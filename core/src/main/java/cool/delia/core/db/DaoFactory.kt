package cool.delia.core.db

import android.database.sqlite.SQLiteDatabase
import cool.delia.core.exception.DatabaseException
import cool.delia.core.utils.LogUtil
import java.io.Serializable

/**
 * Dao类工厂（负责打开数据库，生产对应的表操作对象）
 * @author CSDN_LQR in github
 *
 * xiong'Delia'zhengkun modified in 2021/3/18 16:37
 */
object DaoFactory {

    private val TAG = DaoFactory::class.java.simpleName

    private lateinit var mDatabase: SQLiteDatabase

    private var runInit = false

    fun <T : BaseDao<R>, R : Serializable> getDataHelper(clazz: Class<T>, entity: Class<R>): T? {
        // 必须先运行init方法才能使用此函数
        if (!runInit) {
            throw DatabaseException("before use BaseDaoFactory, run BaseDaoFactory.init() to initialize database path")
        }
        var baseDao: T? = null
        try {
            baseDao = clazz.newInstance()
            val init = baseDao.init(mDatabase, entity)
            LogUtil.getInstance().i(TAG, if (init) "初始化数据库实例成功" else "初始化数据库实例失败")
        } catch (e: InstantiationException) {
            LogUtil.getInstance().e(TAG, e.message)
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            LogUtil.getInstance().e(TAG, e.message)
            e.printStackTrace()
        }
        return baseDao
    }

    fun init(dbPath: String) {
        // 打开数据库，得到数据库对象
        mDatabase = SQLiteDatabase.openOrCreateDatabase(dbPath, null)
        runInit = true
    }
}