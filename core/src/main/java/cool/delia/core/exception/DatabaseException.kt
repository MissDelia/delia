/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.exception

/**
 * 用于数据库异常提示
 * @author xiong'MissDelia'zhengkun
 * @since V1.2.0
 * 2020年7月15日14:26:30
 */
class DatabaseException : RuntimeException {
    /** Constructs a new application initial exception with `null` as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to [.initCause].
     */
    constructor() : super()

    /** Constructs a new application initial exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to [.initCause].
     *
     * @param   message   the detail message. The detail message is saved for
     * later retrieval by the [.getMessage] method.
     */
    constructor(message: String) : super(message)

    /**
     * Constructs a new application initial exception with the specified detail message and
     * cause.
     *
     *Note that the detail message associated with
     * `cause` is *not* automatically incorporated in
     * this application initial exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     * by the [.getMessage] method).
     * @param  cause the cause (which is saved for later retrieval by the
     * [.getCause] method).  (A <tt>null</tt> value is
     * permitted, and indicates that the cause is nonexistent or
     * unknown.)
     * @since  1.4
     */
    constructor(message: String, cause: Throwable) : super(message, cause)
}