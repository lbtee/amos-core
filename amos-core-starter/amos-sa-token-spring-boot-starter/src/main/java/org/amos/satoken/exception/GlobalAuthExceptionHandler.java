package org.amos.satoken.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.exception.AuthException;
import org.amos.core.basic.vo.R;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author admin
 */
@Slf4j
@RestControllerAdvice
@Order(-99)
public class GlobalAuthExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public R handleNotLoginError(NotLoginException e) {
        log.error("未登录异常", e);
        return R.error(HttpStatus.HTTP_UNAUTHORIZED, "unauthorized");
    }
    @ExceptionHandler(AuthException.class)
    public R handleError(AuthException e) {
        log.error("认证异常", e);
        return R.error( e.getMessage());
    }

    @ExceptionHandler(DynamicAccessException.class)
    public R<?> handleException(DynamicAccessException e){
        log.error(e.getMessage(), e);
        return R.error(HttpStatus.HTTP_FORBIDDEN, "the interface has no access permission");
    }
}
