/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.exception;

/**
 * @author xiong'MissDelia'zhengkun
 * 2020年7月15日14:26:30
 * 仅用于Application类中方法的异常
 * 1、其它类禁止使用此异常；
 * 2、Application类禁止使用其它异常；
 */
public class ApplicationInitialException extends RuntimeException {

    /** Constructs a new application initial exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ApplicationInitialException() {
        super();
    }

    /** Constructs a new application initial exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ApplicationInitialException(String message) {
        super(message);
    }

    /**
     * Constructs a new application initial exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this application initial exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public ApplicationInitialException(String message, Throwable cause) {
        super(message, cause);
    }

}
