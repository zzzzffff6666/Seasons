package com.zhang.seasons.config;
import com.zhang.seasons.http.APIMsg;
import com.zhang.seasons.http.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionResolver {
    /**
     * 处理shiro框架异常
     * @param e shiro异常
     * @return 异常信息
     */
    @ExceptionHandler(ShiroException.class)
    public Result doHandleShiroException(ShiroException e) {
        log.error(e.getMessage());
        if (e instanceof UnknownAccountException) {
            return Result.error(APIMsg.LOGIN_ERROR);
        } else if (e instanceof LockedAccountException) {
            return Result.error(APIMsg.ACCOUNT_ERROR);
        } else if (e instanceof IncorrectCredentialsException) {
            return Result.error(APIMsg.LOGIN_ERROR);
        } else if (e instanceof AuthorizationException) {
            return Result.error(APIMsg.AUTH_ERROR);
        } else {
            return Result.error(APIMsg.UNKNOWN_ERROR);
        }
    }

    /**
     * 定义全局异常处理
     * @param e 运行时异常
     * @return 异常信息
     */
    @ExceptionHandler(value = RuntimeException.class)
    public Result topException(RuntimeException e) {
        e.printStackTrace();
        return Result.error(e.toString());
    }
}