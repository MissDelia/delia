/*
 * 2016-2020 ©MissDelia 版权所有
 * "Anti 996" License Version 1.0
 */
package cool.delia.core.net.data;

import java.io.Serializable;

/**
 * 用于处理Response信息的基类
 * 2020年7月16日11:03:06
 * @author xiong'MissDelia'zhengkun
 * @param <T> 可以是{@link java.util.ArrayList}、{@link java.util.HashMap}
 *           或者其它继承{@link Serializable}接口的实体类
 *           注意：这里暂时不允许使用SparseArray、ArrayMap，因其未实现序列化
 */
public class Response<T extends Serializable> implements Serializable {

    private int code;

    private String message;

    private T content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
