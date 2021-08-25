package cool.delia.core.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import cool.delia.core.db.anno.TbField
import cool.delia.core.db.anno.TbName
import cool.delia.core.utils.LogUtil
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 * Dao类的基类，包含数据库的真实操作
 * @author CSDN_LQR in github
 *
 * xiong'Delia'zhengkun modified in 2021/3/18 16:37
 */
abstract class BaseDao<T> : IBaseDao<T> {
    private lateinit var mDatabase: SQLiteDatabase
    private lateinit var mEntityClass: Class<T>
    private lateinit var mTbName: String
    private lateinit var mFieldMap: MutableMap<String, Field>

    /**
     * 初始化表操作对象，一般包括：创建表、获取表字段与类字段的映射关系
     *
     * @param database 数据库对象
     * @param entity 实体类
     * @return 是否创建成功
     */
    fun init(database: SQLiteDatabase, entity: Class<T>): Boolean {
        mDatabase = database
        mEntityClass = entity
        if (!database.isOpen) {
            return false
        }

        // 获取表名
        val tbName = entity.getAnnotation(TbName::class.java)
        mTbName = tbName?.value ?: entity.simpleName

        // 获取表映射字段
        // 创建数据库
        return if (!genFieldMap()) {
            false
        } else {
            createTable(database)
        }
    }

    /**
     * 创建表（可以被子类重写，方便灵活扩展）
     */
    protected open fun createTable(database: SQLiteDatabase): Boolean {
        val sb = StringBuilder()
        for ((columnName, field) in mFieldMap) {
            val tbField = field.getAnnotation(TbField::class.java)
            val length = tbField?.length ?: 255
            var type = ""
            val fieldType = field.type
            if (fieldType == String::class.java) {
                type = "varchar"
            } else if (fieldType == Int::class.javaPrimitiveType || fieldType == Int::class.java) {
                type = "int"
            } else if (fieldType == Double::class.javaPrimitiveType || fieldType == Double::class.java) {
                type = "double"
            } else if (fieldType == Float::class.javaPrimitiveType || fieldType == Float::class.java) {
                type = "float"
            }
            if (TextUtils.isEmpty(type)) {
                LogUtil.getInstance().e(TAG, type.javaClass.name + "是不支持的字段")
            } else {
                sb.append(columnName)
                        .append(" ")
                        .append(type)
                        .append("(")
                        .append(length)
                        .append("),")
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","))
        val s = sb.toString()
        if (TextUtils.isEmpty(s)) {
            LogUtil.getInstance().e(TAG, "获取不到表字段信息")
            return false
        }
        val sql = "create table if not exists $mTbName ($s) "
        LogUtil.getInstance().i(TAG, sql)
        database.execSQL(sql)
        return true
    }

    private fun genFieldMap(): Boolean {
        mFieldMap = HashMap()
        // 得到类中的public字段，包括父类。
        val fields = mEntityClass.fields
        // 得到类中声明的字段（不管是public、protected、private），不包括父类。
//        val fields = mEntityClass.declaredFields
        if (fields.isEmpty()) {
            LogUtil.getInstance().e(TAG, "获取不到类中字段")
            return false
        }
        for (field in fields) {
            field.isAccessible = true
            val tbField = field.getAnnotation(TbField::class.java)
            (mFieldMap as HashMap<String, Field>)[tbField?.value ?: field.name] = field
        }
        return true
    }

