package org.amos.core.basic.vo;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 自定义响应数据结构
 * @param <T>
 */
@Data
public class R<T> implements Serializable {
    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public R() {
    }

    public R(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public R(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public R(T data) {
        this.code = HttpServletResponse.SC_OK;
        this.msg = "ok";
        this.data = data;
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(data);
    }

    public static <T> R<T> ok() {
        return new R<T>(null);
    }

    public static <T> R<T> error(String msg) {
        return new R<T>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg, null);
    }

    public static <T> R<T> error(int code, String msg) {
        return new R<T>(code, msg, null);
    }

    public static <T> R<T> error(T data) {
        return new R<T>(HttpServletResponse.SC_NOT_IMPLEMENTED, "error", data);
    }
}
