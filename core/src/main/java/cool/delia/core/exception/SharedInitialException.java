/*
 * 2016-2021 ©MissDelia 版权所有
 */
package cool.delia.core.exception;

/**
 * SharedPreUtil异常
 * @author xiong'MissDelia'zhengkun
 * 2020/7/17 14:45
 */
public class SharedInitialException extends Exception {

    /** Constructs a new shared initial exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public SharedInitialException() {
        super();
    }

    /** Constructs a new shared initial exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public SharedInitialException(String message) {
        super(message);
    }

    /**
     * Constructs a new application initial exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this shared initial exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public SharedInitialException(String message, Throwable cause) {
        super(message, cause);
    }
}
