package org.amos.core.frame.exception;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.exception.AuthException;
import org.amos.core.basic.vo.R;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author admin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleError(MethodArgumentTypeMismatchException e) {
        log.warn("请求参数格式错误", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        return R.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(TimeoutException.class)
    public void handleTimeoutException(TimeoutException e){
        log.error("【超时异常】，异常信息:{},{}",e.getMessage(), e);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R handleError(AuthException e) {
        log.error("认证异常", e);
        return R.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<?> handleException(Exception e){
        log.error(e.getMessage(), e);
        return R.error("服务器出了点小问题");
    }

    /**
     * DTO 参数校验异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleBindExceptionHandler(MethodArgumentNotValidException e){
        log.error(e.getMessage(), e);

        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuffer buffer = new StringBuffer();

        for (ObjectError error : errors) {
            buffer.append(error.getDefaultMessage());
            //只返回第一条不为空的信息,<注>集合顺序每次返回不一致
            if (!ObjectUtil.isEmpty(error.getDefaultMessage())){
                break;
            }
        }
        return R.error(buffer.toString());
    }
}
