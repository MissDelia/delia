package cool.delia.core.db

/**
 * 基本的数据操作
 * @author CSDN_LQR in github
 *
 * xiong'Delia'zhengkun modified in 2021/3/18 16:37
 */
interface IBaseDao<T> {
    fun insert(entity: T): Long?
    fun delete(where: T): Int?
    fun update(entity: T, where: T): Int?
    fun query(where: T): List<T>?
    fun query(where: T, orderBy: String?): List<T>?
    fun query(where: T, orderBy: String?, page: Int?, pageCount: Int?): List<T>?
}