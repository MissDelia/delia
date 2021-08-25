package cool.delia.core.db.anno

/**
 * 表字段注解
 * @author CSDN_LQR in github
 *
 * xiong'Delia'zhengkun modified in 2021/3/18 16:37
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TbField(val value: String, val length: Int)