    override fun insert(entity: T): Long? {
        try {
            val values = getValues(entity)
            val cv = getContentValues(values)
            return mDatabase.insert(mTbName, null, cv)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return 0L
    }

    override fun delete(where: T): Int? {
        try {
            val whereMap = getValues(where)
            val condition = Condition(whereMap)
            return mDatabase.delete(mTbName, condition.whereClause, condition.whereArgs)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return 0
    }

    override fun update(entity: T, where: T): Int? {
        try {
            val values = getValues(entity)
            val cv = getContentValues(values)
            val whereMap = getValues(where)
            val condition = Condition(whereMap)
            return mDatabase.update(mTbName, cv, condition.whereClause, condition.whereArgs)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return 0
    }

    override fun query(where: T): List<T>? {
        return query(where, null)
    }

    override fun query(where: T, orderBy: String?): List<T>? {
        return query(where, orderBy, null, null)
    }

    override fun query(where: T, orderBy: String?, page: Int?, pageCount: Int?): List<T>? {
        var pageNo = page
        var list: List<T>? = null
        var cursor: Cursor? = null
        try {
            var limit: String? = null
            if (pageNo != null && pageCount != null) {
                val startIndex: Int = --pageNo
                limit = startIndex.coerceAtLeast(0).toString() + "," + pageCount
            }
            cursor = if (where != null) {
                val whereMap = getValues(where)
                val condition = Condition(whereMap)
                mDatabase.query(mTbName, null, condition.whereClause, condition.whereArgs, null, null, orderBy, limit)
            } else {
                mDatabase.query(mTbName, null, null, null, null, null, orderBy, limit)
            }
            list = getDataList(cursor)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return list
    }

    /**
     * 将对象中的属性转成键值对
     */
    @Throws(IllegalAccessException::class)
    private fun getValues(entity: T): Map<String, String> {
        val result: MutableMap<String, String> = HashMap()
        for ((key, value1) in mFieldMap) {
            val value = value1[entity]
            result[key] = value?.toString() ?: ""
        }
        return result
    }

    /**
     * 将键值对转成ContentValues
     */
    private fun getContentValues(values: Map<String, String>): ContentValues {
        val cv = ContentValues()
        for ((key, value) in values) {
            cv.put(key, value)
        }
        return cv
    }

    /**
     * 通过游标，将表中数据转成对象集合
     */
    @Throws(IllegalAccessException::class, InstantiationException::class)
    @Suppress("UNCHECKED_CAST")
    private fun getDataList(cursor: Cursor?): List<T>? {
        if (cursor != null) {
            val result: MutableList<T> = ArrayList()
            // 遍历游标，获取表中一行行的数据
            while (cursor.moveToNext()) {

                // 创建对象
                val pt = this.javaClass.genericSuperclass as ParameterizedType // 获取当前new的对象的 泛型的父类 类型
                val clazz = pt.actualTypeArguments[0] as Class<T> // 获取第一个类型参数的真实类型
                val item = clazz.newInstance()

                // 遍历表字段，使用游标一个个取值，赋值给新创建的对象。
                for (columnName in mFieldMap.keys) {
                    // 找到表字段
                    // 找到表字段对应的类属性
                    val field = mFieldMap[columnName]

                    // 根据类属性类型，使用游标获取表中的值
                    var `val`: Any? = null
                    val fieldType = field?.type
                    if (fieldType == String::class.java) {
                        `val` = cursor.getString(cursor.getColumnIndex(columnName))
                    } else if (fieldType == Int::class.javaPrimitiveType || fieldType == Int::class.java) {
                        `val` = cursor.getInt(cursor.getColumnIndex(columnName))
                    } else if (fieldType == Double::class.javaPrimitiveType || fieldType == Double::class.java) {
                        `val` = cursor.getDouble(cursor.getColumnIndex(columnName))
                    } else if (fieldType == Float::class.javaPrimitiveType || fieldType == Float::class.java) {
                        `val` = cursor.getFloat(cursor.getColumnIndex(columnName))
                    }

                    // 反射给对象属性赋值
                    field!![item] = `val`
                }
                // 将对象添加到集合中
                result.add(item)
            }
            return result
        }
        return null
    }

    private class Condition(whereMap: Map<String, String>) {
        var whereClause: String
        var whereArgs: Array<String>

        init {
            val sb = StringBuilder()
            val list: MutableList<String> = ArrayList()
            for ((key, value) in whereMap) {
                if (!TextUtils.isEmpty(value)) {
                    sb.append("and ")
                            .append(key)
                            .append("=? ")
                    list.add(value)
                }
            }
            whereClause = sb.delete(0, 4).toString()
            whereArgs = list.toTypedArray()
        }
    }

    companion object {
        private val TAG = BaseDao::class.java.simpleName
    }
}