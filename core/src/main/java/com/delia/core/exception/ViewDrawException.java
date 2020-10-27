/*
 * 2016-2020 ©MissDelia 版权所有
 */
package com.delia.core.exception;

/**
 * 绘制视图时出错的Exception
 * @author xiong'MissDelia'zhengkun
 * 2020/10/16 11:22
 */
public class ViewDrawException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ViewDrawException(String message) {
        super(message);
    }
}
