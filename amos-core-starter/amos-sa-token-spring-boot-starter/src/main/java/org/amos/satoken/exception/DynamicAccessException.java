package org.amos.satoken.exception;

/**
 * @desc: 动态权限异常
 * @author: liubt
 * @date: 2022-08-17 10:24
 **/
public class DynamicAccessException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DynamicAccessException(String message) {
        super(message);
    }

    public DynamicAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicAccessException(Throwable cause) {
        super(cause);
    }
}
