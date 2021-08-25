package cool.delia.core.db.anno

/**
 * 表名注解
 * @author CSDN_LQR in github
 *
 * xiong'Delia'zhengkun modified in 2021/3/18 16:37
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TbName(val value: String)